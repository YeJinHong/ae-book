<template>
  <div v-if="book">
    <div class="book-container">
      <h1>{{ book.title }}</h1>
      <p>{{ book.author }} | {{ book.publisher }} | {{ book.publishDate }}</p>
      <div class="bar"></div>
      <div class="main-info">
        <div class="book-image-box">
          <img v-bind:src="book.coverImageUrl" class="book-image" />
        </div>
        <div class="sub-info">
          <div class="price-info">
            <span class="price-text">최저가</span><span class="price">{{ book.price | pricePoint }}원</span>
          </div>
          <div class="red-bar"></div>
          <p><span style="font-weight:bold">ISBN</span> {{ book.isbn }}</p>
          <div class="rating"><review-score-view :score="parseInt(book.scoreSum / book.reviewCount)"></review-score-view></div>
          <!-- TODO: 비로그인시 알림 설정 버튼 안보이도록  -->
          <button type="button" class="ae-btn btn-navy" v-if=book.notification @click="cancelNotification(book.notificationId)">알림 신청중</button>
          <!-- <button type="button" class="ae-btn" v-else v-b-modal.modal-save-notification>알림 신청</button> -->
          <button type="button" class="ae-btn" v-else @click="checkLoginAndOpenModal">알림 신청</button>
          <button type="button" class="ae-btn btn-red" @click="onClickRedirect(book.aladinUrl)">구매하러가기 ></button>
        </div>
      </div>
      <div class="bar"></div>
      <p style="font-weight: bold; text-align: left; font-size: 24px; color:var(--ae-navy)">책 소개</p>
      <p>{{ book.description }}</p>
      <div class="bar"></div>
      <p style="font-weight: bold; text-align: left; font-size: 24px; color:var(--ae-navy)">총 리뷰 개수 <span style="color:var(--ae-red)">{{ book.reviewCount }}</span>개
       <button v-if='!isLogin | myReview != null' class='ae-btn btn-red review-btn' @click="showModal()">리뷰 등록 불가</button>
       <button v-else class='ae-btn btn-red review-btn' @click="showModal()">리뷰 등록</button>
      </p>
    </div>
    <div>
      <review-book-list-view :isbn="isbn"></review-book-list-view>
    </div>
    <review-create-modal-view :modalShow="isModalVisible" @close-modal="closeModal">
      <review-create-view :bookInfo="this.bookInfo" @close-modal="closeModal"/>
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
import ReviewScoreView from '../review/ReviewScoreView.vue'

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
    NotificationCreateModalButton,
    ReviewScoreView
  },
  data () {
    return {
      isModalVisible: false,
      isNotificationModalVisible: false,
      upperLimit: 0,
      upperLimitState: null,
      selected: '',
      isNotifications: false,
      bookInfo: null,
      isLogin: false
    }
  },
  created () {
    if (this.book.notification === null) {
      this.notification = false
    } else {
      this.isNotifications = this.book.notification
    }
    console.log(this.isNotifications)
  },
  computed: {
    ...mapState(bookStore, ['book']),
    ...mapState(reviewStore, ['reviewBookList', 'reviewBookPageSetting', 'myReview'])
  },
  beforeMount () {
    console.log('beforeMount')
    this.isLogin = JSON.parse(sessionStorage.getItem('isLoginUser'))
    if (this.isLogin) {
      this.getMyReviewAction(this.isbn)
    }
    console.log('before end ----------------------=----')
  },
  mounted () {
    this.getBookDetail(this.isbn)
    this.book = this.getBook
    this.isNotifications = this.book.notification

    console.log('mounted')
    this.isLogin = JSON.parse(sessionStorage.getItem('isLoginUser'))
    this.getMyReviewAction(this.isbn)
    console.log('mount end ---------------------------')
  },
  methods: {
    ...mapActions(bookStore, ['getBookDetail']),
    ...mapActions(notificationStore, ['notificationSave', 'notificationdelete']),
    ...mapActions(reviewStore, ['getMyReviewAction']),
    onClickRedirect (url) {
      window.open(url, 'blank')
    },
    showModal () {
      this.bookInfo = {
        isbn: this.isbn,
        title: this.book.title.slice(5, this.book.title.length),
        writer: this.book.author
      }
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
    checkLoginAndOpenModal () {
      const login = sessionStorage.getItem('isLoginUser')
      if (!login) {
        alert('로그인이 필요합니다.')
        this.$router.push('/user/login')
      } else {
        this.$refs.modal.show()
      }
    },
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
          this.getBookDetail(this.book.isbn)
        })
      this.$nextTick(() => {
        this.$bvModal.hide('modal-save-notification')
      })
    },
    cancelNotification (notificationId) {
      console.log('삭제')
      console.log(notificationId)
      if (confirm('알림을 취소하시겠습니까?')) {
        this.notificationdelete(notificationId)
          .then(() => {
            this.getBookDetail(this.book.isbn)
          })
      }
    }
  }
}
</script>

<style scoped>
.pagination-container {
  display:flex;
  justify-content: center;
}

.book-container {
  width: 1000px;
  margin: auto;
}

.book-container > h1 {
  color: var(--ae-navy);
  font-size: 45px;
  font-weight: 800;
  text-align: left;
  margin-top: 30px;
}

.book-container > p {
  color: #888888;
  font-size: 15px;
  font-weight:100;
  text-align: left;
}

.book-image {
  width: 300px;
  border: 15px solid white;
  box-shadow: 2px 4px 11px 0 rgba(0, 0, 0, 0.25);
}

.main-info {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.sub-info {
  width: 40%;
  margin-left: 50px;
}

.sub-info > p {
  text-align: left;
  margin-bottom: 0;
}

.price-info {
  text-align: left;
}

.price-text {
  font-size: 40px;
  font-weight: 300;
  text-align: left;
  margin-right: 30px;
}

.price {
  color: var(--ae-red);
  font-size: 40px;
  font-weight: 800;
  text-align: left;
}

.red-bar {
  height: 4px;
  background-color: var(--ae-red);
  width: 400px;
  margin: 10px 0px;
}

.bar {
  height:2px;
  background-color: #E0E0E0;
  margin: 35px 0px;
}

.rating {
  text-align: left;
  font-size:25px;
  margin-bottom:50px;
}
</style>
