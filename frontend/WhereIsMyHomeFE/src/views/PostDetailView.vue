<template>
  <div class="post-detail-page">
    <QnaNav />

    <div class="post-wrapper">
      <div class="post-container">
        <h1 class="post-title">{{ post.title }}</h1>

        <div class="post-meta">
          <span>작성자: {{ post.user_name }}</span>
          <span>조회수: {{ post.view_count || 0 }}</span>
          <span>작성일: {{ formatDate(post.created) }}</span>
          <span>수정일: {{ formatDate(post.updated) }}</span>
        </div>

        <hr />

        <div class="post-content">
          <p v-html="post.text"></p>
        </div>

        <hr />

        <div class="btn-box">
          <button @click="goBack">목록으로</button>
          <button
            v-if="
              currentUser &&
              (String(post.user_id) === String(currentUser.uid) || currentUser.role === 'ADMIN')
            "
            @click="deletePost"
            class="delete-button"
          >
            삭제
          </button>
        </div>
        <CommentList
          v-if="
            post &&
            post.pid !== undefined &&
            post.category !== undefined &&
            post.category !== 'notice'
          "
          :post-id="post.pid"
          :category="post.category"
          :current-user="currentUser"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import QnaNav from '@/components/QnaNav.vue'
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'
import { aptAi } from '@/axios'
import CommentList from '@/components/CommentList.vue'

const route = useRoute()
const router = useRouter()
const post = ref({})
const currentUser = ref(null)

async function fetchCurrentUser() {
  try {
    const response = await aptAi.get('/api/user/me')
    currentUser.value = response.data // currentUser에 저장
    console.log('유저 정보:', response.data)
  } catch (err) {
    console.error('사용자 정보 조회 실패:', err)
  }
}
async function fetchPost() {
  const id = route.params.id
  try {
    const response = await aptAi.get(`/api/v1/posts/${id}`)
    post.value = response.data.data
  } catch (error) {
    console.error('게시글 상세 조회 실패:', error)
  }
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return dateStr.slice(0, 10)
}

function goBack() {
  const category = route.params.category
  router.push(`/${category}`)
}

async function deletePost() {
  const confirmed = confirm('정말 삭제하시겠습니까?')
  if (!confirmed) return

  try {
    await aptAi.delete(`/api/v1/posts/${post.value.pid}`)
    alert('삭제가 완료되었습니다.')
    router.push(`/${route.params.category}`) // 목록으로 이동
  } catch (error) {
    console.error('삭제 실패:', error)
    alert('삭제에 실패했습니다.')
  }
}

onMounted(async () => {
  await fetchCurrentUser()
  await fetchPost()
})
</script>

<style scoped>
.post-detail-page {
  width: 100%;
}

.post-wrapper {
  display: flex;
  justify-content: center;
  width: 100%;
}

.post-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 1.5rem;
  background-color: #101014;
  color: white;
}

.post-title {
  font-size: 1.8rem;
  font-weight: bold;
  margin-top: 2rem;
  margin-bottom: 1rem;
}

.post-meta {
  display: flex;
  gap: 2rem;
  font-size: 0.9rem;
  color: #aaa;
  margin-bottom: 1rem;
  margin-top: 1rem;
}

.post-content {
  font-size: 1rem;
  line-height: 1.7;
  padding: 1.5rem 0;
}

hr {
  border: 0;
  border-top: 1px solid #333;
  margin: 1.5rem 0;
}

.btn-box {
  display: flex;
  justify-content: center;
  gap: 2rem;
}

.btn-box button {
  background-color: #666;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 0.6rem 1.3rem;
  cursor: pointer;
}
</style>
