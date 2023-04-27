<template>
  <div>
    {{ book.title }}
    {{ book.author }} | {{ book.publisher }} | {{ book.publishDate }}
    <img v-bind:src="book.coverImageUrl" class="book-image" />
    최저가 {{ book.price }}원
    ISBN {{ book.isbn }}
    XXXXX
    <button>알림신청</button>
    <button type="button" @click="onClickRedirect(book.aladinUrl)">구매하러가기</button>
    <p>{{ book.description }}</p>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
const bookStore = 'bookStore'

export default {
  name: 'BookDetailView',
  props: ['isbn'],
  data () {
    return {
      book: Object
    }
  },
  computed: {
    ...mapGetters(bookStore, ['getBook'])
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
