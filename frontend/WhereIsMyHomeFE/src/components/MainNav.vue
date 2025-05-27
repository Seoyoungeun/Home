<!-- src/components/MainNav.vue -->
<template>
  <div class="wrapper">
    <nav class="main-nav">
      <RouterLink to="/" exact-active-class="active" class="nav-logo">
        <img src="@/assets/images/logo.png" alt="홈" />
      </RouterLink>
      <RouterLink to="/about" active-class="active">About Us</RouterLink>
      <RouterLink to="/map" active-class="active">지도</RouterLink>
      <RouterLink to="/forum" active-class="active">게시판</RouterLink>
    </nav>
    <div v-if="currentUser" class="user-box">
      <RouterLink to="/myposts" active-class="active" class="myposts">내 글 보기</RouterLink>

      <div class="user-info">
        <RouterLink to="/settings" class="user-name">{{ currentUser.name }}</RouterLink>
        <button class="logout-btn" @click="logout">로그아웃</button>
      </div>
    </div>
    <RouterLink v-else to="/login" class="login-btn">로그인</RouterLink>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { currentUser, clearCurrentUser } from '@/stores/auth'

const router = useRouter()

function logout() {
  clearCurrentUser()
  router.push('/')
}
</script>

<style scoped>
.wrapper {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: var(--color-bg);
}

.main-nav {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 왼쪽 그룹/오른쪽 로그인 버튼 */
  gap: 1.5rem;
}
.nav-logo img {
  width: 90px;
}
/* 로그인 버튼 스타일 */
.login-btn {
  display: inline-block;
  background-color: #26bbff; /* 선명한 파랑 */
  color: #101014;
  padding: 0.25rem 1.3rem;
  border-radius: 6px;
  font-weight: 500;
  text-decoration: none;
  transition: background-color 0.2s;
  height: 32px;
}
.login-btn:hover {
  background-color: #86d6fc; /* 호버 시 살짝 밝게 */
}
.user-box {
  display: flex;
  align-items: center;
  gap: 2rem; /* 내 글 보기 ↔ 유저 정보 간 간격 */
}
.user-info {
  display: flex;
  align-items: center;
  gap: 0.5rem; /* 이름 ↔ 로그아웃 간 좁은 간격 */
}
.logout-btn {
  display: inline-block;
  background: #636366;
  color: #fff;
  padding: 0.25rem 1rem;
  border-radius: 6px;
  font-weight: 500;
  text-decoration: none;
  transition: background-color 0.2s;
  height: 32px;
  margin-left: 1rem;
  border: none;
  outline: none;
  box-shadow: none;
}
.logout-btn:hover {
  background-color: #ababb0; /* 호버 시 살짝 밝게 */
}

.main-nav a {
  position: relative;
  padding-bottom: 0.25rem;
  transition: color 0.3s ease;
}
/* 1) 초기 상태: 너비 100% 가로 전체, 단 scaleX(0) 로 안 보이도록 */
.main-nav a::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: -2px;
  width: 100%;
  height: 2px;
  background-color: var(--color-primary);

  transform-origin: center; /* 중앙을 기준으로 스케일 */
  transform: scaleX(0); /* 축소 상태 */
  transition: transform 0.3s ease; /* transform 애니메이션 */
}

/* 2) active 클래스가 붙으면 scaleX(1) 으로 원본 크기 회복 */
.main-nav a.active::after {
  transform: scaleX(1);
}
</style>
