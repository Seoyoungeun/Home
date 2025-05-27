<template>
  <div class="comment-section">
    <h3>댓글 ({{ comments.length }})</h3>

    <!-- 댓글 작성 영역 -->
    <div v-if="canWriteComment" class="comment-input">
      <textarea v-model="newComment" placeholder="댓글을 입력하세요"></textarea>
      <button @click="addComment">등록</button>
    </div>

    <!-- 댓글 목록 -->
    <ul class="comment-list">
      <li v-for="comment in comments" :key="comment.cid" class="comment-item">
        <div class="comment-header">
          <strong>User {{ comment.userId }}</strong>
          <span>{{ formatDate(comment.created) }}</span>
        </div>
        <p class="comment-content">{{ comment.content }}</p>
        <button v-if="canDelete(comment)" @click="deleteComment(comment.cid)" class="delete-button">
          삭제
        </button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { aptAi } from '@/axios'
import { computed } from 'vue'

const props = defineProps({
  postId: { type: Number, required: true },
  category: { type: String, required: true },
  currentUser: { type: Object, required: false },
})

const comments = ref([])
const newComment = ref('')

// 댓글 작성 가능 여부
const canWriteComment = computed(() => {
  if (!props.currentUser) return false
  if (props.category === 'notice') return false
  if (props.category === 'qna' && props.currentUser.role !== 'ADMIN') return false
  return true
})

// 댓글 불러오기
async function fetchComments() {
  try {
    const response = await aptAi.get(`/api/comments/${props.postId}`)
    comments.value = response.data.data
  } catch (err) {
    console.error('댓글 불러오기 실패:', err)
  }
}

// 댓글 작성
async function addComment() {
  if (!newComment.value.trim()) {
    alert('댓글 내용을 입력하세요.')
    return
  }

  try {
    await aptAi.post('/api/comments', {
      postId: props.postId,
      content: newComment.value,
      category: props.category,
    })
    newComment.value = ''
    await fetchComments()
  } catch (err) {
    console.error('댓글 등록 실패:', err)
    alert('댓글 등록 중 오류 발생')
  }
}

// 댓글 삭제 가능 여부
function canDelete(comment) {
  return (
    props.currentUser &&
    (comment.userId === props.currentUser.uid || props.currentUser.role === 'ADMIN')
  )
}

// 댓글 삭제
async function deleteComment(cid) {
  if (!confirm('정말 삭제하시겠습니까?')) return

  try {
    await aptAi.delete(`/api/comments/${cid}`)
    await fetchComments()
  } catch (err) {
    console.error('댓글 삭제 실패:', err)
    alert('댓글 삭제 중 오류 발생')
  }
}

function formatDate(str) {
  return str ? str.slice(0, 10) : ''
}

onMounted(() => {
  fetchComments()
})

watch(
  () => props.postId,
  () => {
    fetchComments()
  },
)
</script>

<style scoped>
.comment-section {
  margin-top: 2rem;
  padding: 1rem;
  background-color: #1a1a1a;
  border-radius: 8px;
  color: white;
}
.comment-input textarea {
  width: 100%;
  height: 80px;
  padding: 0.5rem;
  margin-bottom: 0.5rem;
  background-color: #2a2a2a;
  color: white;
  border: 1px solid #444;
  border-radius: 6px;
}
.comment-input button {
  background-color: #26bbff;
  border: none;
  color: black;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
}
.comment-list {
  list-style: none;
  padding: 0;
  margin-top: 1rem;
}
.comment-item {
  border-bottom: 1px solid #444;
  padding: 0.8rem 0;
}
.comment-header {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: #aaa;
}
.comment-content {
  margin-top: 0.3rem;
}
.delete-button {
  margin-top: 0.3rem;
  font-size: 0.8rem;
  background-color: #ff4d4d;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 0.3rem 0.6rem;
  cursor: pointer;
}
</style>
