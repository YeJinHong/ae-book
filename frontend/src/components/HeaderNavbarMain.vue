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
                <!-- 로그인 후, 보여주는 정보는 추후 다시 결정, 마이페이지는 임시 배치 -->
                <b-navbar-nav class="ml-auto" v-if=isLoginUser>
                    <b-nav-form>
                        {{ user.userId }} | {{ user.nickname }}
                        <b-button size="sm" @click="logout">로그아웃</b-button>
                        <router-link :to="{ name: 'MyPage' }" class="link"><b-button size="sm">마이페이지</b-button></router-link>
                    </b-nav-form>
                </b-navbar-nav>
                <!-- 로그인 전 -->
                <b-navbar-nav class="ml-auto" v-else>
                    <b-nav-form>
                        <router-link :to="{ name: 'Login' }" class="link"><b-button size="sm">로그인</b-button></router-link>
                    </b-nav-form>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
// TODO: 메인페이지 Navbar 배치 및 디자인 변경
import { mapState, mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'HeaderNavbarMain',
  data () {
    return {
      kakaoLogOutLink: `https://kauth.kakao.com/oauth/logout?client_id=${process.env.VUE_APP_REST_API_KEY}&logout_redirect_uri=${process.env.VUE_APP_LOGOUT_REDIRECT_URI}`,
      isLoginUser: false
    }
  },
  created () {
    this.isLoginUser = localStorage.getItem('isLoginUser')
  },
  computed: {
    ...mapState(userStore, ['isLogin', 'isLoginError', 'user']),
    user () {
      return JSON.parse(localStorage.getItem('userInfo'))
    }
  },

  methods: {
    ...mapActions(userStore, ['userLogout']),
    logout () {
      if (window.confirm('로그아웃  하시겠습니까?')) {
        this.userlogout().then(() => {
          window.location.href = this.kakaoLogOutLink
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
