import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    port: 5173, // Vue 개발 서버 포트
    proxy: {
      '/api': {
        target: 'http://3.149.246.123:8080', // Spring Boot 서버 주소
        changeOrigin: true,
        secure: false,
      },
      '/kakao': {
        target: 'https://dapi.kakao.com',  // 카카오 API 서버 URL
        changeOrigin: true,                 // CORS 헤더 처리
        rewrite: (path) => path.replace(/^\/kakao/, ''),  // /kakao를 카카오 API 서버 URL로 변경
        secure: false,                      // https를 사용하지 않으면 false로 설정
      },
    },
  },
  
})
