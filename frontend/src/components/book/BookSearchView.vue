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
    <div class="pagination-container">
      <pagination :pageSetting="bookPageSetting" @paging="paging"></pagination>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'
import Pagination from '../common/Pagination.vue'
const bookStore = 'bookStore'

export default {
  components: { Pagination },
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
    onClickRedirect (url) {
      window.open(url, 'blank')
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
