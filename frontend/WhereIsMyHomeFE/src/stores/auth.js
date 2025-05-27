// src/stores/auth.js
import { ref } from 'vue'

export const currentUser = ref(
  localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null,
)

export function setCurrentUser(user) {
  currentUser.value = user
  localStorage.setItem('user', JSON.stringify(user))
}

export function clearCurrentUser() {
  currentUser.value = null
  localStorage.removeItem('user')
  localStorage.removeItem('jwt')
}
