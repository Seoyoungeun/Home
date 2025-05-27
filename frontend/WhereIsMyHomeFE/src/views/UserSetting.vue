<template>
  <div class="settings-page">
    <h1>설정</h1>
    <p class="description">계정 상세 정보를 관리합니다.</p>

    <!-- 계정 정보 -->
    <section class="account-section">
      <h2>계정 정보</h2>
      <div class="info-row">
        <label>이름</label>
        <input v-model="name" :disabled="!isEditingName" />
        <template v-if="!isEditingName">
          <button class="icon-button" @click="startEditName"></button>
        </template>
        <template v-else>
          <div class="region-buttons">
            <button class="confirm" @click="confirmName">✔</button>
            <button class="cancel" @click="cancelName">✖</button>
          </div>
        </template>

        <label>이메일 주소</label>
        <input v-model="email" :disabled="!isEditingEmail" />
      </div>
    </section>

    <!-- 나의 지역 -->
    <section class="region-section">
      <h2>나의 지역</h2>
      <p class="description">나의 지역을 설정합니다.</p>
      <div class="region-select">
        <select v-model="selectedSido" :disabled="!isEditingRegion" @change="fetchGuguns">
          <option disabled value="">시/도</option>
          <option v-for="s in sidoList" :key="s">{{ s }}</option>
        </select>
        <select v-model="selectedGugun" :disabled="!isEditingRegion" @change="fetchDongs">
          <option disabled value="">시/군/구</option>
          <option v-for="g in gugunList" :key="g">{{ g }}</option>
        </select>
        <select v-model="selectedDong" :disabled="!isEditingRegion">
          <option disabled value="">읍/면/동</option>
          <option v-for="d in dongList" :key="d">{{ d }}</option>
        </select>

        <template v-if="!isEditingRegion">
          <button class="icon-button" @click="startEditRegion"></button>
        </template>
        <template v-else>
          <div class="region-buttons">
            <button class="confirm" @click="confirmRegion">✔</button>
            <button class="cancel" @click="cancelRegion">✖</button>
          </div>
        </template>
      </div>
    </section>

    <!-- 아파트 리스트 -->
    <section class="apt-list-section">
      <h2>내 아파트 리스트</h2>
      <p class="description">내 관심 아파트 목록을 관리합니다.</p>
      <ul class="apt-list">
        <li v-for="fav in fav_list" :key="fav.apt_seq" :fav="fav">
          <span class="apt-name" @click="handleClick(fav.apt_seq)">{{ fav.apt_nm }}</span>

          <button class="del-btn" @click="removeFav(fav.apt_seq)">삭제</button>
        </li>
      </ul>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { aptAi } from '@/axios'
const token = localStorage.getItem('jwt')
const user = JSON.parse(localStorage.getItem('user'))

const name = ref('')
const email = ref('')
const originalName = ref('')
const isEditingName = ref(false)

const selectedSido = ref('')
const selectedGugun = ref('')
const selectedDong = ref('')
const isEditingRegion = ref(false)

const sidoList = ref([])
const gugunList = ref([])
const dongList = ref([])

const fav_list = ref([])

onMounted(async () => {
  const storedUser = JSON.parse(localStorage.getItem('user'))
  if (storedUser) {
    name.value = storedUser.name
    email.value = storedUser.email
    originalName.value = storedUser.name
    selectedSido.value = storedUser.sido || ''
    selectedGugun.value = storedUser.gugun || ''
    selectedDong.value = storedUser.dong || ''
  }

  try {
    const res = await aptAi.get('/api/v1/dongcodes')
    sidoList.value = res.data.data.items
  } catch (e) {
    console.error('시/도 목록 불러오기 실패', e)
  }

  if (selectedSido.value) await fetchGuguns()
  if (selectedSido.value && selectedGugun.value) await fetchDongs()

  try {
    const response = await aptAi.get(`/api/v1/favs/list?uid=${user.uid}`)
    fav_list.value = response.data.data
  } catch (error) {
    console.error(error)
  }
})

const startEditName = () => {
  originalName.value = name.value
  isEditingName.value = true
}

