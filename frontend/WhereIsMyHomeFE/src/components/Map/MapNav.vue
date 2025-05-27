<template>
  <div class="wrapper">
    <nav class="map-nav">
      <!-- 지역 레이블 + 드롭다운 그룹 -->
      <div class="filter-group">
        <label class="map-label">지역</label>
        <div class="filters">
          <select v-model="sido_selected" @change="onSidoChange">
            <option disabled value="">시/도</option>
            <option v-for="sido_name in sido_list" :key="sido_name" :value="sido_name">
              {{ sido_name }}
            </option>
          </select>

          <select v-model="gugun_selected" @change="onGugunChange">
            <option disabled value="">시/군/구</option>
            <option v-for="gugun_name in gugun_list" :key="gugun_name" :value="gugun_name">
              {{ gugun_name }}
            </option>
          </select>

          <select v-model="umd_selected">
            <option disabled value="">읍/면/동</option>
            <option v-for="umd_name in umd_list" :key="umd_name" :value="umd_name">
              {{ umd_name }}
            </option>
          </select>
        </div>
      </div>
      <button class="apply-button" @click="onMove">이동</button>

      <!-- 아파트 이름 라벨 + 입력창 그룹 -->
      <div class="filter-group apartment-group">
        <label class="map-label">아파트 이름</label>
        <div class="filters apartment-filter">
          <input
            type="text"
            v-model="apartmentName"
            placeholder="아파트 이름"
            @keyup.enter="onSearch"
          />
        </div>
      </div>

      <div class="filter-actions">
        <button class="apply-button" @click="onSearch">적용</button>
        <button class="reset-button" @click="onReset">초기화</button>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { ref, computed, defineEmits, onMounted } from 'vue'
import { aptAi } from '@/axios'
const emit = defineEmits(['updated:keyword', 'updated:region'])

onMounted(async () => {
  try {
    const response = await aptAi.get('/api/v1/dongcodes')
    sido_list.value = response.data.data.items
  } catch (error) {
    console.log('목록 조회 실패' + error.name)
  }
})

const sido_selected = ref('')
const gugun_selected = ref('')
const umd_selected = ref('')

const sido_list = ref([])
const gugun_list = ref([])
const umd_list = ref([])

const apartmentName = ref('')

const onSidoChange = async () => {
  gugun_selected.value = ''
  umd_selected.value = ''

  if (!sido_selected.value) return

  try {
    const response = await aptAi.get(`/api/v1/dongcodes/${sido_selected.value}`)
    gugun_list.value = response.data.data.items // 구조에 따라 .items 생략할 수도 있어
  } catch (error) {
    console.error('구군 조회 실패:', error.message)
  }
}

const onGugunChange = async () => {
  umd_selected.value = ''
  if (!gugun_selected.value) return

  try {
    const response = await aptAi.get(
      `/api/v1/dongcodes/${sido_selected.value}/${gugun_selected.value}`,
    )
    umd_list.value = response.data.data.items // 구조에 따라 .items 생략할 수도 있어
  } catch (error) {
    console.error('구군 조회 실패:', error.message)
  }
}

const onMove = () => {
  emit('updated:region', `${sido_selected.value} ${gugun_selected.value} ${umd_selected.value}`)
}

function onSearch() {
  emit('updated:keyword', apartmentName.value)
}
function onReset() {
  apartmentName.value = ''
  emit('updated:keyword', '')
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

.map-nav {
  display: flex;
  align-items: center;
  gap: 1.5rem; /* 그룹 간 기본 간격 */
  flex: 1;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.apartment-group {
  margin-left: 2rem; /* 지역 그룹과 아파트 그룹 사이 추가 간격 */
}

.map-label {
  font-weight: 500;
  color: var(--color-text);
}

.filters {
  display: flex;
  gap: 0.5rem;
  flex: 1;
}

.apartment-filter {
  flex: none;
}

select,
input[type='text'] {
  padding: 0.5rem;
  border: 2px solid var(--color-border);
  border-radius: 6px;
  background: #1e1e22;
  color: var(--color-text);
}

.filter-actions {
  display: flex;
  align-items: center;
  gap: 0.5rem; /* 버튼 사이 간격을 8px 정도로 */
}

.apply-button {
  padding: 0.25rem 1.3rem;
  background: #26bbff;
  color: black;
  border: none;
  border-radius: 6px;
  font-weight: 500;
  height: 32px;
  cursor: pointer;
}

.apply-button:hover {
  background: #86d6fc;
}

.reset-button {
  padding: 0.25rem 1.3rem;
  background: #636366;
  color: #fff;
  border: none;
  font-weight: 500;
  border-radius: 6px;
  height: 32px;
  cursor: pointer;
}

.reset-button:hover {
  background: #ababb0;
}

select {
  width: 150px; /* 원하는 크기로 조절 */
}
</style>
