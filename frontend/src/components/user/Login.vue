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
    kakaoLogin () {
      console.log(this.kakaoCode.code)

      // axios.get(`/api/auth/login?code=${this.kakaoCode.code}`).then((result) => {
      //   console.log(result)
      //   // console.log(result.headers.authorization)
      //   // console.log(result.headers.refresh)
      //   localStorage.setItem('authorization', result.headers.authorization)
      //   localStorage.setItem('refresh', result.headers.refresh)
      //   this.$router.push({ name: 'Main' })
      // })
    }
  }

}
</script>

<style>

</style>
