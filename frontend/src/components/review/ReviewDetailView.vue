<template>
  <div>
    <div>ReviewDetail components</div>
      <div v-if="review">
        <div>
          작성자: {{ review.reviewerNickname }} <br/>
          작성일: {{ review.createdAt }} <br />
          수정일: {{ review.updatedAt }}
        </div>
        <b-form-group id="input-group-1" label="Content" label-for="input-2">
        <b-form-textarea
          id="input-1"
          v-model=review.content
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
            <span v-if="index <= review.score">🍎</span>
            <span v-if="index > review.score">🍏</span>
          </div>
        </div>
      </div>
      <b-button @click="onSubmit" type="submit" variant="primary">Submit</b-button>
      <b-button @click="onReset" type="reset" variant="danger">Reset</b-button>
      <b-button @click="onDelete" type="button" variant="warning">Delete</b-button>
    <!-- 리뷰 디테일 -->
    <button @click="modifyReview">수?정</button>
      </div>
      <div v-else>
        해당 리뷰가 존재하지 않다고 하네요 ~
      </div>

    </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex'
const reviewStore = 'reviewStore'

export default {

  name: 'ReviewDetailView',
  props: ['reviewId'],
  data () {
    return {
      // review: Object
    }
  },
  methods: {
    ...mapActions(reviewStore, ['getReviewAction', 'modifyReviewAction', 'deleteReviewAction']),
    modifyReview () {
      console.log('modify Review')
    },
    onSubmit (event) {
      console.log('onSubmit')

      const payload = {
        reviewId: this.review.id,
        data: {
          content: this.review.content,
          score: this.review.score
        }
      }

      event.preventDefault()
      this.modifyReviewAction(payload)
      this.getReviewAction(this.review.id)
    },
    onReset (event) {
      event.preventDefault()
      // Reset our form values
      this.review.content = ''
      this.review.score = 5
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    },
    onDelete (event) {
      event.preventDefault()
      if (confirm('리얼루다가 삭제하시것슴니까 ?!?!?!?!!!?')) {
        this.deleteReviewAction(this.review.id)
      }

      // TODO: 도서 상세라면 서평 리스트 다시 얻기
      this.$router.push({ name: 'Review' })
    },
    check (index) {
      console.log(index)
      this.review.score = index
    }
  },
  computed: {
    ...mapGetters(reviewStore, ['getReview']),
    ...mapState(reviewStore, ['review'])
  },
  mounted () {
    this.getReviewAction(this.reviewId)
  },
  created () {

  }
}
</script>

<style>
.star {
  display: inline-block;
  font-size: 2em;
}
</style>
