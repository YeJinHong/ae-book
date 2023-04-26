<template>
  <div>사진으로 책 검색
    <!-- <button @click="">사진으로 책 검색</button> -->
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BookSearchByPictureView',
  data () {
    return {
      isbn: '9788932916378'
    }
  },
  methods: {
    // 이거 다른 곳에서도 계속 쓰는거라 .... 흠 어떻게 해야될지 .... ㅠㅅㅠ
    getBookReviewList () {
      axios.get(`/reviews/${this.isbn}`).then((result) => {
        console.log(result)
      }).catch((err) => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
</style>
