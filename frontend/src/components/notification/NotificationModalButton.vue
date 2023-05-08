<template #modal-footer>
  <div class="modal-footer">
    <button class="ae-btn btn-red" @click="cancelNotification(notificationId)">삭제</button>
    <button class="ae-btn" @click="closeModal">닫기</button>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
const notificationStore = 'notificationStore'

export default {
  name: 'NotificationModalButton',
  props: ['notificationId', 'request'],
  created () {

  },
  computed: {
    ...mapState(notificationStore, ['notification', 'notificationList'])
  },
  mounted () {
    this.getBookNotificationList(this.request)
  },
  methods: {
    ...mapActions(notificationStore, ['getBookNotificationDetail', 'notificationdelete', 'getBookNotificationList']),
    closeModal () {
      this.$emit('close')
    },
    modifiyNotification () {
      console.log('수정')
    },
    cancelNotification (notificationId) {
      console.log('삭제')
      console.log(notificationId)
      if (confirm('알림을 취소하시겠습니까?')) {
        this.notificationdelete(notificationId)
          .then(() => {
            this.closeModal()
          })
      }
    }
  }
}
</script>

<style>

</style>
