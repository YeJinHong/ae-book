<template>
  <div>
    <div>
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group
        id="input-group-1"
        label="Review Keyword"
        label-for="input-1"
      >
        <b-form-input
          id="input-1"
          v-model="form.keyword"
          type="text"
          placeholder="Enter Keyword"
          required
        ></b-form-input>
        <button @click="createAIReview">자동 작성</button>
      </b-form-group>
      <b-form-group id="input-group-3" label="Content" label-for="input-2">
        <b-form-textarea
          id="input-3"
          v-model="form.content"
          placeholder="Enter Content"
          rows="3"
          required
        ></b-form-textarea>
      </b-form-group>
      <review-modify-score-view v-if="isModify" :score=this.form.score :isModify=this.isModify @modify-score="modifyScore" />
      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
    <b-card class="mt-3" header="Form Data Result">
      <pre class="m-0">{{ form }}</pre>
    </b-card>
  </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapActions } from 'vuex'
import ReviewModifyScoreView from './ReviewModifyScoreView.vue'

const reviewStore = 'reviewStore'

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
      isModify: true
    }
  },
  methods: {
    async createAIReview () {
      axios
        .post(`http://127.0.0.1:8000/reviews/gpt`, {
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
      event.preventDefault()

      const payload = {
        isbn: this.isbn,
        data: {
          content: this.form.content,
          score: this.form.score
        }
      }

      const request = {
        isbn: this.isbn,
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
        })
        .catch((err) => {
          console.log(err)
        })
    },
    onReset (event) {
      event.preventDefault()
      // Reset our form values
      this.form.keyword = ''
      this.form.isbn = ''
      this.form.content = ''
      this.form.score = 5
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    },
    modifyScore (index) {
      console.log(index)
      this.form.score = index
    }
  }

}
</script>

<style>
.star {
  display: inline-block;
  font-size: 2em;
}
</style>
