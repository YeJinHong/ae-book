<template>
  <div class="container">
      <b-input-group>
        <b-form-input v-model="keyword" placeholder="검색어를 입력하세요" @keyup.enter="onClickSearch"></b-form-input>
      <b-input-group-append>
      <b-button @click="onClickSearch">검색</b-button>
    </b-input-group-append>
    </b-input-group>
    <div class="content-container">
      <div class="left">
        <div class="search-option">
          <input type="checkbox" v-model="searchTargets" value="TITLE">제목<br>
          <input type="checkbox" v-model="searchTargets" value="AUTHOR">지은이<br>
          <input type="checkbox" v-model="searchTargets" value="PUBLISHER">출판사<br>
        </div>
      </div>
      <div class="right">
        <div>
          <div class="search-result">
            '<span style="color:var(--ae-red); font-weight: 700;">{{ searchKeyword }}</span>' 검색 결과 전체 <span style="font-size:30px">{{totalSearchCount}}</span>건
          </div>
          <div class="bar"></div>
          <div class="no-search" v-if="totalSearchCount === 0">'<span style="color:var(--ae-red)">{{ searchKeyword }}</span>' 검색 결과를 찾을 수 없습니다.</div>
          <div v-if="totalSearchCount === 0">입력한 검색어의 철자 또는 띄어쓰기가 정확한지 다시 한번 확인해 주세요.<br>
검색어의 단어 수를 줄이거나, 보다 일반적인 검색어를 사용하여 검색해 보세요.</div>
          <div>
            <book-search-item v-for="book in bookList" :key="book.isbn" :book="book"></book-search-item>
          </div>
        <div class="pagination-container" v-if="totalSearchCount !== 0">
          <pagination :pageSetting="bookPageSetting" @paging="paging"></pagination>
        </div>
      </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState, mapMutations } from 'vuex'
import Pagination from '../common/Pagination.vue'
import BookSearchItem from './BookSearchItem.vue'
const bookStore = 'bookStore'

export default {
  components: { Pagination, BookSearchItem },
  name: 'BookSearchView',
  data () {
    return {
      keyword: '',
      searchKeyword: '',
      searchTargets: [],
      request: null
    }
  },
  mounted () {
    this.RESET_BOOK_SEARCH()
  },
  computed: {
    ...mapState(bookStore, ['bookList', 'bookPageSetting', 'totalSearchCount']),
    ...mapGetters(bookStore, ['getBookList'])
  },
  methods: {
    ...mapMutations(bookStore, ['RESET_BOOK_SEARCH']),
    ...mapActions(bookStore, ['getSearchList', 'getPage']),
    onClickSearch () {
      this.request = {
        keyword: this.keyword,
        searchTargets: this.searchTargets
      }
      this.searchKeyword = this.keyword
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
}

.content-container{
  display: flex;
  flex-direction: row;
}

.left {
  width: 20%;
  margin-right: 30px;
}

.right {
  width: 80%;
  display: flex;
  flex-direction: column;
  /* background-color: rgb(215, 163, 163); */
}

.book-image {
  width: 160px;
}

.pagination-container {
  display:flex;
  justify-content: center;
}

.search-option{
  width: 80%;
  height: 200px;
  background-color: var(--main-yellow);
  border-radius: 20px;
  padding: 20px;
}

.search-option > * {
  margin-top: 25px;
}

.input-group{
  width: 500px;
  margin: auto;
  margin-top: 50px;
  margin-bottom: 50px;
}

.bar {
  height:2px;
  background-color: var(--ae-navy);
}

.search-result{
  font-size: 25px;
  text-align: left;
  font-weight: 700;
}

.no-search{
  font-size: 35px;
  margin: 50px;
}
</style>
