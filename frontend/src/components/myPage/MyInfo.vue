<template>
  <div>
    <div>사용자 정보</div>
    <div id="img"><img :src=user.profileUrl></div>
    <div>{{ user.nickname }}</div>
    <!-- <b-button size="sm" @click="modifyUserInfo">사용자 정보 수정</b-button> -->
    <!-- 임시로 page를 생성하고 모달로 변경하도록 하겠습니다,,,-->
    <router-link :to="{ name: 'MyInfoModify' }" class="link"><b-button size="sm">사용자 정보 수정</b-button></router-link>&nbsp;
    <b-button size="sm" @click="deleteUserInfo">사용자 탈퇴</b-button>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
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
    modifyUserInfo () {
      console.log('사용자 정보 수정')
    },
    deleteUserInfo () {
      console.log('사용자 탈퇴')
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
