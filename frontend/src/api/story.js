import axios from 'axios'

// 로그인한 유저의 동화 리스트
const searchStory = accessToken => axios.get(`/stories`, {headers: {Authorization: accessToken}})

// 특정 동화의 상세 정보 조회
const searchDetailStory = storyId => axios.get(`/stories/${storyId}`)

// 특정 동화를 삭제
const deleteStory = (storyId, accessToken) => axios.delete(`/stories/${storyId}`, {headers: {Authorization: accessToken}})

// 특정 동화의 제목 변경
const updateStoryTitle = (accessToken, storyId, payload) => axios.patch(`/stories/${storyId}`, payload, {headers: {Authorization: accessToken}})

// 동화 등록
const registerStory = (accessToken, payload) => axios.post(`/stories`, payload, {headers: {Authorization: accessToken}})

export {searchStory, searchDetailStory, deleteStory, updateStoryTitle, registerStory}
