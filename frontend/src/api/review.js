import axios from 'axios'
import api from '@/api/auth'

// 1. 서평 등록
const saveReview = async (payload) => {
  try {
    const response = await api.post(`/api/reviews/${payload.isbn}`, payload.data)
    return response
  } catch (err) {
    console.log(err)
    throw new Error('서평 등록을 실패하였습니다.')
  }
}
// 2. 서평 상세 조회
const getReview = reviewId => api.get(`/api/reviews/detail/${reviewId}`)

// 3. 서평 수정
const modifyReview = (payload) => api.patch(`/api/reviews/${payload.reviewId}`, payload.data, { headers: {'Content-Type': 'application/json'} })

// 4. 서평 삭제
const deleteReview = reviewId => api.delete(`/api/reviews/${reviewId}`)

// 5. 최신 서평 리스트 - 메인페이지 전용, 12개
const getReviewMainList = () => axios.get(`/api/reviews/latest`)

// 6. 특정 도서의 서평 리스트 by ISBN
const getReviewBookList = (request) => axios.get(`/api/reviews/${request.isbn}?page=${request.page}&size=${request.size}&sort=${request.sort},${request.direction}`)
// /reviews/{isbn}?page=int&size=5&sort=key,direction
// sort 종류
// 1. id, DESC // LATEST
// 2. score, DESC // SCORE_HIGHEST
// 3. score, ASC // SCORE_LOWEST

// 7. 로그인한 사용자의 서평 리스트
const getReviewMyList = (request) => api.get(`/api/reviews?page=${request.page}&size=${request.size}&sort=${request.sort},${request.direction}`)

export { saveReview, getReview, modifyReview, deleteReview, getReviewMainList, getReviewBookList, getReviewMyList }
