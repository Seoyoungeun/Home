import axios from 'axios'
const aptAi = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000,
  withCredentials: true,
})
aptAi.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwt')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    console.log('[요청]', config.url, config.data, config.headers)
    return config
  },
  (error) => {
    console.log('[요청 실패]', error.name)
    return Promise.reject(error)
  },
)
aptAi.interceptors.response.use(
  (response) => {
    console.log('[응답 성공]', response.config.url, response.status, response.data)
    return response
  },
  (error) => {
    console.log('[응답 실패]', error)
    return Promise.reject(error)
  },
)
export { aptAi }
