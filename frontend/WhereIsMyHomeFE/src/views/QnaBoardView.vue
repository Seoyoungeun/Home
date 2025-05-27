<!-- src/views/QnaBoardView.vue -->
<template>
  <div class="qna-page">
    <QnaNav />

    <div class="qna-wrapper">
      <div class="qna-container">
        <div class="board-header">
          <h2>Q&A 게시판</h2>
        </div>

        <div class="board-controls">
          <RouterLink to="/qna/write" class="write-btn">글 작성</RouterLink>
        </div>

        <table class="board-table">
          <thead>
            <tr>
              <th>글 번호</th>
              <th>제목</th>
              <th>작성자</th>
              <th>댓글</th>
              <th>조회수</th>
              <th>작성일</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="post in posts" :key="post.pid">
              <td>{{ post.pid }}</td>
              <td>
                <RouterLink :to="`/qna/detail/${post.pid}`">{{ post.title }}</RouterLink>
              </td>
              <td>{{ post.user_name }}</td>
              <td>0</td>
              <td>{{ post.view_count || 0 }}</td>
              <td>{{ formatDate(post.created) }}</td>
            </tr>
          </tbody>
        </table>

        <div class="pagination">
          <button @click="goToPage(1)">&lt;&lt;</button>
          <button @click="goToPage(Math.max(currentPage - 1, 1))">&lt;</button>

          <button
            v-for="page in 10"
            :key="page"
            @click="goToPage(page)"
            :class="{ active: page === currentPage }"
          >
            {{ page }}
          </button>

          <button @click="goToPage(Math.min(currentPage + 1, 10))">&gt;</button>
          <button @click="goToPage(10)">&gt;&gt;</button>
        </div>

        <div class="search-bar">
          <select>
            <option>제목</option>
            <option>작성자</option>
          </select>
          <input type="text" placeholder="검색어를 입력하세요" />
          <button>검색</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import QnaNav from '@/components/QnaNav.vue'
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { RouterLink } from 'vue-router'

const currentPage = ref(1)
const posts = ref([])

function goToPage(page) {
  currentPage.value = page
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return dateStr.slice(0, 10)
}

async function fetchPosts() {
  try {
    const response = await axios.get('http://3.149.246.123:8080/api/v1/posts/category/qna')
    posts.value = response.data.data
    console.log('[게시글 목록]', posts.value)
  } catch (error) {
    console.error('게시글 목록 가져오기 실패:', error)
  }
}

onMounted(() => {
  fetchPosts()
})
</script>

<style scoped>
/* 전체 영역에서 가운데 정렬 */
.qna-page {
  width: 100%;
}
.qna-wrapper {
  display: flex;
  justify-content: center;
  width: 100%;
}
.qna-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 1.5rem;
  background-color: #101014;
  color: white;
}
.board-header {
  margin-top: 1.5rem;
  text-align: center;
}
.board-controls {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
}
.write-btn {
  background-color: #26bbff;
  padding: 0.5rem 1.2rem;
  border-radius: 5px;
  text-decoration: none;
  color: #101014;
  font-weight: bold;
  transition: background-color 0.3s;
}
.write-btn:hover {
  background-color: #5ad2ff;
}
.board-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
  background-color: #1a1a1a;
  border-radius: 6px;
  overflow: hidden;
}
.board-table th,
.board-table td {
  padding: 0.75rem;
  border-bottom: 1px solid #333;
  text-align: center;
}
.board-table th {
  background-color: #222;
  font-weight: 600;
}
.pagination {
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  margin: 1.5rem 0;
}
.pagination button {
  background: #222;
  color: white;
  padding: 0.5rem 0.8rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.pagination .active {
  background-color: #26bbff;
  color: black;
}
.search-bar {
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  margin-top: 2rem;
}
.search-bar input {
  padding: 0.5rem;
  width: 220px;
  border: none;
  border-radius: 4px;
}
.search-bar select,
.search-bar button {
  padding: 0.5rem;
  border: none;
  border-radius: 4px;
  background-color: #333;
  color: white;
}
.search-bar button:hover {
  background-color: #555;
}
</style>
