<template>
  <div class="post-write-page">
    <QnaNav />
    <div class="post-wrapper">
      <div class="post-form">
        <div class="form-row">
          <label for="category">카테고리</label>
          <select v-model="category" id="category">
            <option value="notice">공지사항</option>
            <option value="qna">QNA</option>
            <option value="free">자유</option>
          </select>

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
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import QnaNav from '@/components/QnaNav.vue'
import { aptAi } from '@/axios' // JWT 포함된 axios 인스턴스
import axios from 'axios'

const route = useRoute()
const router = useRouter()

const category = ref('qna')
const title = ref('')
const content = ref('')

// 게시글 ID 가져오기
const postId = route.params.id

onMounted(async () => {
  try {
    const response = await aptAi.get(`/api/v1/posts/${postId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
    const post = response.data.data
    title.value = post.title
    content.value = post.text
  } catch (error) {
    console.error('게시글 불러오기 실패:', error)
    alert('글 정보를 불러올 수 없습니다.')
  }
})

function cancel() {
  router.back()
}

async function submit() {
  const token = localStorage.getItem('jwt')
  if (!token) {
    alert('로그인이 필요합니다.')
    router.push('/login')
    return
  }

  try {
    await axios.patch(
      'http://localhost:8080/api/v1/posts',
      {
        pid: postId, // 서버에서 pid를 기반으로 수정하므로 포함 필요
        title: title.value,
        text: content.value,
        category: category.value,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      },
    )

    alert('수정 완료!')
    router.push(`/${category.value}/detail/${postId}`)
  } catch (err) {
    console.error('수정 실패:', err)
    alert('수정 중 오류 발생!')
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
