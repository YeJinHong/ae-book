<template>
  <div>
    <div>&nbsp;</div>
    <div class="detail">
      <div class="detail-img">
        <img class="book-cover" v-bind:src="notification.coverImageUrl">
      </div>
      <div class="detail-content">
        <p class="detail-content-title">{{ notification.title }}</p>
        <div v-if="notification.notificationType === 'S'">
          <p>알림타입 : 사용자 지정 최저가</p>
          <p>지정가격 : {{ notification.upperLimit }}원</p>
        </div>
        <p v-else >알림타입 : 기본 최저가</p>
        <p>알림 신청일시 : {{ formatDate(notification.createdAt) }}</p>
        <p>알림 수정일시 : {{ formatDate(notification.updatedAt) }}</p>
      </div>
    </div>
    <!-- <button class="ae-btn btn-red" @click="updateNotification({notificationId: notification.id, upperLimit: {upperLimit:notification.upperLimit}})">수정</button> -->
    <div>&nbsp;</div>
  </div>
  </template>

<script>
import { mapState, mapActions } from 'vuex'
const notificationStore = 'notificationStore'

export default {
  name: 'NotificationDetailView',
  data () {
    return {

    }
  },
  computed: {
    ...mapState(notificationStore, ['notification'])
  },
  methods: {
    ...mapActions(notificationStore, ['updateNotification']),
    formatDate (dateString) {
      const date = new Date(dateString)
      const year = date.getFullYear().toString().substr(-2)
      const month = ('0' + (date.getMonth() + 1)).slice(-2)
      const day = ('0' + date.getDate()).slice(-2)
      const hour = ('0' + date.getHours()).slice(-2)
      const minute = ('0' + date.getMinutes()).slice(-2)
      return `${year}.${month}.${day} ${hour}:${minute}`
    }
  }
}
</script>

<style>
.detail {
  display:flex;
}
.detail-img {
  flex-basis: 40%;
  margin-left: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.detail-content {
  flex-basis: 60%;
  text-align: left;
  margin-right: 30px;
}
.book-cover {
  width: 120px;
  box-shadow: 2px 4px 11px 0 rgba(0, 0, 0, 0.25);
}
.detail-content-title {
  font-weight: 800;
  font-size: 22px;
}
</style>
