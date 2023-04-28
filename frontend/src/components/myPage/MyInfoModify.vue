<template>
  <div>
    <div>사용자 정보 변경(임시 -> 추후 모달로 변경)</div>

    <div>
      <form id="form-modifyInfo" method="post" action="">
        <div class="input-group mb-3"> 닉네임
          <input
            type="text"
            class="form-control"
            name="nickname"
            id="nickname"
            v-model="user.nickname"
            placeholder="새로운 닉네임을 입력해주세요"
          />
        </div>

        <div class="input-group mb-3"> 이미지
          <input type="file"
            class="form-control"
            name="profileUrl"
            id="profileUrl"
            accept="image/*"
          />
        </div>

        <div class="d-grid col-12 mx-auto">
          <b-button
            type="submit"
            variant="secondary"
            @click.prevent="modifyUser"
            id="btn-modify"
          >
            수정
          </b-button>
          &nbsp;
          <router-link :to="{ name: 'MyPage' }" class="link"><b-button variant="secondary" type="button">취소</b-button></router-link>
        </div>
      </form>
    </div>

  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'MyInfoModify',
  data () {
    return {
      nickname: '',
      profileUrl: null
    }
  },
  created () {
    // console.log(this.isLogin)
    this.isLoginUser = sessionStorage.getItem('isLoginUser')
    this.nickname = this.user.nickname
  },
  computed: {
    ...mapState(userStore, ['isLogin', 'isLoginError', 'user']),
    user () {
      return JSON.parse(sessionStorage.getItem('userInfo'))
    }
  },

  methods: {
    ...mapActions(userStore, ['userUpdate']),
    modifyUser () {
      console.log('정보수정')
    }
  }

}
</script>

<style>

</style>
