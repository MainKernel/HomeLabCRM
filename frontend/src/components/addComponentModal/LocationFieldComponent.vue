<template>
    <div class="input-group">
        <label>Місцезнаходження</label>
        
        <div class="input-wrapper">
            <div class="input-container">
                <input 
                    v-model="searchQuery"
                    type="text"
                    placeholder="Полиця 1 | Бокс 7 | Комірка 3"
                    @focus="isDropdownOpen = true"
                    @blur="handleBlur"
                    @input="handleInput"
                    required="true"
                />
                <small v-if="showNewCategoryMessage">Буде створено нову категорію</small>
            </div>

            <ul 
                v-if="isDropdownOpen && filterLocatons.length > 0"
                class="dropdown-list"
            >
                <li 
                    v-for="loc in filterLocatons" 
                    :key="loc.id"
                    @mousedown.prevent="selectItem(loc)"
                >
                    {{ loc.name }}
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
const { formData, locations } = storeToRefs(modalStore)
const { fetchLocations } = modalStore

const isDropdownOpen = ref(false)
const searchQuery = ref(formData.value.location || '')

onMounted( async () =>{
    await fetchLocations()
})

const filterLocatons = computed (() => {
    if(!locations.value) return [];
    if(!searchQuery.value) return locations.value;

    const query = searchQuery.value.toLowerCase();

    return locations.value.filter(loc => 
    loc.name?.toLowerCase().includes(query))
});

const showNewCategoryMessage = computed(() => {
    const query = searchQuery.value.trim().toLowerCase();
    
    // Не показуємо, якщо інпут порожній
    if (!query) return false;
    
    // Шукаємо точний збіг у базі
    const exactMatch = locations.value.find(
      loc => loc.name?.toLowerCase() === query
    );
    
    // Якщо точного збігу НЕМАЄ, значить категорія нова (показуємо повідомлення)
    return !exactMatch;
});
const handleInput = () => {
    isDropdownOpen.value = true
    formData.value.location = searchQuery.value
}

const selectItem = (item) => {
    searchQuery.value = item.name       // Підставляємо гарну назву в інпут
    formData.value.location = item.name // Передаємо фінальне значення в Pinia Store (або item.id, якщо бекенд чекає ID)
    isDropdownOpen.value = false        // Ховаємо меню
}

const handleBlur = () => {
    isDropdownOpen.value = false
}

</script>
<style scoped>
label {
    color: whitesmoke;
    font-weight: 900;
    padding-left: 5px;
}

.input-wrapper {
    position: relative; /* ОБОВ'ЯЗКОВО! Якір для випадаючого списку */
    margin-top: 5px;
    margin-left: 5px;
    margin-right: 20px; /* Обмежуємо ширину обгортки */
}

.input-container {
    display: flex;
    flex-direction: column;
}

input {
    color: whitesmoke;
    background-color: var(--color-surface, #1e1e1e);
    border: 2px solid #9c50ff8d;
    min-height: 25px;
    padding-left: 10px;
    border-radius: 5px;
    width: 100%; /* Інпут займає всю ширину батька */
    box-sizing: border-box; /* Щоб падінги не ламали ширину */
}

input:focus {
    outline: none;
    box-shadow: 0 0 5px rgba(0, 255, 65, 0.5), 
                0 0 15px rgba(0, 255, 65, 0.4);
}

small {
    color: rgba(0, 255, 65, 0.7);
    margin-top: 4px;
    margin-left: 5px;
    font-size: 8px;
}

/* --- МАГІЯ ВИПАДАЮЧОГО СПИСКУ --- */
.dropdown-list {
    position: absolute; /* Вириваємо з сітки */
    top: 100%; /* Одразу під інпутом */
    left: 0;
    width: 100%; /* Ширина точно як у інпута */
    z-index: 1000; /* Перекриваємо все інше */
    
    margin: 4px 0 0 0;
    padding: 0;
    background-color: #1E293B; /* Суцільний фон для списку */
    border: 1px solid #9D50FF;
    border-radius: 6px; /* Акуратні кути */
    
    /* Якщо локацій буде 100+, додаємо скрол */
    max-height: 200px; 
    overflow-y: auto;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5);
}

.dropdown-list li {
    color: whitesmoke;
    list-style: none;
    padding: 8px 10px;
    cursor: pointer;
    border-bottom: 1px dashed rgba(156, 80, 255, 0.3); /* Легкий розділювач */
    transition: background-color 0.2s;
}

/* Прибираємо лінію в останнього елемента */
.dropdown-list li:last-child {
    border-bottom: none; 
}

/* Ефект наведення на пункт меню */
.dropdown-list li:hover {
    background-color: rgba(157, 80, 255, 0.2); 
    color: #9D50FF;
}
</style>