<template>
  <div class="about-page">
    <!-- 배경 이미지 섹션 -->
    <section class="hero-section">
      <div class="hero-content">
        <h1>우리의 목표는, 부동산 정보를 더 쉽게 이해하게 만드는 것입니다.</h1>
        <p>
          복잡하고 막연한 숫자 대신,<br />
          직관적인 지도와 깔끔한 거래 정보로 <strong>더 빠르게, 더 똑똑하게</strong> 집을 살펴보세요.
        </p>
        <button class="cta-btn" @click="goToMap">지도에서 보기</button>
      </div>
    </section>

    <!-- 팀 정보 섹션 -->
    <section class="team-info">
      <h2>Team Info</h2>
      <p>이 서비스는 데이터를 사랑하는 개발자들이 만들고 있습니다.</p>
      <div class="developer-details">
        <!-- 개발자 1 -->
        <div class="developer">
          <img src="@/assets/images/송은교사진.jpg" alt="송은교" class="dev-avatar" />
          <p><strong>송은교</strong></p>
        </div>

        <!-- 개발자 2 -->
        <div class="developer">
          <img src="@/assets/images/서영은사진.png" alt="서영은" class="dev-avatar" />
          <p><strong>서영은</strong></p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

// 버튼 클릭 시 지도 페이지로 이동
const goToMap = () => {
  router.push('/map')
}
</script>

<style scoped>
/* 전체 페이지 스타일 */
.about-page {
  font-family: 'Noto Sans KR', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

/* 배경 이미지 섹션 */
.hero-section {
  width: 100%;
  height: 100vh; /* 화면 전체 크기 */
  background-image: url('https://images.unsplash.com/photo-1721623905125-af5f7b7fb18b?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'); /* 예시 이미지 */
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  text-align: center;
  position: relative;
  overflow: hidden;
}

/* 배경의 텍스트 */
.hero-content h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.4);
}

.hero-content p {
  font-size: 1.5rem;
  margin-bottom: 2rem;
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.4);
}

/* 버튼 스타일 */
.cta-btn {
  background-color: #26bbff;
  border: none;
  padding: 15px 40px;
  font-size: 1.1rem;
  color: #101014;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.cta-btn:hover {
  background-color: #1a9fd2;
  transform: scale(1.05);
}

/* 팀 정보 스타일 */
.team-info {
  background-color: #f8f9fa;
  width: 100%;
  padding: 50px 20px;
  text-align: center;
  color: black;
}

.team-info h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.team-info p {
  font-size: 1.2rem;
  color: #777;
}

/* 개발자 섹션 스타일 */
.developer-details {
  display: flex;
  justify-content: center;
  gap: 3rem; /* 개발자 간 간격 */
  margin-top: 30px;
}

.developer {
  width: 250px;
  text-align: center;
}

.dev-avatar {
  width: 100px;      /* 이미지의 크기를 고정 */
  height: 100px;     /* 너비와 높이를 동일하게 설정 (원형) */
  border-radius: 50%; /* 원형 모양 */
  margin-bottom: 10px;
  object-fit: cover;  /* 이미지를 비율에 맞춰 크기 조정하고, 넘치는 부분은 자르기 */
}
.contact-btn {
  background-color: #26bbff;
  color: white;
  padding: 10px 20px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: bold;
  margin-top: 20px;
  display: inline-block;
  transition: background-color 0.3s ease;
}

.contact-btn:hover {
  background-color: #1a9fd2;
}
</style>
