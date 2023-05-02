<template>
  <div>
    <h1>그림장</h1>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
    <PaintingDetailView/>
    </ModalView>
    <div
        v-for="painting in paintingList"
        :key="painting.id"
        @click="showModal(painting.id)"
      >
      <img v-bind:src="painting.fileUrl" class="painting-image">
      {{ painting.title }}
    </div>
</div>

</template>

<script>
import { mapActions, mapState } from 'vuex'
import PaintingDetailView from '@/components/painting/PaintingDetailView.vue'
import ModalView from '@/components/common/ModalView.vue'
const paintingStore = 'paintingStore'

export default {
  name: 'MyPaintingListView',
  components: {
    ModalView,
    PaintingDetailView
  },
  data () {
    return {
      isModalVisible: false
    }
  },
  computed: {
    ...mapState(paintingStore, ['paintingList'])
  },
  mounted () {
    this.getPaintingList('COLOR')
  },
  methods: {
    ...mapActions(paintingStore, ['getPaintingList', 'getPaintingDetail']),
    showModal (paintingId) {
      this.getPaintingDetail(paintingId)
      this.isModalVisible = true
    },
    closeModal () {
      this.isModalVisible = false
      // this.clearStoryId()
    }
  }
}
</script>

<style>

</style>
