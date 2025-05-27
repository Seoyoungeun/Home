<template>
  <div class="about-page">
    <!-- 상단 이미지 -->
    <div class="hero-image"></div>

    <!-- 소개 문구 -->
    <section class="content">
      <h1>우리의 목표는, 부동산 정보를 더 쉽게 이해하게 만드는 것입니다.</h1>
      <p>
        복잡하고 막연한 숫자 대신,<br />
        직관적인 지도와 깔끔한 거래 정보로 더 빠르게, 더 똑똑하게 집을 살펴보세요.
      </p>
      <button class="go-map-btn" @click="goToMap">지도에서 보기</button>

      <!-- 팀 소개 -->
      <div class="team-info">
        <h2>Team Info</h2>
        <p>이 서비스는 데이터를 사랑하는 개발자들이 만들고 있습니다.</p>
      </div>
    </section>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

const goToMap = () => {
  router.push('/map')
}
</script>

<style scoped>
.about-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: 'Noto Sans KR', sans-serif;
  background: #f9fafc;
}

/* 상단 큰 이미지 */
.hero-image {
  width: 100%;
  height: 400px;
  background-image: url('https://images.unsplash.com/photo-1580587771525-78b9dba3b914'); /* 아파트 관련 이미지 */
  background-size: cover;
  background-position: center;
}

/* 본문 내용 */
.content {
  max-width: 800px;
  margin: -80px auto 80px;
  background: white;
  padding: 60px 32px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  border-radius: 16px;
  text-align: center;
}

.content h1 {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
}

.content p {
  font-size: 1.1rem;
  color: #444;
  line-height: 1.7;
  margin-bottom: 2rem;
}

.go-map-btn {
  background-color: #26bbff;
  border: none;
  padding: 14px 28px;
  font-size: 1rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  color: black;
  transition:
    box-shadow 0.3s ease,
    transform 0.2s ease;
}

.go-map-btn:hover {
  box-shadow: 0 0 16px rgba(38, 187, 255, 0.8);
  transform: translateY(-2px);
}

.team-info {
  margin-top: 4rem;
}

.team-info h2 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.team-info p {
  color: #777;
}
</style>
