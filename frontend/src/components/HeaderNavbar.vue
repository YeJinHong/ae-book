<template>
    <div>
        <b-navbar toggleable="lg" id="headerNav">
            <b-navbar-brand href="#">
                <router-link to="/">
                  <img src="../assets/images/aebook_logo.png" width="100" height="50">
                </router-link>
            </b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav id="navbarNav">
                <b-nav-item>
                    <router-link to="/story">
                      <div class="nav-menu first">
                        <b-nav-text>동화책 만들기</b-nav-text>
                      </div>
                    </router-link>
                </b-nav-item>
                <b-nav-item>
                    <router-link to="/painting">
                      <div class="nav-menu second">
                        <b-nav-text>색칠공부</b-nav-text>
                      </div>
                    </router-link>
                </b-nav-item>
                <b-nav-item>
                    <router-link to="/book/searchbypicture">
                      <div class="nav-menu third">
                        <b-nav-text>사진으로 책 검색</b-nav-text>
                      </div>
                    </router-link>
                </b-nav-item>
                <b-nav-item>
                    <router-link to="/book/search">
                      <div class="nav-menu four">
                        <b-nav-text>제목으로 책 검색</b-nav-text>
                      </div>
                    </router-link>
                </b-nav-item>
            </b-navbar-nav>
            <!-- 로그인 후, 보여주는 정보는 추후 다시 결정, 마이페이지는 임시 배치 -->
            <b-navbar-nav class="ml-auto" v-if=isLoginUser>
                    <b-nav-form>
                        {{ user.userId }} | {{ user.nickname }}
                        <b-button size="sm" @click="logout" class="btn-red">로그아웃</b-button>
                        <router-link :to="{ name: 'MyInfo' }" class="link">
                          <b-button size="sm" class="btn-navy">마이페이지</b-button></router-link>
                    </b-nav-form>
            </b-navbar-nav>
            <!-- 로그인 전 -->
            <b-navbar-nav class="ml-auto" v-else>
                <b-nav-form>
                    <router-link :to="{ name: 'Login' }" class="link"><b-button size="sm" class="btn-red">로그인</b-button></router-link>
                </b-nav-form>
            </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
// TODO: 메인페이지 Navbar 배치 변경.
import { mapState, mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'HeaderNavbar',
  data () {
    return {
      kakaoLogOutLink: `https://kauth.kakao.com/oauth/logout?client_id=${process.env.VUE_APP_REST_API_KEY}&logout_redirect_uri=${process.env.VUE_APP_LOGOUT_REDIRECT_URI}`,
      isLoginUser: false
    }
  },
  created () {
    this.isLoginUser = sessionStorage.getItem('isLoginUser')
  },
  computed: {
    ...mapState(userStore, ['isLogin', 'isLoginError', 'user']),
    user () {
      return JSON.parse(sessionStorage.getItem('userInfo'))
    }
  },

  methods: {
    ...mapActions(userStore, ['userLogout']),
    logout () {
      if (window.confirm('로그아웃을 하시겠습니까?')) {
        this.userLogout().then(() => {
          window.location.href = this.kakaoLogOutLink
        })
      }
    }
  }
}
</script>

<style scoped>
.nav-menu{
  width: 200px;
  height: 50px;
  background-color: red;
  border-radius: 30px 30px 0px 0px;
  border: 10px solid white;
  border-bottom: 0px;
  position:relative;
  padding: 0px;
  text-align: center;
}

#navbarNav{
  margin-bottom: -24px;
}

.nav-menu.first {
  background-color: var(--menu-blue);
}

.nav-menu.second {
  background-color: var(--menu-red);
}

.nav-menu.third {
  background-color: var(--menu-yellow);
}

.nav-menu.fourth {
  background-color: var(--menu-green);
}
</style>
