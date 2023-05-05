import { saveNotification, getNotificationList, getNotificationDetail, updateNotification } from '@/api/notification'

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
    },
    UPDATE_NOTIFICATION: (state, data) => {
      state.notification.upperLimit = data.upperLimit
    }
  },
  actions: {
    async notificationSave ({ commit }, data) {
      await saveNotification(data)
        .then(({ data }) => {
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getBookNotificationList ({ commit }) {
      await getNotificationList()
        .then(({ data }) => {
          commit('SET_NOTIFICATION_LIST', data.result.content)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getBookNotificationDetail ({ commit }, notificationId) {
      await getNotificationDetail(notificationId)
        .then(({ data }) => {
          commit('SET_NOTIFICATION', data.result)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async notificationUpdate ({ commit }, payload) {
      await updateNotification(payload)
        .then(({ data }) => {
          commit('UPDATE_NOTIFICATION', data.result)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}

export default notificationStore
