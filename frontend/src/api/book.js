import axios from 'axios'

const searchByISBN = isbn => axios.get(`/api/books/${isbn}`)

export { searchByISBN }
