import { login, logout } from '@/api/user'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    user: {},
    isValidToken: false
  },
  getter: {
    checkUserInfo: function (state) {
      return state.user
    }
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken
    },
    SET_USER_INFO: (state, user) => {
      state.isLogin = true
      state.user = user
    }
  },
  actions: {
    async userLogin ({ commit }, kakaoCode) {
      try {
        const data = await login(kakaoCode)
        if (data.statusText === 'OK') {
          let accessToken = data.headers['authorization']
          let refreshToken = data.headers['refresh']

          const user = {
            userId: data.data.userId,
            nickname: data.data.nickname,
            profileUrl: data.data.profileUrl
          }
          commit('SET_USER_INFO', user)
          commit('SET_IS_LOGIN', true)
          commit('SET_IS_LOGIN_ERROR', false)

          localStorage.setItem('accessToken', accessToken)
          localStorage.setItem('refreshToken', refreshToken)
          localStorage.setItem('IsLogin', true)
          localStorage.setItem('userInfo', JSON.stringify(user))
          this.$router.push({ name: 'Main' })
        } else {
          commit('SET_IS_LOGIN', false)
          commit('SET_IS_LOGIN_ERROR', true)

          localStorage.setItem('IsLogin', false)
        }
      } catch (error) {
        console.log(error)
      }
    },
    async userLogout ({ commit }) {
      try {
        const data = await logout()
        if (data.statusText === 'OK') {
          console.log(data)
          commit('SET_USER_INFO', null)
          commit('SET_IS_LOGIN', false)

          localStorage.removeItem('accessToken')
          localStorage.removeItem('refreshToken')
          localStorage.setItem('IsLogin', false)

          this.$router.push({ name: 'Main' })
        } else {
          console.log('로그아웃 오류')
          commit('SET_IS_LOGIN', true)

          localStorage.setItem('IsLogin', true)
        }
      } catch (error) {
        console.log(error)
      }
    }

  }

}

export default userStore
