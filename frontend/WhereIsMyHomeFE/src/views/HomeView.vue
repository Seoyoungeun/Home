<template>
  <div class="main-page">
    <div class="slideshow">
      <transition name="fade" mode="out-in">
        <img :key="currentImage" :src="currentImage" class="bg-image" />
      </transition>
    </div>

    <div class="overlay">
      <h1 class="title">복잡한 부동산 정보, 이제 지도로 한눈에.</h1>
      <p class="subtitle">당신 주변의 아파트 거래 정보, 직관적으로 확인하세요.</p>
      <button @click="goToMap" class="start-btn">Get Started</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const images = [
  'https://images.unsplash.com/photo-1568605114967-8130f3a36994',
  'https://images.unsplash.com/photo-1580587771525-78b9dba3b914',
  'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb',
  'https://images.unsplash.com/photo-1583847268964-b28dc8f51f92',
  'https://images.unsplash.com/photo-1528909514045-2fa4ac7a08ba',
]

const currentImage = ref(images[0])
let index = 0

onMounted(() => {
  setInterval(() => {
    index = (index + 1) % images.length
    currentImage.value = images[index]
  }, 10000) // 4초마다 전환
})

const goToMap = () => {
  router.push('/map')
}
</script>

<style scoped>
.main-page {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.slideshow {
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: 0;
  top: 0;
  left: 0;
}

.bg-image {
  width: 100%;
  height: 100vh;
  object-fit: cover;
  filter: brightness(0.5);
  position: absolute;
  top: 0;
  left: 0;
}

/* 페이드 전환 효과 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-to,
.fade-leave-from {
  opacity: 1;
}

.overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  text-align: center;
}

.title {
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.subtitle {
  font-size: 1.2rem;
  margin-bottom: 2rem;
}

.start-btn {
  background-color: #26bbff;
  border: none;
  padding: 12px 24px;
  font-size: 1rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  color: black;
}

.start-btn {
  background-color: #26bbff;
  border: none;
  padding: 12px 24px;
  font-size: 1rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  color: black;
  transition:
    box-shadow 0.3s ease,
    transform 0.2s ease;
}

.start-btn:hover {
  box-shadow: 0 0 16px rgba(38, 187, 255, 0.8);
  transform: translateY(-2px);
}
</style>
