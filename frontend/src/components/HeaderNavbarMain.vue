<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="light" class="nav">
            <b-navbar-brand href="#">
                <router-link to="/">
                    아이북
                </router-link>
            </b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item>
                        <router-link to="/story">
                                동화책 만들기
                        </router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link to="/painting">
                                색칠공부
                        </router-link>
                    </b-nav-item>
                     <b-nav-item>
                    <router-link to="/book/searchbypicture">
                            사진으로 책 검색
                    </router-link>
                </b-nav-item>
                <b-nav-item>
                    <router-link to="/book/search">
                            제목으로 책 검색
                    </router-link>
                </b-nav-item>
                </b-navbar-nav>
                <!-- 로그인 후 -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-form>
                        <!-- TODO : 로그인 모달창 작성 및 연결 -->
                        <b-button size="sm" @click="loginOut">로그아웃</b-button>
                    </b-nav-form>
                </b-navbar-nav>
                <!-- 로그인 전 -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-form>
                        <b-button size="sm"><router-link :to="{ name: 'Login' }" class="link">로그인 </router-link></b-button>
                    </b-nav-form>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
// TODO: 메인페이지 Navbar 배치 및 디자인 변경
export default {
  name: 'HeaderNavbarMain',
  data () {
    return {
      kakaoLogOutLink: `https://kauth.kakao.com/oauth/logout?client_id=${process.env.VUE_APP_REST_API_KEY}&logout_redirect_uri=${process.env.VUE_APP_LOGOUT_REDIRECT_URI}`,
      islogin: false
    }
  },

  methods: {
    // login () {
    //   this.$router.push({ name: 'Login' })
    // },
    loginOut () {
      console.log('로그아웃')
      if (window.confirm('로그아웃 하시겠습니까?')) {
        localStorage.removeItem('authorization')
        localStorage.removeItem('refresh')
        window.location.href = this.kakaoLogOutLink
      }
    }
  }
}
</script>

<style scoped>

</style>
