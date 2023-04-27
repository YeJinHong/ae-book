<template>
    <div>
    <header-navbar-main></header-navbar-main>
    <div> This is Main</div>
    <!-- TODO : 각기 다음에 필요한 컴포넌트는 components/{domain} 하위에 작성 -->
    <!-- TODO : 새로 들어온 책 콘텐츠 케러셀 컴포넌트-->
    <!-- TODO : 최근 리뷰 컨텐츠 케러셀 컴포넌트 -->
    <button @click="getLatestReviewList">최신 리뷰 리스트</button>
    <!-- TODO : 우리 아이들 작품(만든 동화) 리스트 컴포넌트 -->
    <footer-info></footer-info>
  </div>
</template>

<script>
import axios from 'axios'
import HeaderNavbarMain from '@/components/HeaderNavbarMain'
import FooterInfo from '@/components/FooterInfo'

// 로그인 기능 생성 전 테스트용 토큰
localStorage.setItem('accessToken', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiZXhwIjoxNjgzMDc4OTM2fQ.SrAoFJY5peY6zNeGx7IHN2dqkQNztNKWleraEorOwDR1iERuHZxLQkpezcTZDxS4sHnO5sO4vDWpWQtu5himvA')
localStorage.setItem('refreshToken', 'eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODM2ODM3MzZ9.aZ7Y-HjrOIVKOvjG_FXLiqQA_kElZ0Z4l9OPnoV_YwrTH_4oSAwo9ldw4ut0JbDYgB_mpGCuafLTdW6SK3aQTQ')

export default {
  name: 'AppMain',
  components: {
    HeaderNavbarMain,
    FooterInfo
  },
  methods: {
    getLatestReviewList () {
      axios.get(`/reviews/latest`).then((result) => {
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
