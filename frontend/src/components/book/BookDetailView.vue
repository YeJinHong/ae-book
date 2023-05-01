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
    <h3>isbn은 .. {{ this.isbn }}이다 ..</h3>
    <review-book-list-view :isbn="isbn"></review-book-list-view>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import ReviewBookListView from '../review/ReviewBookListView.vue'
const bookStore = 'bookStore'

export default {
  name: 'BookDetailView',
  props: ['isbn'],
  components: {
    ReviewBookListView
  },
  computed: {
    ...mapState(bookStore, ['book'])
  },
  mounted () {
    this.getBookDetail(this.isbn)
    this.book = this.getBook
  },
  methods: {
    ...mapActions(bookStore, ['getBookDetail']),
    onClickRedirect (url) {
      window.open(url, 'blank')
    }
  }
}
</script>

<style>

</style>
