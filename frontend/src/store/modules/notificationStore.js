import { saveNotification } from '@/api/notification'

const notificationStore = {
  namespaced: true,
  state: {
    isNotification: false,
    notification: null,
    notificationList: []
  },
  getters: {
    getIsNotification: state => {
      return state.isNotification
    },
    getNotification: state => {
      return state.notification
    },
    getNotificationList: state => {
      return state.notificationList
    }
  },
  mutations: {
    SET_IS_NOTIFICATION: (state, data) => {
      state.isNotification = data
    },
    SET_NOTIFICATION: (state, data) => {
      state.notification = data
    },
    SET_NOTIFICATION_LIST: (state, data) => {
      state.notificationList = data
    },
    RESET_NOTIFICATION_LIST (state) {
      state.notificationList = []
    }
  },
  actions: {
    async notificationSave ({ commit }, data) {
      await saveNotification(data)
        .then(({ data }) => {
          commit('SET_IS_NOTIFICATION', true)
          sessionStorage.setItem('isNotification', true)
          commit('SET_NOTIFICATION', data.result)
          console.log(data)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}

export default notificationStore
