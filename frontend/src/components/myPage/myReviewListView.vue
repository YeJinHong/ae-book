<template>
  <div>
    마이페이지 리뷰 리스트
    <button @click="getMyReviewList">로그인 사용자의 리뷰 리스트</button>
  </div>
</template>

<script>
// import axios from 'axios'
import api from '@/api/auth'

// 로그인 완성 전 테스트 토큰
// 희주 계정임 ㅎㅎ;;
localStorage.setItem('accessToken', 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNjgzMTMyMzYzfQ.WPLUo-Z_VA76PZQuI2q0xweAeysV22810zmkwXS_ZOrfa7YCgFPeqLkTkSClKjkGy8jWmflUXg_cCsPGQ4q-qQ')
localStorage.setItem('refreshToken', 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODM3MzcxNjN9.CRd4wSz32lYp7qee7vVqEBrnO_aZNYvzWzrcniw9BxJrwAqkmdk61Cc_xAKkqjU7f9Zb0hiimc17QqMwCGDW4g')

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
