<template>
  <div>
    마이페이지 리뷰 리스트
    <button @click="getMyReviewList">로그인 사용자의 리뷰 리스트</button>
  </div>
</template>

<script>
import api from '@/api/auth'

export default {
  name: 'MyReviewListView',
  data () {
    return {
      page: 0,
      size: 3,
      sort: 'id,DESC'
    }
  },
  methods: {
    getMyReviewList () {
      api({
        method: 'GET',
        url: `/api/reviews?page=${this.page}&size=${this.size}&sort=${this.sort}`
      }).then((result) => {
        console.log(result)
      }).catch((err) => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped></style>
