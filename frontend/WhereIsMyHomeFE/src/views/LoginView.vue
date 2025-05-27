<!-- src/views/Login.vue -->
<template>
  <div class="login-page">
    <div class="login-card">
      <img src="@/assets/images/logo.png" alt="Logo" class="logo" />

      <h1 class="title">로그인</h1>

      <form @submit.prevent="onSubmit" class="login-form">
        <label>
          이메일 주소
          <input v-model="email" type="email" placeholder="you@example.com" required />
        </label>

        <label>
          비밀번호
          <input v-model="password" type="password" placeholder="••••••••" required />
        </label>

        <RouterLink to="/findPW" class="link findPW">비밀번호를 잊으셨나요?</RouterLink>

        <button type="submit" class="btn-login">로그인</button>
      </form>

      <RouterLink to="/signup" class="link signup">계정 생성</RouterLink>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router' // RouterLink가 아니라 useRouter 사용
import { aptAi } from '@/axios' // 실제 위치에 맞춰 경로 확인
import { setCurrentUser } from '@/stores/auth'

const router = useRouter()

const email = ref('')
const password = ref('')
const errorMessage = ref('')

async function onSubmit() {
  try {
    const response = await aptAi.post('/api/user/login', {
      email: email.value,
      password: password.value,
    })

    console.log('로그인 응답:', response)

    const token = response.data.token
    const userInfo = response.data.user

    if (token && userInfo) {
      localStorage.setItem('jwt', token) // JWT 저장
      setCurrentUser(userInfo)
      router.push('/') // 홈으로 이동
    } else {
      errorMessage.value = '토큰을 찾을 수 없습니다.'
    }
  } catch (error) {
    console.error('로그인 실패:', error)
    errorMessage.value = error.response?.data?.error || '로그인에 실패했습니다.'
  }
}
</script>

<style scoped>
.login-card {
  background: var(--color-bg-alt);
  border: 2px solid var(--color-border);
  border-radius: 10px;
  padding: 2rem;
  width: 100%;
  min-width: 320px;
  width: 360px;
  max-width: 100%;
  text-align: center;
  color: var(--color-text);
}

.login-card .logo {
  width: 130px;
  margin-bottom: 1rem;
}

.login-card .title {
  margin-bottom: 1.5rem;
  font-size: 1.25rem;
  font-weight: 600;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  text-align: left;
}

.login-form label {
  display: flex;
  flex-direction: column;
  font-size: 0.875rem;
}

.login-form input {
  margin-top: 0.25rem;
  padding: 0.5rem;
  background: #242428;
  border: 2px solid var(--color-border);
  border-radius: 6px;
  color: var(--color-text);
}

.link {
  display: block;
  font-size: 0.75rem;
  color: #26bbff;
  text-decoration: none;
  margin-top: 0.25rem;
  transition: opacity 0.2s;
}

.link:hover {
  opacity: 0.8;
}

.forgot {
  text-align: right;
}

.btn-login {
  margin-top: 1rem;
  padding: 0.75rem;
  background: #26bbff;
  color: var(--color-bg);
  border: none;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.2s;
}

.btn-login:hover {
  background: #86d6fc; /* 약간 밝게 */
}

.signup {
  margin-top: 1.5rem;
  font-size: 0.875rem;
}
</style>
