<template>
  <div class="forgot-page">
    <div class="forgot-card">
      <img src="@/assets/images/logo.png" alt="Logo" class="logo" />

      <h1 class="title">비밀번호 찾기</h1>

      <form @submit.prevent="onSubmit" class="forgot-form">
        <label>
          이메일 주소
          <input v-model="email" type="email" placeholder="you@example.com" required />
        </label>

        <button type="submit" class="btn-send">임시 비밀번호 전송</button>
      </form>

      <RouterLink to="/login" class="link login">로그인으로 돌아가기</RouterLink>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

const email = ref('')

function onSubmit() {
  // TODO: 실제 API 연동
  alert(`임시 비밀번호가 ${email.value}로 전송되었습니다.`)
}
</script>

<style scoped>
.forgot-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: var(--color-bg);
}

.forgot-card {
  background: var(--color-bg-alt);
  border: 2px solid var(--color-border);
  border-radius: 10px;
  padding: 4rem;
  width: 360px;
  text-align: center;
  color: var(--color-text);
}

.logo {
  width: 130px;
  margin-bottom: 1rem;
}

.title {
  margin-bottom: 1.5rem;
  font-size: 1.25rem;
  font-weight: 600;
}

.forgot-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  text-align: left;
}

.forgot-form label {
  display: flex;
  flex-direction: column;
  font-size: 0.875rem;
}

.forgot-form input {
  margin-top: 0.25rem;
  padding: 0.5rem;
  background: #242428;
  border: 2px solid var(--color-border);
  border-radius: 6px;
  color: var(--color-text);
}

.btn-send {
  margin-top: 1rem;
  padding: 0.75rem;
  background: #26bbff;
  color: var(--color-bg);
  border: none;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.2s;
  margin-bottom: 1rem;
}
.btn-send:hover {
  background: #86d6fc;
}

.link.login {
  margin-top: 1.5rem;
  font-size: 0.875rem;
  color: #26bbff;
  text-decoration: none;
}
.link.login:hover {
  opacity: 0.8;
}
</style>
