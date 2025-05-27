<template>
  <div class="login-page">
    <div class="login-card">
      <img src="@/assets/images/logo.png" alt="Logo" class="logo" />

      <h1 class="title">회원가입</h1>

      <form @submit.prevent="onSignup" class="login-form">
        <label>
          이름
          <input v-model="name" type="text" placeholder="홍길동" required />
        </label>

        <label>
          이메일 주소
          <input v-model="email" type="email" placeholder="you@example.com" required />
        </label>

        <label>
          비밀번호
          <input v-model="password" type="password" placeholder="••••••••" required />
        </label>

        <button type="submit" class="btn-login">가입하기</button>
      </form>

      <RouterLink to="/login" class="link signup">이미 계정이 있으신가요? 로그인</RouterLink>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { aptAi } from '@/axios' // axios 인스턴스 import 필요

const name = ref('')
const email = ref('')
const password = ref('')

const router = useRouter()

async function onSignup() {
  try {
    const response = await aptAi.post('/api/user/regist', {
      name: name.value,
      email: email.value,
      password: password.value,
    })

    console.log('회원가입 응답:', response)

    alert('회원가입이 완료되었습니다!')
    router.push('/login')
  } catch (error) {
    console.error('회원가입 실패:', error)
    alert('회원가입에 실패했습니다.')
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
  background: #86d6fc;
}

.signup {
  margin-top: 1.5rem;
  font-size: 0.875rem;
}
</style>
