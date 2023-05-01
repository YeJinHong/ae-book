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
            ref="fileInput"
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
          <router-link :to="{ name: 'MyInfo' }" class="link"><b-button variant="secondary" type="button">취소</b-button></router-link>
        </div>
      </form>
    </div>

  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex'
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
    this.isLoginUser = sessionStorage.getItem('isLoginUser')
    this.nickname = this.user.nickname
  },
  computed: {
    ...mapState(userStore, ['isLogin', 'isLoginError', 'user']),
    ...mapGetters(userStore, ['getUserInfo']),
    user () {
      return JSON.parse(sessionStorage.getItem('userInfo'))
    }
  },

  methods: {
    ...mapActions(userStore, ['userUpdate']),
    async modifyUser () {
      console.log('정보수정')

      let data = {
        nickname: this.user.nickname
      }

      let formData = new FormData()
      if (!this.$refs.fileInput.files[0]) {
        formData.append('imgUrl', this.$refs.fileInput.files[0])
      } else {
        formData.append('imgUrl', null)
      }
      formData.append('content', new Blob([JSON.stringify(data)], {type: 'application/json'}))

      console.log(formData)
      await this.userUpdate(formData)
      this.$router.push({ name: 'MyInfo' })
    }
  }

}
</script>

<style>

</style>
