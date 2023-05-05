<template>
  <div>
    <input type="text" v-model="notification.title">
    <button class="ae-btn btn-red" @click="updateNotification({notificationId: notification.id, upperLimit: {upperLimit:notification.upperLimit}})">수정</button>
    <img v-bind:src="notification.coverImageUrl">
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
    ...mapActions(notificationStore, ['updateNotification'])
  }
}
</script>

  <style>

  </style>
