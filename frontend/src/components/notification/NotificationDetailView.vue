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
          <p v-if="modify">
            지정가격 : <input type="text" v-model="notification.upperLimit"> &nbsp;
            <button class="ae-btn btn-modifiy" @click="cancelModify">취소</button> &nbsp;
            <button class="ae-btn btn-navy btn-modifiy" @click="submitNotification(notification.upperLimit)">수정</button> &nbsp;
          </p>
          <p v-else>
            지정가격 : {{ notification.upperLimit }}원 &nbsp;
            <button class="ae-btn btn-navy btn-modifiy" @click="modifyNotification">수정</button>
          </p>
        </div>
        <p v-else >알림타입 : 기본 최저가</p>
        <p>알림 신청일시 : {{ formatDate(notification.createdAt) }}</p>
        <p>알림 수정일시 : {{ formatDate(notification.updatedAt) }}</p>
      </div>
    </div>
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
      modify: false,
      upperLimit: 0
    }
  },
  computed: {
    ...mapState(notificationStore, ['notification'])
  },
  methods: {
    ...mapActions(notificationStore, ['notificationUpdate']),
    formatDate (dateString) {
      const date = new Date(dateString)
      const year = date.getFullYear().toString().substr(-2)
      const month = ('0' + (date.getMonth() + 1)).slice(-2)
      const day = ('0' + date.getDate()).slice(-2)
      const hour = ('0' + date.getHours()).slice(-2)
      const minute = ('0' + date.getMinutes()).slice(-2)
      return `${year}.${month}.${day} ${hour}:${minute}`
    },
    modifyNotification () {
      this.modify = true
    },
    cancelModify () {
      this.modify = false
    },
    submitNotification (price) {
      if (!isNaN(price)) {
        const payload = {
          notificationId: this.notification.id,
          content: {
            upperLimit: price,
            notificationType: this.notification.notificationType
          }
        }
        this.notificationUpdate(payload)
          .then(() => {
            // alert('알림이 성공적으로 수정되었습니다.')
            this.modify = false
          })
      } else {
        alert('숫자만 입력할 수 있습니다.')
      }
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
  box-shadow: 2px 4px 11px 0 rgba(0, 0, 0, 0.25);
}
.detail-content-title {
  font-weight: 800;
  font-size: 22px;
}
.btn-modifiy{
  float: right;
  font-size: 0.8em;
  padding: 0.4em 0.6em;
}
</style>
