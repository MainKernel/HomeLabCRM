<template>
    <select v-model="selectedWorkspaceId" @change="onWorkspaceChange">
    <option class="drop" v-for="workspace in workspaces" :key="workspace.id" :value="workspace.id">
      {{ workspace.name }}
    </option>
  </select>
</template>
<script setup>

import { ref, onMounted } from 'vue'
import api from '@/api/axiosInstance'

const workspaces = ref([])
const selectedWorkspaceId = ref(null)

// 1. Завантаження даних
const loadWorkspaces = async () => {
  try {
    const response = await api.get('/users/workspaces') 
    workspaces.value = response.data

    // 2. Логіка початкового вибору
    const savedId = localStorage.getItem('workspaceId')
    
    if (savedId) {
      // Якщо в пам'яті вже є ID, перетворюємо його на число і вибираємо
      selectedWorkspaceId.value = Number(savedId)
    } else if (workspaces.value.length > 0) {
      // Якщо пам'ять порожня, автоматично вибираємо перший простір зі списку
      selectedWorkspaceId.value = workspaces.value[0].id
      localStorage.setItem('workspaceId', selectedWorkspaceId.value)
    }
  } catch (error) {
    console.error('Помилка завантаження просторів', error)
  }
}

// 3. Збереження при кліку (зміні вибору)
const onWorkspaceChange = () => {
  localStorage.setItem('workspaceId', selectedWorkspaceId.value)
  
  // Щоб нові запити (наприклад, таблиця) підхопили новий ID, 
  // найпростіше перезавантажити сторінку:
  window.location.reload()
}

onMounted(() => {
  loadWorkspaces()
})
</script>

<style scoped>
select{
  background-color: transparent;
  color: var(--color-warning);
  border-color: transparent;
  border-radius: 3px;

}

.drop{
  background-color: var(--color-surface);
  color: var(--color-text);
}
</style>