const confirmName = async () => {
  isEditingName.value = false
  const storedUser = JSON.parse(localStorage.getItem('user'))
  const token = localStorage.getItem('jwt')

  if (!storedUser || !token) {
    alert('로그인이 만료되었습니다.')
    return
  }

  try {
    const updatedUser = {
      uid: storedUser.uid,
      email: storedUser.email,
      name: name.value,
      sido: storedUser.sido,
      gugun: storedUser.gugun,
      dong: storedUser.dong,
    }

    const res = await aptAi.put('/api/user', updatedUser, {
      headers: { Authorization: `Bearer ${token}` },
    })

    localStorage.setItem('user', JSON.stringify(res.data.data))
    alert('이름이 수정되었습니다.')
  } catch (error) {
    console.error('이름 수정 실패', error)
    alert('수정 중 오류 발생')
  }
}

const cancelName = () => {
  name.value = originalName.value
  isEditingName.value = false
}

const startEditRegion = () => {
  isEditingRegion.value = true
}

const cancelRegion = () => {
  isEditingRegion.value = false
}

const confirmRegion = async () => {
  isEditingRegion.value = false

  const storedUser = JSON.parse(localStorage.getItem('user'))
  const token = localStorage.getItem('jwt')

  if (!storedUser || !token) {
    alert('로그인이 만료되었습니다.')
    return
  }

  try {
    const updatedUser = {
      uid: storedUser.uid,
      email: storedUser.email,
      name: storedUser.name,
      sido: selectedSido.value,
      gugun: selectedGugun.value,
      dong: selectedDong.value,
    }

    const res = await aptAi.put('/api/user', updatedUser, {
      headers: { Authorization: `Bearer ${token}` },
    })

    localStorage.setItem('user', JSON.stringify(res.data.data))
    alert('지역 정보가 수정되었습니다.')
  } catch (e) {
    console.error('지역 수정 실패', e)
    alert('지역 설정 중 오류 발생')
  }
}

const fetchGuguns = async () => {
  try {
    const res = await aptAi.get(`/api/v1/dongcodes/${selectedSido.value}`)
    gugunList.value = res.data.data.items
  } catch (e) {
    console.error('구/군 목록 실패', e)
  }
}

const fetchDongs = async () => {
  try {
    const res = await aptAi.get(`/api/v1/dongcodes/${selectedSido.value}/${selectedGugun.value}`)
    dongList.value = res.data.data.items
  } catch (e) {
    console.error('동 목록 실패', e)
  }
}

const removeFav = async (aptId) => {
  try {
    await aptAi.delete(`/api/v1/favs?uid=${user.uid}&apt_seq=${aptId}`)
    const response = await aptAi.get(`/api/v1/favs/list?uid=${user.uid}`)
    fav_list.value = response.data.data
  } catch (error) {
    console.error(error)
  }
}
</script>

<style scoped>
.settings-page {
  max-width: 1350px;
  margin: 3rem auto;
  color: white;
}
.description {
  color: #aaa;
  margin-top: 0.8rem;
  margin-bottom: 1.5rem;
}
h1,
h2 {
  font-weight: 700;
}
section {
  margin-bottom: 3.75rem;
}
.info-row {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin-top: 1.5rem;
  margin-bottom: 1.5rem;
}
input {
  flex: 1;
  padding: 0.75rem;
  border-radius: 9px;
  background-color: #1a1a1a;
  border: 1.5px solid #444;
  color: white;
}
button {
  background-color: #26bbff;
  color: black;
  border: none;
  padding: 0.75rem;
  border-radius: 9px;
  cursor: pointer;
}

.region-select {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-top: 1rem;
  flex-wrap: wrap;
}

.region-select select {
  padding: 0.8rem;
  border-radius: 8px;
  margin-right: 1.5rem;
  min-width: 180px;
  background-color: #1a1a1a;
  border: 1.5px solid #444;
  color: white;
}

.region-buttons {
  display: flex;
  gap: 0.8rem;
}

.confirm {
  background-color: #00cc44;
  color: white;
  font-size: 1.1rem;
  width: 50px;
  height: 50px;
}
.cancel {
  background-color: #666;
  color: white;
  font-size: 1.1rem;
  width: 50px;
  height: 50px;
}
.apt-list {
  list-style: none;
  padding: 0;
  max-height: 500px;
  overflow-y: auto;
}
.apt-list li {
  background-color: #2a2a2a;
  margin-bottom: 1.05rem;
  padding: 1.2rem;
  border-radius: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.apt-list li button {
  background-color: #999;
  color: white;
}
.icon-button {
  width: 50px;
  height: 50px;
  border: none;
  background-color: transparent;
  background-image: url('@/components/icons/edit-pencil.png');
  background-size: cover;
  background-position: center;
  border-radius: 12px;
  cursor: pointer;
}
</style>
