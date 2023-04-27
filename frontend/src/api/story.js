import axios from 'axios'
import api from '@/api/auth'

// 로그인한 유저의 동화 리스트
const searchStory = api.get(`/stories`)

// 특정 동화의 상세 정보 조회
const searchDetailStory = storyId => axios.get(`/stories/${storyId}`)

// 특정 동화를 삭제
const deleteStory = (storyId) => api.delete(`/stories/${storyId}`)

// 특정 동화의 제목 변경
const updateStoryTitle = (storyId, payload) => api.patch(`/stories/${storyId}`, payload)

// 동화 등록
const registerStory = (payload) => api.post(`/stories`, payload)

export {searchStory, searchDetailStory, deleteStory, updateStoryTitle, registerStory}
