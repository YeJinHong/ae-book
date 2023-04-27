<template>
  <div>
    <div>로그인</div>
    <!-- <b-button size="sm"><img src="../../assets/images/kakao_login_medium_wide.png" alt="카카오 로그인"></b-button> -->
    <div>
      <a href="https://kauth.kakao.com/oauth/authorize?client_id=c8552298d81db44a10187c0ca23800c8&redirect_uri=http://localhost:3000/user/oauth&response_type=code">
      <img src="../../assets/images/kakao_login_medium_wide.png">
    </a>
    </div>

  </div>
</template>

<script>
// const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=c8552298d81db44a10187c0ca23800c8&redirect_uri=http://localhost:3000&response_type=code`
// import axios from 'axios'
export default {
  name: 'login',
  data () {
    return {
      kakaoCode: ''
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
