<template>
  <div>
    <div>ReviewModify components</div>
    reviewId : {{ review.reviewId }}  <br/>
     작성자 : {{ review.reviewerNickname }} <br/>
     별점 : {{ review.score }} <br/>
     내용 : {{ review.content }} <br/>
     작성일 : {{ review.createAt }} <br />
     수정일 : {{ review.updateAt }}
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex'
const reviewStore = 'reviewStore'

export default {
  name: 'ReviewModifyView',
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
