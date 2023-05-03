<template>
  <div v-if="book">
    {{ book.title }}
    {{ book.author }} | {{ book.publisher }} | {{ book.publishDate }}
    <img v-bind:src="book.coverImageUrl" class="book-image" />
    최저가 {{ book.price }}원
    ISBN {{ book.isbn }}
    XXXXX
    <button>알림신청</button>
    <button type="button" @click="onClickRedirect(book.aladinUrl)">구매하러가기</button>
    <p>{{ book.description }}</p>
    <!-- TODO: 별점 및 서평 등록 -->
    <button @click="showModal()">리뷰 등록</button>
    <div class="review-book-list">
      <review-book-list-view :isbn="isbn"></review-book-list-view>
    </div>
    <review-create-modal-view :modalShow="isModalVisible" @close-modal="closeModal">
      <review-create-view :isbn="isbn" @close-modal="closeModal"/>
    </review-create-modal-view>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

import ReviewBookListView from '../review/ReviewBookListView.vue'
import ReviewCreateView from '../review/ReviewCreateView.vue'
import ReviewCreateModalView from '../review/ReviewCreateModalView.vue'

const bookStore = 'bookStore'
const reviewStore = 'reviewStore'

export default {
  name: 'BookDetailView',
  props: ['isbn'],
  components: {
    ReviewBookListView,
    ReviewCreateView,
    ReviewCreateModalView
  },
  data () {
    return {
      isModalVisible: false
    }
  },
  computed: {
    ...mapState(bookStore, ['book']),
    ...mapState(reviewStore, ['reviewBookList', 'reviewBookPageSetting'])
  },
  mounted () {
    this.getBookDetail(this.isbn)
    this.book = this.getBook
  },
  methods: {
    ...mapActions(bookStore, ['getBookDetail']),
    onClickRedirect (url) {
      window.open(url, 'blank')
    },
    showModal () {
      this.isModalVisible = true
    },
    closeModal () {
      this.isModalVisible = false
    }
  }
}
</script>

<style scoped>
.review-book-list {
  display: flex;
  width: 50%;
  margin: auto
}
.pagination-container {
  display:flex;
  justify-content: center;
}
</style>
