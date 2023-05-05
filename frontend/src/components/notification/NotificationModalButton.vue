<template #modal-footer>
  <div class="modal-footer">
    <button class="ae-btn btn-navy">수정</button>
    <button class="ae-btn btn-red">삭제</button>
    <button class="ae-btn" @click="closeModal">닫기</button>
  </div>
</template>
<script>
import { mapState } from 'vuex'
const notificationStore = 'notificationStore'

export default {
  name: 'NotificationModalButton',
  computed: {
    ...mapState(notificationStore, ['notification'])
  },
  methods: {
    closeModal () {
      this.$emit('close')
    }
  }
}
</script>

<style>

</style>
