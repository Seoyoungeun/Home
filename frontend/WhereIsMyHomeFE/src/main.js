import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { useKakao } from 'vue3-kakao-maps/@utils';

useKakao('edd62ebe68e95e8cae501ed613b68c04');

const app = createApp(App)

app.use(router)

app.mount('#app')
