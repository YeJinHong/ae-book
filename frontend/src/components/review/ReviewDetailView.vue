<template>
  <div>
    <div>ReviewDetail components</div>
    <!-- 리뷰 디테일 -->
    <div v-if="review">
     reviewId : {{ review.reviewId }}  <br/>
     작성자 : {{ review.reviewerNickname }} <br/>
     별점 : {{ review.score }} <br/>
     내용 : {{ review.content }} <br/>
     작성일 : {{ review.createAt }} <br />
     수정일 : {{ review.updateAt }}
    </div>
    <!-- "result": {
        "reviewId": 23,
        "reviewerNickname": "닉네임2",
        "score": 5,
        "content": "억뷰",
        "createAt": "2023-04-21T08:15:55",
        "updateAt": "2023-04-21T08:15:55"
    },
    }, -->
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
    ...mapActions(reviewStore, ['getReviewAction'])
  },
  computed: {
    ...mapGetters(reviewStore, ['getReview']),
    ...mapState(reviewStore, ['review'])
  },
  mounted () {
    this.getReviewAction(this.reviewId)
    // this.review = this.getReview
  },
  created () {
    console.log('created: ' + this.$route.params.reviewId)
  }
}
</script>

<style>

</style>
