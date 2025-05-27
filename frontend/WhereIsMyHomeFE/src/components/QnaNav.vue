<!-- src/components/QnaNav.vue -->
<template>
  <div class="qna-nav">
    <RouterLink to="/notice" active-class="active">공지사항</RouterLink>
    <RouterLink to="/qna" active-class="active">Q&A</RouterLink>
    <RouterLink to="/free" active-class="active">자유</RouterLink>
  </div>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>

<style scoped>
.qna-nav {
  display: flex;
  justify-content: flex-start; /* 왼쪽 정렬 */
  gap: 1.5rem;
  padding: 0.4rem 2rem; /* 적당한 간격 + 왼쪽 여백 */
  background-color: var(--color-bg);
  font-size: 0.95rem;
  width: 100%; /* 전체 너비 사용 */
  margin: 0; /* MainNav 바로 아래 붙게 */
}

.qna-nav a {
  color: #ccc;
  text-decoration: none;
  position: relative;
  padding-bottom: 4px;
}
.qna-nav a::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 2px;
  background-color: #26bbff;
  transform: scaleX(0);
  transition: transform 0.3s ease;
  transform-origin: center;
}
.qna-nav a.active::after {
  transform: scaleX(1);
}
</style>
