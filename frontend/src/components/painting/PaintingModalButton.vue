<template #modal-footer>
  <div class="modal-footer">
    <button class="ae-btn btn-navy" @click="clickDelete(painting.id)">삭제</button>
    <button class="ae-btn" @click="closeModal">닫기</button>
    <button class="ae-btn btn-red" @click="downloadPainting(painting.id)">다운로드</button>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'PaintingModalButton',
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
