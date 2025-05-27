<template>
  <div class="post-write-page">
    <QnaNav />
    <div class="post-wrapper">
      <div class="post-form">
        <div class="form-row">
          <label for="title">제목</label>
          <input type="text" id="title" v-model="title" class="title-input" />
        </div>

        <div class="form-row">
          <label for="content">내용</label>
          <textarea
            id="content"
            rows="15"
            v-model="content"
            placeholder="내용을 입력해 주세요."
          ></textarea>
        </div>

        <div class="form-buttons">
          <button class="cancel-btn" @click="cancel">취소</button>
          <button class="submit-btn" @click="submit">완료</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import QnaNav from '@/components/QnaNav.vue'
import { aptAi } from '@/axios'

const props = defineProps({
  category: String,
  redirectPath: String,
})

const title = ref('')
const content = ref('')
const router = useRouter()

function cancel() {
  router.back()
}

async function submit() {
  const token = localStorage.getItem('jwt')
  const user = JSON.parse(localStorage.getItem('user'))

  if (!token || !user) {
    alert('로그인이 필요합니다.')
    router.push('/login')
    return
  }

  const post = {
    user_id: user.uid,
    title: title.value,
    text: content.value,
    category: props.category,
  }

  try {
    await aptAi.post('/api/v1/posts', post, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    alert('작성 완료!')
    router.push(props.redirectPath)
  } catch (error) {
    console.error('글 작성 실패:', error)
    alert('글 작성 중 오류가 발생했습니다.')
  }
}
</script>

<style scoped>
.post-write-page {
  width: 100%;
  min-height: 100vh;
}

.post-wrapper {
  display: flex;
  justify-content: center;
  width: 100%;
}

.post-form {
  width: 100%;
  max-width: 1200px;
  margin: 0;
  padding: 4rem; /* 좌우 여백을 확보해 중앙 느낌 강화 */
  background-color: #101014;
  color: white;
}

.form-row {
  display: flex;
  align-items: center;
  gap: 1.7rem;
  margin-bottom: 3rem;
  flex-wrap: wrap;
}

.title-input {
  width: 100%;
  max-width: 55%;
  min-width: 800px;
}

label {
  font-weight: 650;
  min-width: 80px;
  font-size: 1.3rem; /* ← 추가 */
}

textarea {
  width: 100%;
  padding: 1rem;
  background-color: #1a1a1a;
  border: 1px solid #555;
  border-radius: 10px;
  color: white;
  resize: vertical;
  min-height: 400px; /* ← 높이 확장 */
  font-size: 1rem; /* ← 입력 폰트 가독성 향상 */
}
select,
input[type='text'] {
  padding: 0.5rem;
  min-width: 200px;
  background-color: #1a1a1a;
  border: 1px solid #555;
  color: white;
  border-radius: 4px;
}

.form-buttons {
  display: flex;
  justify-content: center;
  gap: 4rem;
  margin-top: 2rem;
}

.cancel-btn,
.submit-btn {
  padding: 0.7rem 1.8rem;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
.cancel-btn {
  background-color: #555;
  color: white;
}
.submit-btn {
  background-color: #26bbff;
  color: black;
}
</style>
