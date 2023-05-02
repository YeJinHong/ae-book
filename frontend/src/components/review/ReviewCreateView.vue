<template>
  <div>
    <div>ReviewCreate components</div>
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
            <span v-if="index <= form.score">🧡</span>
            <span v-if="index > form.score">🤍</span>
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

export default {
  name: 'ReviewCreateView',
  data () {
    return {
      form: {
        keyword: '',
        isbn: '',
        content: '',
        score: 5
      },
      show: true
    }
  },
  methods: {
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
