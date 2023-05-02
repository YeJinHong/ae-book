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
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
const bookStore = 'bookStore'

export default {
  name: 'BookDetailView',
  props: ['isbn'],
  computed: {
    ...mapState(bookStore, ['book'])
  },
  created () {
    this.getBookDetail(this.isbn)
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
