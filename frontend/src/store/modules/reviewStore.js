import { getReview } from '../../api/review'

const reviewStore = {
  namespaced: true,
  // state: 변수들의 집합
  state: {
    review: null,
    mainReviewList: [],
    myReviewList: [],
    bookReviewList: []
  },
  /*
  Gettes: state의 변수들을 get하는역할을 한다.
  - state 자체를 변경하지 않음.
  */
  getters: {
    getReview: state => {
      return state.review
    },
    getMainReviewList: state => {
      return state.mainReviewList
    },
    getMYReviewList: state => {
      return state.myReviewList
    },
    getBookReviewList: state => {
      return state.bookReviewList
    }
  },
  /*
  Mutations: 변수들을 조작하는 함수들의 집합
  - State는 반드시 Mutations가 가진 method를 통해서만 조작 함.
  */
  mutations: {
    SET_REVIEW: (state, data) => {
      state.review = data
    },
    SET_MAIN_REVIEW_LIST: (state, data) => {
      state.mainReviewList = data
    },
    SET_MY_REVIEW_LIST: (state, data) => {
      state.myReviewList = data
    },
    SET_BOOK_REVIEW_LIST: (state, data) => {
      state.bookReviewList = data
    }
  },
  /*
  Actions: 비동기 처리를 하는 함수들의 집합
  - Actions에서는 비동기적 작업을 Mutations에서는 동기적 작업만을 함.
  - commit은 mutation명을 쓰면 됨
  */
  actions: {
    getReviewAction ({ commit }, reviewId) {
      getReview(reviewId)
        .then(({data}) => {
          commit('SET_REVIEW', data.result)
          console.log(data.result)
        }).catch((err) => {
          console.log(err)
        })
    }
    // getBookDetail ({ commit }, isbn) {
    //   searchByISBN(isbn)
    //     .then(({ data }) => {
    //       commit('SET_BOOK', data.result)
    //       console.log(data.result)
    //     })
    //     .catch(error => {
    //       console.log(error)
    //     })
    // }
  }
}

export default reviewStore
