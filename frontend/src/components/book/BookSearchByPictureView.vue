<template>
  <div>사진(ISBN)으로 책 검색
    <input type="text" v-model="isbn">
    <button @click="getBookDetail(isbn)">검색</button>
    <div v-if="book">
      {{ book.isbn }} | {{ book.title }}
  </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
const bookStore = 'bookStore'

export default {
  name: 'BookSearchByPictureView',
  data () {
    return {
      isbn: '9788932916483'
    }
  },
  computed: {
    ...mapState(bookStore, ['book'])
  },
  methods: {
    ...mapActions(bookStore, ['getBookDetail'])
  }
}
</script>

<style scoped>
</style>
