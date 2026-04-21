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
<style scoped>
label{
    color: whitesmoke;
    font-weight: 900;
    padding-left: 5px;
}
input{
    color: whitesmoke;
    background-color: var(--color-surface, #1e1e1e);
    border: 2px solid #9c50ff8d;
    min-height: 25px;
    padding-left: 10px;
    border-radius: 5px;
    margin: 0px 0px 0px 0px;
}
.input-wrapper{
    margin-top: 5px;
    margin-left: 5px;
}
input:focus{
    outline: none;
    box-shadow: 0 0 5px rgba(0, 255, 65, 0.5), 
              0 0 15px rgba(0, 255, 65, 0.4);
}
.input-container{
    display: flex;
    flex-direction: column;
    margin-right: 20px;
}

small{
    color: rgba(0, 255, 65, 0.7);
    margin-left: 10px;
    font-size: 8px;
}
.dropdown-list{
    margin-top: 0px;
    padding-top: 0px;
}
ul{
    position: absolute;
    margin-left: 0px;
    padding-left: 0px;
    border-radius: 20px;
    min-height: 30vh;
    width: 100%;
}
li{
    color: grey;
    margin-left: 0px;
    min-height: 30px;
    align-content: center;
    list-style: none;
    padding-right: 0px;
    margin-right: 20px;
    background-color: #1E293B;
    border: 1px dashed gray;
    padding-left: 5px;
}
</style>