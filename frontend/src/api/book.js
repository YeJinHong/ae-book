import axios from 'axios'

const searchByISBN = isbn => axios.get(`/api/books/${isbn}`)
const getSearchList = (request) => axios.get(`/api/books?keyword=${request.keyword}&searchTargets=${request.searchTargets}&page=${request.page}`)

export { searchByISBN, getSearchList }
