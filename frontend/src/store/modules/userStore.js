import { login } from '@/api/user'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    user: null,
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
    async userConfirm ({commit}, kakaoCode) {
      await login(
        kakaoCode,
        ({ data }) => {
          if (data.statusText === 'OK') {
            let accessToken = data['authorization']
            let refreshToken = data['refresh']

            commit('SET_USER_INFO', data)
            commit('SET_IS_LOGIN', true)
            commit('SET_IS_LOGIN_ERROR', false)
            commit('SET_IS_VALID_TOKEN', true)

            localStorage.setItem('authorization', accessToken)
            localStorage.setItem('refresh', refreshToken)
          } else {
            commit('SET_IS_LOGIN', false)
            commit('SET_IS_LOGIN_ERROR', true)
            commit('SET_IS_VALID_TOKEN', false)
          }
        },
        (error) => {
          console.log(error)
        })
    }

  }

}

export default userStore
