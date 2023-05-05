<template>
  <div>
    <h1 class="subject">알림 신청한 책 목록</h1>
    <div style="height:2px; background-color: #E0E0E0;"></div>
    <ModalView :modalShow="isModalVisible" @close-modal="closeModal">
      <notification-detail-view/>
      <notification-modal-button :id="id" @close="closeModal"></notification-modal-button>
    </ModalView>
    <div class="carousel-container">
      <img-carousel-view :items="notificationList" :chunkSize="3" @moveTo="showModal"></img-carousel-view>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import ModalView from '@/components/common/ModalView.vue'
import ImgCarouselView from '@/components/common/list/ImgCarouselView.vue'
import NotificationDetailView from '@/components/notification/NotificationDetailView.vue'
import NotificationModalButton from '@/components/notification/NotificationModalButton.vue'
const notificationStore = 'notificationStore'

export default {
  name: 'MyNotificationBookListView',
  components: {
    ModalView,
    ImgCarouselView,
    NotificationDetailView,
    NotificationModalButton
  },
  data () {
    return {
      isModalVisible: false,
      id: null
    }
  },
  computed: {
    ...mapState(notificationStore, ['notificationList'])
  },
  mounted () {
    this.getBookNotificationList()
  },
  methods: {
    ...mapActions(notificationStore, ['getBookNotificationList', 'getBookNotificationDetail']),
    showModal (notificationId) {
      this.id = notificationId
      this.getBookNotificationDetail(notificationId)
      this.isModalVisible = true
    },
    closeModal () {
      this.isModalVisible = false
      this.getBookNotificationList()
    }
  }

}
</script>

<style>
.subject{
  text-align: left;
  font-size: 24px;
  font-weight: 800;
}
</style>
