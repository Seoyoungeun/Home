<template>
  <!-- 내 아파트 버튼 -->
  <transition name="favorite" mode="out-in">
    <button v-if="!isSidebarOpen" class="favorite-button" @click="toggleSidebar">
      <span class="book-icon">📓</span>
      <span class="text">내 북마크</span>
    </button>
  </transition>

  <!-- 사이드바 -->
  <transition name="sidebar" mode="out-in">
    <div v-if="isSidebarOpen" class="sidebar">
      <div class="sidebar-title">
        <h3 class="title">내 찜 목록</h3>
        <span class="close-btn" @click="toggleSidebar">닫기</span>
      </div>
      <hr />
      <div v-if="isLoggedIn()" class="sidebar-content">
        <ul>
          <li class="sidebar-item" v-for="fav in fav_list" :key="fav.apt_seq" :fav="fav">
            <span class="apt-name" @click="handleClick(fav.apt_seq)">{{ fav.apt_nm }}</span>

            <span class="del-btn" @click="removeFav(fav.apt_seq)">삭제</span>
          </li>
        </ul>

        <span class="apt-name" @click="handleClick(fav.apt_seq)"></span>
        <div class="del-btn"></div>
      </div>

      <span v-else>로그인 후 사용해 주세요</span>
    </div>
  </transition>
</template>

<script setup>
import { ref, watch, defineProps } from 'vue'
import { aptAi } from '@/axios'

const emit = defineEmits(['removeFav', 'updated:apt_selected', 'updated:fav_list'])

const props = defineProps({
  fav_list: Array,
})

const token = localStorage.getItem('jwt')
const user = JSON.parse(localStorage.getItem('user'))
const isLoggedIn = () => {
  if (!token || !user) {
    return false
  }
  return true
}

const isSidebarOpen = ref(false) // 사이드바 상태 (열림/닫힘)
// 사이드바 열기/닫기 토글 함수
const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value
}

// 찜 목록에서 아파트 삭제 함수
const removeFav = (aptId) => {
  emit('removeFav', aptId)
}

const handleClick = (aptId) => {
  emit('updated:apt_selected', aptId)
}
</script>

<style scoped>
/* "내 아파트" 버튼 스타일 */
.favorite-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 12px 20px;
  background-color: #f8f8f8;
  color: #333;
  font-size: 16px;
  font-weight: 500;
  border-radius: 9px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 1); /* 부드러운 그림자 */
  cursor: pointer;
  z-index: 9999; /* 항상 맨 위에 */
  transition:
    box-shadow 0.3s,
    transform 0.3s;
  border: none; /* 테두리 제거 */
}

.favorite-button:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* 호버 시 그림자 강조 */
  transform: translateY(-3px); /* 호버 시 살짝 올라감 */
}

/* 하트 아이콘 */
.favorite-button .book-icon {
  font-size: 24px;
  margin-right: 8px;
}

/* "내 아파트" 텍스트 */
.favorite-button .text {
  font-size: 16px;
  font-weight: 600;
}

hr {
  border: none;
  border-top: 1px solid #333;
  margin: 10px 0;
}

.sidebar {
  display: flex;
  flex-direction: column;
  position: fixed;
  background-color: #f8f8f8;
  bottom: 20px;
  right: 20px;
  z-index: 999;
  width: 250px;
  height: 550px;
  padding: 12px 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 1);
  color: black;
  font-weight: 500;
}

.sidebar-enter-active,
.sidebar-leave-active,
.favorite-enter-active,
.favorite-leave-active {
  transition: all 0.3s ease;
}
.sidebar-enter-from,
.favorite-leave-to {
  opacity: 0;
  transform: scale(0.9);
}
.sidebar-leave-to,
.favorite-enter-from {
  opacity: 0;
  transform: scale(1.1);
}
.sidebar-enter-to,
.sidebar-leave-from,
.favorite-enter-to,
.favorite-leave-from {
  opacity: 1;
  transform: scale(1);
}

.sidebar-content ul {
  padding: 0;
  margin: 0;
  list-style: none;
  width: 100%; /* 꼭 추가 */
}

.sidebar-title {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 0.5rem;
}

.sidebar-content {
  display: flex;
  flex: 1;
  overflow-y: scroll;
  width: 100%;
}

.sidebar-content::-webkit-scrollbar {
  width: 1px; /* 스크롤바 너비 줄이기 */
}

.sidebar-content::-webkit-scrollbar-track {
  background: transparent; /* 트랙 배경 투명 */
}

.sidebar-content::-webkit-scrollbar-thumb {
  background-color: black; /* 스크롤바 색상 */
  border-radius: 3px;
}

.sidebar-item {
  display: flex;
  width: 100%;
  justify-content: space-between;
  padding: 0.5rem;
  align-items: center;
  /* gap: 10px; */
}

.apt-name {
  font-size: 14px;
  cursor: pointer;
}

.del-btn {
  text-decoration: underline;
  flex-shrink: 0;
  font-size: 10px;
  cursor: pointer;
}

.close-btn {
  text-decoration: underline;
  flex-shrink: 0;
  font-size: 15px;
  cursor: pointer;
}
</style>
