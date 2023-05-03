<template>
  <div>
    <h1 class="subject">그림장</h1>
    <div style="height:2px; background-color: #E0E0E0;"></div>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
    <painting-detail-view/>
    <painting-modal-button @close="closeModal"></painting-modal-button>
    </ModalView>
    <div class="carousel-container">
      <img-carousel-view :items="paintingList" :chunkSize="3" @moveTo="showModal"></img-carousel-view>
    </div>
</div>

</template>

<script>
import { mapActions, mapState } from 'vuex'
import PaintingDetailView from '@/components/painting/PaintingDetailView.vue'
import ModalView from '@/components/common/ModalView.vue'
import ImgCarouselView from '@/components/common/list/ImgCarouselView.vue'
import PaintingModalButton from '../painting/PaintingModalButton.vue'
const paintingStore = 'paintingStore'

export default {
  name: 'MyPaintingListView',
  components: {
    ModalView,
    PaintingDetailView,
    ImgCarouselView,
    PaintingModalButton
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

<style scoped>
.subject{
  text-align: left;
  font-size: 24px;
  font-weight: 800;
}
</style>
