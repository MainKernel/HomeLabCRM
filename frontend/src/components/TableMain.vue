<template>
  <div class="table-box">
    <div class="table-scroll-wrapper">
    <table>
      <thead >
        <tr>
          <th class="col-img">Фото</th>
          <th class="col-name">Назва</th>
          <th class="col-location">Місцезнаходження</th>
          <th class="col-qty">Кількість</th>
          <th class="col-actions"></th>
        </tr>
      </thead>

      <tbody>
        <TableRow 
          v-for="item in items" 
          :key="item.id" 
          :item="item" 
        />
      </tbody>

    </table>
    </div>
  </div>
  <InventoryPagging
    :currentPage="currentPage"
    :totalPages="totalPages"
    :itemsPerPage="itemsPerPage"
    @change-page="handlePageChange"
    @size-change="handleSizeChange"/>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useInventoryStore } from '../modules/inventory/store/inventoryStore';

// 1. Імпортуємо наш створений компонент рядка
import TableRow from './TableRow.vue' 
import InventoryPagging from './InventoryPagging.vue';


import { storeToRefs } from 'pinia';

const inventoryStore = useInventoryStore()
const {items, activeLocationId, totalPages, currentPage, itemsPerPage} = storeToRefs(inventoryStore)

const {fetchItemsForLocation } = inventoryStore;

onMounted(async () => {
  activeLocationId.value = 1; 
  await fetchItemsForLocation(activeLocationId.value);
})

const handlePageChange = async (newPage, newSize) =>{
  await fetchItemsForLocation(activeLocationId.value, newPage, newSize)
}

const handleSizeChange = async (newSize) => {
  // 1. Оновлюємо значення в сховищі Pinia
  itemsPerPage.value = newSize; 
  localStorage.setItem('itemsPerPage', newSize);  
  // 2. Золоте правило: завантажуємо сторінку 0 з новим розміром
  await fetchItemsForLocation(activeLocationId.value, 0); 
}


</script>


<style scoped>
.table-box {
  height: 80vh;
    display: grid;
    grid-template-columns: 1fr 6fr 6fr 1fr;
}

/* 1. НОВИЙ КОНТЕЙНЕР ДЛЯ СКРОЛУ */
.table-scroll-wrapper {
    grid-column: 2 / span 2;
    
    /* Фіксуємо висоту. vh - це відсоток від висоти екрану, дуже зручно! */
    height: 80vh; 
    min-height: 400px; 
    
    /* Вмикаємо вертикальний скрол */
    overflow-y: auto; 
    
    /* Перенесли сюди Glassmorphism з table */
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.35);
    border-radius: 20px;
    box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
}

/* 2. НАЛАШТУВАННЯ САМОЇ ТАБЛИЦІ */
table {
    width: 100%;
    /* border-collapse: collapse ВАЖЛИВО! Без цього між липким заголовком і рядками буде щілина */
    border-collapse: collapse; 
}

/* 3. ЛИПКИЙ ЗАГОЛОВОК */
th {
    /* Приклеюємо до верху */
    position: sticky;
    top: 0;
    z-index: 10; /* Щоб заголовок завжди був поверх рядків */
    
    /* СЕКРЕТНИЙ ІНГРЕДІЄНТ:
      Заголовок не може бути повністю прозорим, інакше текст рядків 
      змішається з текстом заголовка при скролі.
      Даємо йому щільний фон або сильний блюр.
    */
    background-color: #2b2b2b; /* Темний колір вашого фону */
    /* АБО красивий скляний варіант: */
    /* background: rgba(30, 30, 30, 0.85); backdrop-filter: blur(10px); */
    
    padding: 15px; /* Задаємо відступи всередині заголовка */
    
    border-right: 2px solid whitesmoke;

    color: white;
}

th:last-child {
    border-right: transparent;
}

tr {
    text-align: center;
    height: 50px;
}

tr:nth-child(even) {
    background-image: linear-gradient(170deg, rgba(255,255,255,0.05), rgba(255,255,255, 0.1));
}

/* Дизайн скролбару для webkit-браузерів */
.table-scroll-wrapper::-webkit-scrollbar {
  width: 8px;
}
.table-scroll-wrapper::-webkit-scrollbar-track {
  background: transparent; 
}
.table-scroll-wrapper::-webkit-scrollbar-thumb {
  background: rgba(157, 80, 255, 0.5); /* Напівпрозорий фіолетовий */
  border-radius: 10px;
}
.table-scroll-wrapper::-webkit-scrollbar-thumb:hover {
  background: rgba(157, 80, 255, 0.8);
}

/* Ширина колонок */
.col-img { width: auto; }       
.col-name { width: 40%; }       
.col-location { width: 30%; }
.col-qty { width: 15%; }        
.col-actions { width: 5%; } 
</style>