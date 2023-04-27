<template>
  <div>
  </div>

</template>

<script>
import axios from 'axios'
export default {
  name: 'oauth',
  data () {
    return {
      // isLoginError: false,
      kakaoCode: ''
    }
  },

  created () {
    // console.log(this.$route.query) // 경로 확인
    this.kakaoCode = this.$route.query
    // console.log(this.kakaoCode.code)
    // console.log(typeof this.kakaoCode.code)

    if (this.kakaoCode.code === undefined) {
      console.log('undifined')
    } else {
      console.log('axios')
      axios.get(`/api/auth/login?code=${this.kakaoCode.code}`).then((result) => {
        console.log(result)
        // console.log(result.headers.authorization)
        // console.log(result.headers.refresh)
        localStorage.setItem('authorization', result.headers.authorization)
        localStorage.setItem('refresh', result.headers.refresh)
        this.$router.push({ name: 'Main' })
      })
    }
  }

}
</script>

<style>

</style>
