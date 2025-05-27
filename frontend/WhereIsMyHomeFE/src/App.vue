<template>
  <section class="app">
    <header class="main-header">
      <MainNav />
    </header>

    <main
      class="main-content"
      :class="{
        centered: isCenterView,
        fixed: isFixed,
        'no-scroll': isNoScroll,
      }"
    >
      <RouterView />
    </main>
  </section>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, RouterView } from 'vue-router'
import MainNav from './components/MainNav.vue'

const route = useRoute()

const isCenterView = computed(() => route.meta.center === true)
const isFixed = computed(() => route.meta.fixed === true)
const isNoScroll = computed(() => route.path === '/map' || route.path === '/') // ✅ 여기서 /map일 때만 no-scroll
</script>
