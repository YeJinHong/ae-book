<template>
  <div>마이페이지 리뷰 리스트
    <button @click="getMyReviewList">로그인 사용자의 리뷰 리스트</button>
  </div>
</template>

<script>
import axios from 'axios'

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
      const accessToken = localStorage.getItem('accessToken')
      const refreshToken = localStorage.getItem('refreshToken')
      axios({
        method: 'get',
        url: `/api/reviews?page=${this.page}&size=${this.size}&sort=${this.sort}`,
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Refresh': `Bearer ${refreshToken}`,
          'Content-Type': 'application/json;charset=utf-8'
        }
      }).then((result) => {
        console.log(result)
      }).catch((err) => {
        console.log(err)
      })

      // console.log('getMyReviewList')
      // axios.get(`/api/reviews?page=${this.page}&size=${this.size}&sort=${this.sort}`).then((result) => {
      //   console.log(result)
      // }).catch((err) => {
      //   console.log(err)
      // })
    }
  }
}
</script>

<style scoped>

</style>
