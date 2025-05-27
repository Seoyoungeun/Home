<template>
  <div class="myposts-page">
    <QnaNav />
    <div class="myposts-wrapper">
      <div class="myposts-container">
        <h2>내가 쓴 글</h2>

        <table class="myposts-table">
          <thead>
            <tr>
              <th>글 번호</th>
              <th>제목</th>
              <th>카테고리</th>
              <th>작성일</th>
              <th>수정</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="post in myPosts" :key="post.id">
              <td>{{ post.id }}</td>
              <td>
                <RouterLink :to="`/${post.category}/detail/${post.pid}`">
                  {{ post.title || post.text || '(제목 없음)' }}
                </RouterLink>
              </td>
              <td>{{ post.category.toUpperCase() }}</td>
              <td>{{ post.date }}</td>
              <td>
                <RouterLink :to="`/${post.category}/edit/${post.pid}`" class="edit-btn">
                  수정
                </RouterLink>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'
import QnaNav from '@/components/QnaNav.vue'
import { aptAi } from '@/axios'

const myPosts = ref([])

onMounted(async () => {
  try {
    const response = await aptAi.get('/api/user/my', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`,
      },
    })
    myPosts.value = response.data.data //서버에서 받은 게시글 목록
  } catch (error) {
    console.error('내 글 불러오기 실패:', error)
  }
})
</script>

<style scoped>
.myposts-page {
  width: 100%;
}
.myposts-wrapper {
  display: flex;
  justify-content: center;
  width: 100%;
}
.myposts-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 1.5rem;
  background-color: #101014;
  color: white;
}
h2 {
  margin-bottom: 2rem;
  text-align: center;
}
.myposts-table {
  margin-top: 1rem;
  margin-bottom: 1rem;
  width: 100%;
  border-collapse: collapse;
  background-color: #1a1a1a;
  border-radius: 6px;
  overflow: hidden;
}
.myposts-table th,
.myposts-table td {
  padding: 0.75rem;
  border-bottom: 1px solid #333;
  text-align: center;
}
.myposts-table th {
  background-color: #222;
  font-weight: bold;
}
.edit-btn {
  background-color: #26bbff;
  color: #101014;
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  text-decoration: none;
  font-weight: bold;
}
.edit-btn:hover {
  background-color: #5ad2ff;
}
</style>
