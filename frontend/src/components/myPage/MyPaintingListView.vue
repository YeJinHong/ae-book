<template>
  <div>
    <h1>그림장</h1>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
    <painting-detail-view/>
    </ModalView>
    <img-carousel-view :items="paintingList" :chunkSize="3"></img-carousel-view>
</div>

</template>

<script>
import { mapActions, mapState } from 'vuex'
import PaintingDetailView from '@/components/painting/PaintingDetailView.vue'
import ModalView from '@/components/common/ModalView.vue'
import ImgCarouselView from '@/components/common/list/ImgCarouselView.vue'
const paintingStore = 'paintingStore'

export default {
  name: 'MyPaintingListView',
  components: {
    ModalView,
    PaintingDetailView,
    ImgCarouselView
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
