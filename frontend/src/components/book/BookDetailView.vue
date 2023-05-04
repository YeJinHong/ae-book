<template>
  <div v-if="book">
    {{ book.title }}
    {{ book.author }} | {{ book.publisher }} | {{ book.publishDate }}
    <img v-bind:src="book.coverImageUrl" class="book-image" />
    최저가 {{ book.price }}원
    ISBN {{ book.isbn }}
    XXXXX
    <!-- TODO: 알림 설정  -->
    <button type="button" v-if="isNotifications || isNotification">알림 신청중</button>
    <button type="button" v-else v-b-modal.modal-save-notification>알림 신청</button>
    <button type="button" @click="onClickRedirect(book.aladinUrl)">구매하러가기</button>
    <p>{{ book.description }}</p>
    <!-- TODO: 별점 및 서평 등록 -->
    <button @click="showModal()">리뷰 등록</button>
    <!-- TODO: 서평 수정 및 삭제 -->
    <div>
      <review-book-list-view :isbn="isbn"></review-book-list-view>
    </div>
    <review-create-modal-view :modalShow="isModalVisible" @close-modal="closeModal">
      <review-create-view :isbn="isbn" @close-modal="closeModal"/>
    </review-create-modal-view>

    <!-- 알림 신청 모달 -->
    <!-- <ModalView :modalShow="isNotificationModalVisible" @close-modal="closeNotificationModal">
      <notification-create-view :isbn="isbn"/>
      <notification-create-modal-button @close="closeNotificationModal"></notification-create-modal-button>
    </ModalView> -->
    <b-modal
      id="modal-save-notification"
      ref="modal"
      title="알림 신청"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
      ok-title="신청"
      ok-variant="danger"
      cancel-title="취소"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group v-slot="{ ariaDescribedby }" class="text-left">
          <b-form-group>
            <b-form-radio v-model="selected" :aria-describedby="ariaDescribedby" name="some-radios" value="D">최저가</b-form-radio>
          </b-form-group>

          <b-form-group>
            <b-form-radio v-model="selected" :aria-describedby="ariaDescribedby" name="some-radios" value="S">사용자 지정 최저가</b-form-radio>
            <b-form-group
              label="알림 신청 가격"
              label-for="upperLimit-input"
              invalid-feedback="알림 신청 가격을 입력하세요."
              :state="upperLimitState"
              v-if="selected === 'S'"
              class="d-flex align-items-center mr-3 mt-1"
            >
              <b-form-input
                id="upperLimit-input"
                v-model="upperLimit"
                :state="upperLimitState"
                required
                size="sm"
                class="flex-grow-1 ml-3"
              ></b-form-input>
            </b-form-group>
          </b-form-group>
        </b-form-group>
      </form>
    </b-modal>

  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

import ModalView from '@/components/common/ModalView'
import ReviewBookListView from '../review/ReviewBookListView.vue'
import ReviewCreateView from '../review/ReviewCreateView.vue'
import ReviewCreateModalView from '../review/ReviewCreateModalView.vue'
import NotificationCreateView from '../notification/NotificationCreateView.vue'
import NotificationCreateModalButton from '../notification/NotificationCreateModalButton.vue'

const bookStore = 'bookStore'
const reviewStore = 'reviewStore'
const notificationStore = 'notificationStore'

export default {
  name: 'BookDetailView',
  props: ['isbn'],
  components: {
    ReviewBookListView,
    ReviewCreateView,
    ReviewCreateModalView,
    ModalView,
    NotificationCreateView,
    NotificationCreateModalButton
  },
  data () {
    return {
      isModalVisible: false,
      isNotificationModalVisible: false,
      upperLimit: 0,
      upperLimitState: null,
      selected: '',
      isNotifications: false
    }
  },
  created () {
    this.isNotifications = sessionStorage.getItem('isNotification')
    console.log(this.isNotifications)
  },
  computed: {
    ...mapState(bookStore, ['book']),
    ...mapState(reviewStore, ['reviewBookList', 'reviewBookPageSetting']),
    ...mapState(notificationStore, ['isNotification'])
  },
  mounted () {
    this.getBookDetail(this.isbn)
    this.book = this.getBook
  },
  methods: {
    ...mapActions(bookStore, ['getBookDetail']),
    ...mapActions(notificationStore, ['notificationSave']),
    onClickRedirect (url) {
      window.open(url, 'blank')
    },
    showModal () {
      this.isModalVisible = true
    },
    // showNotificationModal () {
    //   this.isNotificationModalVisible = true
    // },
    closeModal () {
      this.isModalVisible = false
    },
    // closeNotificationModal () {
    //   this.isNotificationModalVisible = false
    // },
    checkFormValidity () {
      const valid = this.$refs.form.checkValidity()
      this.upperLimitState = valid
      return valid
    },
    resetModal () {
      this.upperLimit = 0
      this.upperLimitState = null
    },
    handleOk (bvModalEvent) {
      bvModalEvent.preventDefault()
      this.handleSubmit()
    },
    handleSubmit () {
      // Exit when the form isn't valid
      if (!this.checkFormValidity()) {
        return
      }

      const data = {
        isbn: this.isbn,
        upperLimit: this.upperLimit,
        notificationType: this.selected
      }
      console.log(data)

      this.notificationSave(data)
        .then(() => {
          alert('알림이 성공적으로 등록되었습니다!')
        })
      this.$nextTick(() => {
        this.$bvModal.hide('modal-save-notification')
      })
    }
  }
}
</script>

<style scoped>
.pagination-container {
  display:flex;
  justify-content: center;
}
</style>
