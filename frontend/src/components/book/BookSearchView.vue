<template>
  <div class="container">책 검색
    <input type="text" v-model="keyword" @keyup.enter="onClickSearch">
    <input type="checkbox" v-model="searchTargets" value="TITLE" checked>제목
    <input type="checkbox" v-model="searchTargets" value="AUTHOR" checked>지은이
    <input type="checkbox" v-model="searchTargets" value="PUBLISHER" checked>출판사
    <button @click="onClickSearch">검색</button>
    <div>
      <div
        class="list-group-item"
        v-for="book in bookList"
        :key="book.isbn"
      >
      <router-link :to="{ name: 'BookDetail', params: { isbn: book.isbn } }">
        <img v-bind:src="book.coverImageUrl" class="book-image" />
      </router-link>
      {{ book.title }}
      현재 최저가 {{ book.price }}원
      {{ book.author }} | {{ book.publisher }} | {{ book.publishDate }}
      <button type="button" @click="onClickRedirect(book.aladinUrl)">구매하러가기</button>
    </div>
  </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'
const bookStore = 'bookStore'

export default {
  name: 'BookSearchView',
  data () {
    return {
      keyword: '',
      searchTargets: []
    }
  },
  computed: {
    ...mapState(bookStore, ['bookList']),
    ...mapGetters(bookStore, ['getBookList'])
  },
  methods: {
    ...mapActions(bookStore, ['getSearchList']),
    onClickSearch () {
      let request = {
        keyword: this.keyword,
        searchTargets: this.searchTargets
      }
      this.getSearchList(request)
    },
    onClickRedirect (url) {
      window.open(url, 'blank')
    }
  }
}
</script>

<style scoped>
.container {
  padding: 0 100px;
}

.book-image {
  width: 160px;
}
</style>
