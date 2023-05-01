<template>
  <div>
    <div>로그인</div>
    <div>
      <a :href="kakaoLoginLink">
        <img src="../../assets/images/kakao_login_medium_wide.png">
      </a>
    </div>

  </div>
</template>

<script>
// import axios from 'axios'
export default {
  name: 'login',
  data () {
    return {
      kakaoCode: '',
      kakaoLoginLink: `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_REDIRECT_URI}&response_type=code`
    }
  },
  created () {
    this.kakaoCode = this.$route.query
  },
  methods: {
  }

}
</script>

<style>

</style>
