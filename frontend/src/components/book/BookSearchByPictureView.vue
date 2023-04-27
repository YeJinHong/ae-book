<template>
  <div>사진(ISBN)으로 책 검색
    <input type="text" v-model="isbn">
    <button @click="searchISBN">검색</button>
    <div>
      {{ book.isbn }} | {{ book.title }}
  </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BookSearchByPictureView',
  data () {
    return {
      isbn: '9788932916483',
      book: Object
    }
  },
  methods: {
    searchISBN () {
      axios.get(`/api/books/${this.isbn}`).then((result) => {
        this.book = result.data.result
        console.log(this.book)
      }).catch((err) => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
</style>
