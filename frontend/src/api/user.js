import axios from 'axios'
import api from '@/api/auth'

const login = async (kakaoCode) => {
  try {
    const response = await axios.get(`/api/auth/login?code=${kakaoCode}`)
    return response
  } catch (error) {
    console.log(error)
    throw new Error('로그인에 실패하였습니다.')
  }
}
const logout = async () => {
  try {
    console.log('로그아웃')
    const response = api.post(`/api/auth/logout`)
    return response
  } catch (error) {
    console.log(error)
    throw new Error('로그아웃에 실패하였습니다.')
  }
}

export { login, logout }
