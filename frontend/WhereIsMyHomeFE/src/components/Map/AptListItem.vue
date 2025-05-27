<template>
 <div class="apt-card"
 @click="handleAptSelect">
    <div class="apt-image" :style="{ backgroundImage: `url(${exampleImg})` }">
      <div class="apt-info">
        <div class="apt-name">{{ apt.apt_nm }}</div>
        <div class="apt-year">{{ apt.build_year}} 준공</div>
      </div>
      <!-- <div class="heart-icon">❤️</div> -->
    </div>
  </div>
</template>

<script setup>
import exampleImg from '@/assets/images/example.jpg'
import { defineProps, defineEmits } from "vue";

const props = defineProps({
    apt: {
        type: Object,
        required: true,
  },
})

const emit = defineEmits(['update:apt_selected']);
const handleAptSelect = () => {
  emit('update:apt_selected', props.apt.apt_seq)
};
</script>

<style scoped>
.apt-card {
  display:flex;
  width: 370px;
  height: 180px;
  border-radius: 20px;
  margin: 1rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  
  overflow: hidden;

  transition: transform 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
}

.apt-card:hover {
  transform: translateY(-6px) scale(1.02);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

/* 클릭(활성) 시 살짝 눌리는 효과 */
.apt-card:active {
  transform: translateY(-2px) scale(0.98);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.apt-image {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  position: relative;
  display: flex;
  align-items: flex-end;
  justify-content: center;
}

.apt-info {
  background: #1C1C20;
  width: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.heart-icon {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 24px;
  color: white;
}

.apt-year {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);  /* 반투명 흰색 느낌 (어두운 배경에 적합) */
  margin-top: 4px;
}
</style>