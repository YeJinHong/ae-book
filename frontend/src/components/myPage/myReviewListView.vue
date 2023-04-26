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
      // const res = axios({
      //   method: 'get',
      //   url: `http://localhost:8082/reviews?page=${this.page}&size=${this.size}&sort=${this.sort}`,
      //   headers: {
      //     'Authorization': `Bearer ${this.jwtToken}`,
      //     'Content-Type': 'application/json;charset=utf-8'
      //   }
      // })
      // console.log(res)

      console.log('getMyReviewList')
      axios.get(`/reviews?page=${this.page}&size=${this.size}&sort=${this.sort}`).then((result) => {
        console.log(result)
      }).catch((err) => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>

</style>
