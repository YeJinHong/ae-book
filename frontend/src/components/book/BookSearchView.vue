<template>
  <div class="container">책 검색
    <input type="text" v-model="keyword" @keyup.enter="onClickSearch">
    <input type="checkbox" v-model="searchTargets" value="TITLE" checked>제목
    <input type="checkbox" v-model="searchTargets" value="AUTHOR" checked>지은이
    <input type="checkbox" v-model="searchTargets" value="PUBLISHER" checked>출판사
    <button @click="onClickSearch">검색</button>
    <div>
      <book-search-item v-for="book in bookList" :key="book.isbn" :book="book"></book-search-item>
  </div>
    <div class="pagination-container">
      <pagination :pageSetting="bookPageSetting" @paging="paging"></pagination>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'
import Pagination from '../common/Pagination.vue'
import BookSearchItem from './BookSearchItem.vue'
const bookStore = 'bookStore'

export default {
  components: { Pagination, BookSearchItem },
  name: 'BookSearchView',
  data () {
    return {
      keyword: '',
      searchTargets: [],
      request: null
    }
  },
  computed: {
    ...mapState(bookStore, ['bookList', 'bookPageSetting']),
    ...mapGetters(bookStore, ['getBookList'])
  },
  methods: {
    ...mapActions(bookStore, ['getSearchList', 'getPage']),
    onClickSearch () {
      this.request = {
        keyword: this.keyword,
        searchTargets: this.searchTargets
      }
      this.getSearchList(this.request)
    },
    paging (page) {
      this.request['page'] = page - 1
      this.getSearchList(this.request)
    }
  }
}
</script>

<style scoped>
.container {
  width: 100%;
  padding: 0 100px;
}

.book-image {
  width: 160px;
}

.pagination-container {
  display:flex;
  justify-content: center;
}
</style>
