import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      meta: { center: true },
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/map',
      name: 'map',
      component: () => import('../views/MapView.vue'),
    },
    {
      path: '/forum',
      name: 'forum',
      redirect: '/notice', // 게시판 누르면 공지사항으로 이동
    },
    {
      path: '/notice',
      name: 'notice',
      component: () => import('@/views/NoticeView.vue'),
    },
    {
      path: '/qna',
      name: 'qna',
      component: () => import('@/views/QnaBoardView.vue'),
    },
    {
      path: '/free',
      name: 'free',
      component: () => import('@/views/FreeView.vue'),
    },
    {
      path: '/notice/write',
      component: () => import('@/views/NoticeWriteView.vue'),
    },
    {
      path: '/qna/write',
      component: () => import('@/views/QnaWriteView.vue'),
    },
    {
      path: '/free/write',
      component: () => import('@/views/FreeWriteView.vue'),
    },
    {
      path: '/settings',
      name: 'settings',
      component: () => import('@/views/UserSetting.vue'),
    },
    {
      path: '/signup',
      name: 'signup',
      meta: { center: true },
      component: () => import('@/views/UserSignup.vue'),
    },
    {
      path: '/:category/detail/:id',
      name: 'PostDetail',
      component: () => import('@/views/PostDetailView.vue'),
    },
    {
      path: '/findPW',
      name: 'findPW',
      meta: { center: true },
      component: () => import('@/views/FindPW.vue'),
    },
    {
      path: '/myposts',
      name: 'myposts',
      component: () => import('@/views/MyPostsView.vue'),
    },
    {
      path: '/:category/edit/:id',
      name: 'PostEdit',
      component: () => import('@/views/PostEditView.vue'),
    },
  ],
})

export default router
