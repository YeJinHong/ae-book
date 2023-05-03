import api from '@/api/auth'

const saveNotification = async (data) => {
  try {
    console.log('알림 신청')
    const response = await api.post(`/api/notifications`, data)
    return response
  } catch (error) {
    console.log(error)
    throw new Error('알림 신청에 실패하였습니다.')
  }
}

export { saveNotification }
