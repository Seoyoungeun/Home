<template>
  <div class="map-view">
    <header class="sub-header">
      <MapNav @updated:keyword="handleKeyword" @updated:region="handleRegion" />
      <!-- 지도와 관계 없는 컴포넌트는 여기 위치 -->
    </header>
    <main class="sub-content">
      <div class="apt-list">
        <AptList :apt_list="apt_list" @updated:apt_selected="handleAptSelect" />
      </div>

      <div class="detail-and-map">
        <div v-if="apt_selected" class="detail-overlay" ref="detailOverlayRef">
          <AptDetail
            :apt_selected="apt_selected"
            :fav_list="fav_list"
            @close-detail="apt_selected = null"
            @addFav="handleAdd"
            @removeFav="handleRemove"
            @moveTo="handleMoveTo"
          />
        </div>

        <MapComp
          :keyword="keyword"
          :apt_selected="apt_selected"
          :region="region"
          :moveToAdr="moveToAdr"
          @updated:apt_list="handleAptListUpdate"
          @updated:apt_selected="handleAptSelect"
          @updated:region="resetRegion"
        />

        <div class="fav-list">
          <FavList
            :fav_list="fav_list"
            @updated:fav_list="handleFavList"
            @removeFav="handleRemove"
            @updated:apt_selected="handleAptSelect"
          />
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { aptAi } from '@/axios'
import MapNav from '@/components/Map/MapNav.vue'
import AptList from '@/components/Map/AptList.vue'
import MapComp from '@/components/Map/MapComp.vue'
import AptDetail from '@/components/Map/AptDetail.vue'
import FavList from '@/components/Map/FavList.vue'

import { ref, watch, onMounted, nextTick } from 'vue'

const apt_list = ref([])
const apt_selected = ref(null)
const keyword = ref('')
const region = ref(null)
const fav_list = ref([])
const token = localStorage.getItem('jwt')
const user = JSON.parse(localStorage.getItem('user'))
const moveToAdr = ref(null)

onMounted(() => {
  handleFavList()
})

const handleAptListUpdate = (newAptList) => {
  apt_list.value = newAptList
}

const handleAptSelect = (apt) => {
  apt_selected.value = apt
}

const handleKeyword = (newKeyword) => {
  keyword.value = newKeyword
}

const handleRegion = (newRegion) => {
  region.value = newRegion
}

const resetRegion = () => {
  region.value = ''
}

const handleFavList = async () => {
  if (!token || !user) {
    return
  }

  try {
    const response = await aptAi.get(`/api/v1/favs/list?uid=${user.uid}`)
    fav_list.value = response.data.data
  } catch (error) {
    console.error(error)
  }
}

const handleRemove = async (aptId) => {
  if (!token || !user) {
    return
  }

  try {
    await aptAi.delete(`/api/v1/favs?uid=${user.uid}&apt_seq=${aptId}`)
    const response = await aptAi.get(`/api/v1/favs/list?uid=${user.uid}`)
    fav_list.value = response.data.data
  } catch (error) {
    console.error(error)
  }
}

const handleAdd = async (aptId) => {
  if (!token || !user) {
    return
  }

  try {
    await aptAi.post(`/api/v1/favs?uid=${user.uid}&apt_seq=${aptId}`)
    const response = await aptAi.get(`/api/v1/favs/list?uid=${user.uid}`)
    fav_list.value = response.data.data
  } catch (error) {
    console.error(error)
  }
}

const handleMoveTo = (newMoveTo) => {
  moveToAdr.value = newMoveTo
}

const detailOverlayRef = ref(null)
watch(apt_selected, async (newVal) => {
  if (newVal !== null) {
    await nextTick() // DOM 업데이트 이후 실행
    detailOverlayRef.value?.scrollTo({ top: 0, behavior: 'smooth' })
  }
})
</script>

<style scoped>
.map-view {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh; /* 부모 컨테이너의 크기 설정 */
}

.sub-header {
  display: flex;
  flex: none;
  width: 100%;
  z-index: 10; /* MapNav가 지도 위로 오지 않도록 z-index 설정 */
}

.sub-content {
  display: flex;
  flex: 1;
  flex-direction: row;
  min-height: 0;
  overflow: hidden;
}

.apt-list {
  width: 400px;
  height: 100%; /* 가로폭 고정, 높이는 자식에게 맡김 */
  display: flex;
  flex-direction: column; /* sub-content 가 준 높이 전부 */
  overflow-y: scroll; /* 이 칸만 스크롤 */
  min-height: 0;
  align-items: center;
}

.detail-overlay {
  position: relative;
  width: 500px;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* 반투명 뒤배경 */
  display: flex;
  flex-direction: column;
  flex: 0 0 500px;
  overflow-y: scroll;
}

.detail-and-map {
  flex: 1; /* 지도 영역이 더 크도록 설정 */
  display: flex;
  width: 100%;
}

.fav-list {
  z-index: 999;
}
</style>
