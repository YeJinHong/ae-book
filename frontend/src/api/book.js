import axios from 'axios'

const searchByISBN = isbn => axios.get(`/api/books/${isbn}`)
const getSearchList = (request) => axios.get(`/api/books?keyword=${request.keyword}&searchTargets=${request.searchTargets}&page=${request.page}`)
const getISBNfromPicture = formData => axios.post(`/fast/books/isbn`, formData, {headers: {'Content-Type': 'multipart/form-data'}})
export { searchByISBN, getSearchList, getISBNfromPicture }
