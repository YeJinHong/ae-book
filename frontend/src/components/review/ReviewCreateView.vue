<template>
  <div>
    <div>
      <div class='description'>
        <p class='p-bottom'>방법 1. 키워드를 작성한다. 리뷰의 길이는 선택적으로 지정한다.</p>
        <p class='p-line'>자동 작성 버튼을 누른다.</p>
        <p class='p-bottom'>방법 2. 마이크 버튼을 누른다. 키워드를 나열하며 말한다.</p>
        <p class='p-line'>마이크 버튼을 눌러서 녹음을 멈춘다.</p>
      </div>
      <b-form @reset="onReset" v-if="show">
        <div class='line-1'>
          <div class='line-2'>
            <b-form-group
              id="input-group-1"
            >
              <div>
                <b-form-input
                  id="input-1"
                  class="input-line"
                  v-model="form.keyword"
                  type="text"
                  placeholder="키워드를 입력하세요."
                  ref="keywordInput"
                ></b-form-input>
              </div>
            </b-form-group>
              <b-form-group
                id="input-group-2"
                label-for="input-2"
              >
                <b-form-input
                  id="input-2"
                  class="input-line"
                  v-model="form.char"
                  type="number"
                  placeholder="1 이상 100 이하의 숫자를 입력하세요."
                  min="1"
                  max="100"
                ></b-form-input>
              </b-form-group>
          </div>
          <button id='auto-create' class='ae-btn btn-red' type="button" @click="createAIReview">자동 작성</button>
          <!-- <button v-if="isRecording===false" class='' type="button" @click="soundToKeyword">말하기</button> -->
          <!-- <button v-else class='' type="button" @click="stopSoundToKeyword">멈추기</button> -->
        </div>
        <b-form-group id="input-group-3" label="리뷰내용" label-for="input-2">
          <b-form-textarea
            id="input-3"
            v-model="form.content"
            placeholder="내용을 300자 내로 입력해주세요."
            rows="6"
            min="1"
            :max="150"
            ref="contentInput"
          ></b-form-textarea>
        </b-form-group>

        <b-button class='submit-btn b-btn' type="button" variant="primary" @click="onSubmit">등록</b-button>
        <b-button class='reset-btn b-btn' type="reset" variant="danger">초기화</b-button>
      </b-form>
      <!-- <b-card class="mt-3" header="Form Data Result">
        <pre class="m-0">{{ bookInfo }}</pre>
      </b-card> -->
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions, mapState } from 'vuex'
import ReviewModifyScoreView from './ReviewModifyScoreView.vue'

const reviewStore = 'reviewStore'
const bookStore = 'bookStore'

export default {
  components: { ReviewModifyScoreView },
  name: 'ReviewCreateView',
  props: {
    bookInfo: Object
  },
  data () {
    return {
      form: {
        title: this.bookInfo.title,
        keyword: '',
        writer: this.bookInfo.writer,
        char: null,
        isbn: this.bookInfo.isbn,
        content: '',
        score: 5
      },
      show: true,
      isModify: true,
      audioArray: [],
      mediaRecorder: null,
      isRecording: false
    }
  },
  computed: {
    ...mapState(bookStore, ['book'])
  },
  methods: {
    soundToKeyword () {
      navigator.mediaDevices.getUserMedia({ audio: true })
        .then((stream) => {
          this.mediaRecorder = new MediaRecorder(stream)
          this.mediaRecorder.ondataavailable = (event) => {
            this.audioArray.push(event.data)
          }
          this.mediaRecorder.start()
          this.isRecording = true
        })
    },
    async stopSoundToKeyword () {
      this.isRecording = false
      this.mediaRecorder.stop()
      this.mediaRecorder.onstop = (event) => {
        const blob = new Blob(this.audioArray, {type: 'audio/mp3'})
        this.audioArray.splice(0)
        const formData = new FormData()
        formData.append('audio', blob, 'recoding.mp3')
        formData.append('title', this.form.title)

        if (this.writer != null) {
          formData.append('writer', this.form.writer)
        }
        if (this.char != null) {
          formData.append('char', this.form.char)
        }

        axios
          .post('/fast/reviews/sound', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(result => {
            console.log(result)
            this.form.content = result.data.review
            this.form.score = result.data.star
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    async createAIReview () {
      if (!this.form.keyword) {
        alert('키워드를 입력해주세요.')
        this.$refs.keywordInput.focus()
        return
      }

      axios
        .post(`/fast/reviews/gpt`, {
          title: this.form.title,
          keyword: this.form.keyword,
          writer: this.form.writer,
          char: this.form.char
        })
        .then(result => {
          console.log(result)
          this.form.content = result.data.review
          this.form.score = result.data.star
        })
        .catch(err => {
          console.log(err)
        })
    },
    ...mapActions(reviewStore, ['saveReviewAction', 'getReviewBookListAction']),
    onSubmit (event) {
      if (!this.form.content) {
        alert('리뷰 내용을 입력해주세요')
        this.$refs.contentInput.focus()
        return
      }

      if (this.form.content.length > 300) {
        alert('리뷰 입력은 300자 내로 가능합니다. \n' + '현재 입력된 글자는 ' + this.form.content.length + '자 입니다.')
        this.$refs.contentInput.focus()
        return
      }

      const payload = {
        isbn: this.form.isbn,
        data: {
          content: this.form.content,
          score: this.form.score
        }
      }

      const request = {
        isbn: this.form.isbn,
        page: 0,
        size: 3,
        sort: 'createdAt',
        direction: 'DESC'
      }

      this.saveReviewAction(payload)
        .then(() => {
          return this.getReviewBookListAction(request)
        })
        .then(() => {
          this.$emit('close-modal')
          this.$emit('get-my-review')
          this.book.reviewCount += 1
          this.book.scoreSum += this.form.score
        })
        .catch((err) => {
          console.log(err)
        })
    },
    onReset (event) {
      event.preventDefault()
      // Reset our form values
      this.form.keyword = ''
      this.form.content = ''
      this.form.score = 5
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    },
    modifyScore (index) {
      this.form.score = index
    }
  }

}
</script>

<style scoped>
.input-line {
  width: 350px;
}
.p-bottom {
  margin-bottom: 0rem;
}
.p-line {
  margin-left: 54px;
}
.description {
  margin-left: 18px;
  text-align: left;
}
.reset-btn {
  background-color: var(--ae-red);
}
.submit-btn {
  background-color: var(--ae-navy);
}
.line-1 {
  display: flex;
}
.score-container {
  margin: 36px 0px 0px 0px;
}
.btn-red {
  width: 120px;
  height: 95px;
  padding: 0px 5px;
  border-radius: 8px;
  border: 2px solid;
  font-weight: bold;
}
.b-btn {
  width: 90px;
  font-weight: bold;
}
</style>
