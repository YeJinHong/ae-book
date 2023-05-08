<template>
  <b-container fluid="md">

      <b-row class="text-center">
        <b-col cols="6">
          <img src="../../assets/images/login_logo.png" style="width:200px; height:250px;">
        </b-col>
        <b-col cols="4"><span id="font">간편하게<br> 아이북을<br> 이용해보세요.<br></span></b-col>
      </b-row>
      <div>
        <a :href="kakaoLoginLink">
          <img src="../../assets/images/kakao_login_medium_wide.png">
        </a>
      </div>

  </b-container>
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

<style scoped>

#font{
  font-weight: bold;
  font-size:45px;
  color:white;
  text-shadow: 2px 2px 2px gray;
}

</style>
