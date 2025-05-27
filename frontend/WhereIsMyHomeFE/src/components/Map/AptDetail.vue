<template>
  <button class="close-btn" @click="close">X</button>
  <img class="apt-image" :src="exampleImg" alt="아파트 예시 이미지" />
  <div class="apt-desc">
    <div class="apt-name-row">
      <h3 class="apt-name">{{ apt_info.apt_nm }}</h3>

      <div class="btns">
        <button class="move-btn" @click="move">이동</button>
        <img
          :src="bookMarkSelected"
          alt="지우기"
          v-if="isFavorite()"
          class="apt-add-btn"
          @click="onDel"
        />
        <img :src="bookMark" alt="추가" v-else class="apt-add-btn" @click="onAdd" />
      </div>
    </div>
    <p class="apt-year">준공년도: {{ apt_info.build_year }}</p>
    <p>{{ apt_info.apt_address }}</p>
  </div>
  <div class="deal-table-wrapper">
    <table class="deal-table">
      <thead>
        <tr>
          <th>거래금액</th>
          <th>거래일</th>
          <th>동</th>
          <th>층</th>
          <th class="unit" @click="toggleUnit">전용면적</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="deal in apt_deals" :key="deal.deal_seq">
          <td>{{ deal.deal_amount }} 만원</td>
          <td>{{ deal.deal_year }}-{{ deal.deal_month }}-{{ deal.deal_day }}</td>
          <td>{{ deal.apt_dong }}</td>
          <td>{{ deal.floor }}</td>
          <td>
            {{
              showInPyeong ? Math.ceil(deal.exclu_use_ar / 3.3058) + '평' : deal.exclu_use_ar + '㎡'
            }}
          </td>
        </tr>
        <tr v-if="apt_deals.length === 0">
          <td colspan="5" class="no-data">거래내역이 없습니다.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { aptAi } from '@/axios'
import { defineEmits, defineProps, ref, watch, onMounted } from 'vue'
import exampleImg from '@/assets/images/example.jpg'
import bookMark from '@/assets/images/bookmark.png'
import bookMarkSelected from '@/assets/images/bookmark_selected.png'

let geocoder
onMounted(() => {
  // SDK가 로딩된 후에야 kakao.maps.services가 유효합니다
  geocoder = new kakao.maps.services.Geocoder()
})

function searchDetailAddrFromCoords(callback) {
  // 좌표로 법정동 상세 주소 정보를 요청합니다
  geocoder.coord2Address(apt_info.value.longitude, apt_info.value.latitude, callback)
}
const callback = (result, status) => {
  if (status === kakao.maps.services.Status.OK) {
    apt_info.value.apt_address = result[0].road_address.address_name
  }
}

const isFavorite = () => {
  if (!props.fav_list || props.fav_list.length === 0) return false
  return props.fav_list.some((apt) => apt.apt_seq === props.apt_selected)
}

const props = defineProps({
  apt_selected: String,
  fav_list: Array,
})
const emit = defineEmits(['close-detail', 'addFav', 'removeFav', 'moveTo'])

function close() {
  emit('close-detail')
}

const token = localStorage.getItem('jwt')
const user = JSON.parse(localStorage.getItem('user'))
const onAdd = () => {
  if (!token || !user) {
    alert('로그인 후 사용해 주세요')
    return
  }
  emit('addFav', props.apt_selected)
}

const onDel = () => {
  if (!token || !user) {
    alert('로그인 후 사용해 주세요')
    return
  }
  emit('removeFav', props.apt_selected)
}

const apt_seq_local = ref(null)
const apt_info = ref({})
const apt_deals = ref([])
const showInPyeong = ref(false)

const pagination = ref({
  page: 1,
  per_page: 100,
})
const search = async () => {
  try {
    let response = await aptAi.get(`/api/v1/apts/${apt_seq_local.value}`)
    const info = response.data.data
    apt_info.value = info

    response = await aptAi.get(`/api/v1/apts/${apt_seq_local.value}/deals`, {
      params: pagination.value,
    })
    const deals = response.data.data.items
    apt_deals.value = deals

    searchDetailAddrFromCoords(callback)
  } catch (error) {
    console.log('정보 조회 실패' + error.name)
  }
}

const move = () => {
  emit('moveTo', apt_info.value.apt_address)
}

watch(
  () => props.apt_selected,
  (newApt) => {
    apt_seq_local.value = newApt
    search()
  },
  { immediate: true },
)

const toggleUnit = () => {
  showInPyeong.value = !showInPyeong.value
}
</script>

<style scoped>
.apt-add-btn {
  width: 36px;
  height: 36px;
  cursor: pointer;
}

.close-btn {
  position: absolute;
  top: 8px;
  right: 8px;

  width: 32px;
  height: 32px;
  padding: 0;

  background: #fff; /* 완전 불투명 흰색 */
  border: none; /* 테두리 없애기 */
  border-radius: 6px;

  box-shadow: 0 2px 6px rgba(0, 0, 0, 1); /* 부드러운 그림자 */

  font-size: 1.2rem;
  line-height: 32px;
  text-align: center;

  color: #333;
  cursor: pointer;
  transition: box-shadow 0.2s;
}

.close-btn:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2); /* 호버 시 그림자 강화 */
}

.close-btn:active {
  transform: scale(0.95);
}

.btns {
  display: flex;
  gap: 10px;
}

.move-btn {
  padding: 0.25rem 1.3rem;
  background: #26bbff;
  color: black;
  border: none;
  border-radius: 6px;
  font-weight: 500;
  height: 32px;
  cursor: pointer;
}

.apt-image {
  width: 100%;
  height: 250px;
  display: flex;
  align-items: flex-end;
  justify-content: center;
  object-fit: fill;
}

.apt-name-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.apt-name {
  margin: 0 0 8px;
  font-size: 1.5rem;
}

.apt-year {
  margin: 0;
  font-size: 1rem;
  color: rgba(255, 255, 255, 0.6);
}

.apt-desc {
  margin: 1rem;
}

.deal-table-wrapper {
  margin: 1rem;
  padding: 16px;
  background: #f0f4f8;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.deal-table {
  width: 100%;
  border-collapse: separate; /* rounded corner 가능하게 */
  border-spacing: 0 8px; /* 행 사이 간격 */
}

.deal-table th,
.deal-table td {
  padding: 12px 16px;
  text-align: center;
  font-size: 0.95rem;
}

/* 헤더 스타일 */
.deal-table thead th {
  background: rgba(255, 255, 255, 0.8);
  color: #333;
  font-weight: 600;
  position: sticky;
  top: 0; /* 스크롤 시 고정 */
  backdrop-filter: blur(8px);
}

.deal-table td {
  border: none;
  color: #333; /* 본문 셀 텍스트는 진한 회색으로 */
}

/* 첫/마지막 셀 둥글게 */
.deal-table tbody tr:first-of-type td:first-of-type {
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
}
.deal-table tbody tr:first-of-type td:last-of-type {
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
}

/* 행 카드 효과 */
.deal-table tbody tr {
  background: #fff;
  transition:
    transform 0.2s,
    box-shadow 0.2s;
}
.deal-table tbody tr:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 구분선 없애기 */
.deal-table td {
  border: none;
}

/* 데이터 없을 때 */
.no-data {
  padding: 24px;
  color: #777;
}

.unit {
  cursor: pointer;
}
</style>
