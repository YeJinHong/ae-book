<template>
  <div>
    <div>ReviewCreate components</div>
    <div>
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group
        id="input-group-0"
        label="Review title"
        label-for="input-0"
      >
        <b-form-input
          id="input-0"
          v-model="form.title"
          type="text"
          placeholder="Enter title"
          required
        ></b-form-input>
      </b-form-group>

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

      <b-form-group
        id="input-group-2"
        label="Book ISBN"
        label-for="input-2"
      >
        <b-form-input
          id="input-2"
          v-model="form.isbn"
          type="text"
          maxlength=13
          placeholder="Enter ISBN"
          required
        ></b-form-input>
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
      <!-- 별점 테스트 -->
      <div class="inner">
        <div class="star-rating">
          <div
            class="star"
            v-for="index in 5"
            :key="index"
            @click="check(index)"
          >
            <span v-if="index <= form.score">🍎</span>
            <span v-if="index > form.score">🍏</span>
          </div>
        </div>
      </div>
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
import api from '@/api/auth'
import axios from 'axios'

export default {
  name: 'ReviewCreateView',
  data () {
    return {
      form: {
        title: '',
        keyword: '',
        writer: null,
        char: null,
        isbn: '',
        content: '',
        score: 5
      },
      show: true
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
    async createReview () {
      await api({
        method: 'POST',
        url: `/api/reviews/${this.form.isbn}`,
        data: {
          'content': this.form.content,
          'score': this.form.score
        }
      }).then((result) => {
        console.log(result)
      }).catch((err) => {
        console.log(err)
      })
    },
    onSubmit (event) {
      event.preventDefault()
      this.createReview()
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
    check (index) {
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
