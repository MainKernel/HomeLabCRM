<template>
    <div class="input-group">
        <label>Категорія</label>
        <div class="input-wrapper" style="position: relative;">
      <div class="input-container">     
      <input 
        v-model="searchQuery" 
        type="text" 
        placeholder="Резистори..."
        @focus="isDropdownOpen = true"
        @blur="handleBlur"
        @input="handleInput"
        required="true"
      />
      <small v-if="showNewCategoryMessage">Буде створено нова категорію</small>
      </div>
      <ul v-if="isDropdownOpen && filteredCategories.length > 0"
         class="dropdown-list">
        <li 
          v-for="cat in filteredCategories" 
          :key="cat.id"
          @mousedown.prevent="selectItem(cat)" 
        >
          {{ cat.name }}
        </li>
      </ul>

    </div>
    </div>
</template>
<script setup>
import { onMounted, ref, computed } from 'vue';
import { storeToRefs } from 'pinia';
import { useModalStore } from './modalStore';

const modalStore = useModalStore()
const { categories, formData } = storeToRefs(modalStore)
const { fetchCategories } = modalStore

const isDropdownOpen = ref(false)

const searchQuery = ref(formData.value.category || '') 

onMounted(async () => {
    await fetchCategories() 
})

const filteredCategories = computed(() => {
    if (!categories.value) return [];
    if (!searchQuery.value) return categories.value;
    
    const query = searchQuery.value.toLowerCase();
    return categories.value.filter(cat => 
        cat.name?.toLowerCase().includes(query)
    );
})
const showNewCategoryMessage = computed(() => {
    const query = searchQuery.value.trim().toLowerCase();
    
    // Не показуємо, якщо інпут порожній
    if (!query) return false;
    
    // Шукаємо точний збіг у базі
    const exactMatch = categories.value.find(
      cat => cat.name?.toLowerCase() === query
    );
    
    return !exactMatch;
});

// --- ОБРОБНИКИ ПОДІЙ ---

const handleInput = () => {
    isDropdownOpen.value = true
    formData.value.category = searchQuery.value
}

const selectItem = (item) => {
    searchQuery.value = item.name       // Підставляємо гарну назву в інпут
    formData.value.category = item.name // Передаємо фінальне значення в Pinia Store (або item.id, якщо бекенд чекає ID)
    isDropdownOpen.value = false        // Ховаємо меню
}

const handleBlur = () => {
    isDropdownOpen.value = false
}
</script>
<style src="./style/categoryfieldcomponent.css" scoped></style>