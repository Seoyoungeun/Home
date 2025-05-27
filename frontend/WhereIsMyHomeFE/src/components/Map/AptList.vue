<template>
  <div>
    <AptListItem
      v-for="apt in props.apt_list"
      :key="apt.apt_seq"
      :apt="apt"
      @update:apt_selected="handleAptSelect($event)"
    />
  </div>
</template>

<script setup>
import { aptAi } from '@/axios'
import { ref, watch } from 'vue'
import AptListItem from '@/components/Map/AptListItem.vue'
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  apt_list: Object,
})

const emit = defineEmits(['updated:apt_selected'])
const handleAptSelect = (apt_selected) => {
  emit('updated:apt_selected', apt_selected)
}
</script>

<style scoped></style>
