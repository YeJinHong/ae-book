import { getReview, getLatestReviewList, getBookReviewList, modifyReview } from '../../api/review'

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
    getMyReviewList: state => {
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
    },
    getMainReviewListAction ({ commit }) {
      getLatestReviewList()
        .then(({data}) => {
          commit('SET_MAIN_REVIEW_LIST', data.result)
          console.log('MAIN_REVIEW_LIST : ' + data.result)
        }).catch((err) => {
          console.log(err)
        })
    },
    getBookReviewListAction ({ commit }, isbn) {
      getBookReviewList(isbn)
        .then(({data}) => {
          commit('SET_BOOK_REVIEW_LIST', data.result)
          console.log('BOOK_REVIEW_LIST :' + data.result)
        }).catch((err) => {
          console.log(err)
        })
    },
    modifyReviewAction ({ commit }, reviewId, payload) {
      modifyReview(reviewId, payload)
        .then(({data}) => {
          console.log(data.config.headers)
          console.log(data)
          // 만약 백에서 return을 변경된 값으로 주면 바로 SET_REVIEW
        }).then(getReview(reviewId))
        .catch((err) => {
          console.log(err.config.headers)
          console.log(err)
        })
    }
  }
}

export default reviewStore
