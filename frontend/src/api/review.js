import axios from 'axios'
import api from '@/api/auth'

// 1. 서평 등록
const saveReview = (isbn, payload) => api.post(`/api/reviews/${isbn}`, payload)

// 2. 서평 상세 조회
const getReview = reviewId => api.get(`/api/reviews/detail/${reviewId}`)

// 3. 서평 수정
const modifyReview = (reviewId, payload) => api.patch(`/api/reviews/${reviewId}`, payload, {
  headers: {
    'Content-Type': 'application/json'
  }
})

// 4. 서평 삭제

// 5. 최신 서평 리스트 - 메인페이지 전용, 12개
const getLatestReviewList = () => axios.get(`/api/reviews/latest`)

// 6. 특정 도서의 서평 리스트 by ISBN
const getBookReviewList = isbn => axios.get(`/api/reviews/${isbn}`)

// 7. 로그인한 사용자의 서평 리스트

/* 예시
 * const searchByISBN = isbn => axios.get(`/api/books/${isbn}`)
 * const requestLogin = (payload) => api.post("/api/users/login", payload);
 */
export { saveReview, getReview, modifyReview, getLatestReviewList, getBookReviewList }
