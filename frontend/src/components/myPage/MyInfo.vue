<template>
  <div>
    <div>사용자 정보</div>
    <div id="img"><img :src=user.profileUrl></div>
    <div>{{ user.nickname }}</div>
    <!-- <b-button size="sm" @click="modifyUserInfo">사용자 정보 수정</b-button> -->
    <!-- TODO: 임시로 page를 생성하고 모달로 변경하도록 하겠습니다,,,-->
    <router-link :to="{ name: 'MyInfoModify' }" class="link"><b-button size="sm">사용자 정보 수정</b-button></router-link>&nbsp;
    <b-button size="sm" @click="deleteUserInfo">사용자 탈퇴</b-button>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'MyInfo',
  created () {
    this.isLoginUser = sessionStorage.getItem('isLoginUser')
    this.user = this.getUserInfo
    console.log(this.user)
  },
  computed: {
    ...mapState(userStore, ['isLogin', 'isLoginError', 'user']),
    ...mapGetters(userStore, ['getUserInfo']),
    user () {
      return JSON.parse(sessionStorage.getItem('userInfo'))
    }
  },

  methods: {
    ...mapActions(userStore, ['userDelete', 'userLogout']),
    deleteUserInfo () {
      console.log('사용자 탈퇴')
      if (confirm('정말로 탈퇴하시겠습니까?')) {
        this.userDelete()
          .then(() => {
            console.log('사용자 탈퇴 완료')
            return this.userLogout()
          })
          .then(() => {
            console.log('사용자 로그아웃 완료')
            this.$router.push({ name: 'Main' })
          })
          .catch((error) => {
            console.error(error)
          })
      }
    }
  }

}
</script>

<style>
#img img {
  width: 200px;
  height: 200px;
}
</style>
