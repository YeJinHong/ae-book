<template #modal-footer>
  <div class="modal-footer">
    <button class="ae-btn" @click="closeModal">닫기</button>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'NotificationModalButton',
  computed: {
    ...mapState(paintingStore, ['painting'])
  },
  methods: {
    ...mapActions('paintingStore', ['downloadPainting', 'deletePainting', 'updatePaintingTitle']),
    closeModal () {
      this.$emit('close')
    },
    clickDelete (id) {
      this.deletePainting(id)
      this.closeModal()
    }
  }
}
</script>

<style>

</style>
