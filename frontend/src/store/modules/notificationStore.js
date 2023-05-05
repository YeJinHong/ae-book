import { saveNotification, getNotificationList, getNotificationDetail, deletetNotification } from '@/api/notification'

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
          // console.log(data)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getBookNotificationList ({ commit }) {
      await getNotificationList()
        .then(({ data }) => {
          console.log(data)
          commit('SET_NOTIFICATION_LIST', data.result.content)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getBookNotificationDetail ({ commit }, notificationId) {
      await getNotificationDetail(notificationId)
        .then(({ data }) => {
          console.log(data)
          commit('SET_NOTIFICATION', data.result)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async notificationdelete ({ commit }, notificationId) {
      console.log('삭제')
      await deletetNotification(notificationId)
        .then(({ data }) => {
          console.log(data)
          commit('RESET_NOTIFICATION_LIST')
          this.getBookNotificationList()
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}

export default notificationStore
