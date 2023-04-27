import { searchByISBN } from '@/api/book'

const bookStore = {
  namespaced: true,
  // state: 변수들의 집합
  state: {
    book: null,
    bookList: []
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
    }
  },
  /*
  Actions: 비동기 처리를 하는 함수들의 집합
  - Actions에서는 비동기적 작업을 Mutations에서는 동기적 작업만을 함.
  */
  actions: {
    getBookDetail ({ commit }, isbn) {
      searchByISBN(isbn)
        .then(({ data }) => {
          commit('SET_BOOK', data.result)
          console.log(data.result)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}

export default bookStore
