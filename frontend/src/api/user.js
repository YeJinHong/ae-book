import axios from 'axios'

const login = kakaoCode => axios.get(`/api/auth/login?code=${this.kakaoCode.code}`)

export { login }
