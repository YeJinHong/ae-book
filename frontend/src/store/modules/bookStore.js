import { searchByISBN, getSearchList } from '@/api/book'

const bookStore = {
  namespaced: true,
  // state: 변수들의 집합
  state: {
    book: null,
    bookList: [],
    bookPageSetting: null
  },
  /*
  Gettes: state의 변수들을 get하는역할을 한다.
  - state 자체를 변경하지 않음.
  */
  getters: {
    getBook: state => {
      return state.book
    },
    getBookList: state => {
      return state.bookList
    }
  },
  /*
  Mutations: 변수들을 조작하는 함수들의 집합
  - State는 반드시 Mutations가 가진 method를 통해서만 조작 함.
  */
  mutations: {
    SET_BOOK: (state, data) => {
      state.book = data
    },
    SET_BOOK_LIST: (state, data) => {
      state.bookList = data
    },
    RESET_BOOK_SEARCH (state) {
      state.bookList = []
      state.bookPageSetting = null
    },
    SET_PAGE_SETTING: (state, data) => {
      const { pageable, last, first, totalPages, size, totalElements, numberOfElements, empty } = data
      state.bookPageSetting = { pageable, last, first, totalPages, size, totalElements, numberOfElements, empty }
    }
  },
  /*
  Actions: 비동기 처리를 하는 함수들의 집합
  - Actions에서는 비동기적 작업을 Mutations에서는 동기적 작업만을 함.
  */
  actions: {
    async getBookDetail ({ commit }, isbn) {
      await searchByISBN(isbn)
        .then(({ data }) => {
          commit('SET_BOOK', data.result)
          console.log(data.result)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getSearchList ({ commit }, request) {
      await getSearchList(request)
        .then(({ data }) => {
          console.log(data.result.content)
          commit('SET_BOOK_LIST', data.result.content)
          commit('SET_PAGE_SETTING', data.result)
        })
        .catch(error => {
          console.log(error)
        })
    },
    async getPage ({ commit }, request) {
      await getSearchList(request)
        .then(({ data }) => {
          console.log(data.result.content)
          commit('SET_BOOK_LIST', data.result.content)
          commit('SET_PAGE_SETTING', data.result)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}

export default bookStore
