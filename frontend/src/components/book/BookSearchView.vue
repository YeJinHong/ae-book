<template>
  <div class="container">책 검색
    <button @click="getBookReviewList">특정 도서의 서평 리스트</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BookSearchView',
  data () {
    return {
      isbn: '9788932916378'
    }
  },
  methods: {
    getBookReviewList () {
      axios.get(`/api/reviews/${this.isbn}`).then((result) => {
        console.log(result)
      }).catch((err) => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
.container {
  padding: 0 100px;
}
</style>
