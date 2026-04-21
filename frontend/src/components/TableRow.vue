<template>
  <tr>
    <td>
    <img v-if="item.image" :src="item.image" alt="Фото деталі" 
    height="50" width="50" class="item-img"/>
    <div v-else class="no-photo" >
      <span>Немає фото</span>
    </div>
    </td>
    <td>{{ item.name }}</td>
    <td>{{ item.location }}</td>
    <td>{{ item.totalQuantity}} {{ item.unitOfMeasure }}</td>
    <td>
      <RowEditButton
      :item="item.id"
      />
    </td>
  </tr>
</template>

<script setup>
import RowEditButton from './RowEditButton.vue';

defineProps({
  item: Object
})
</script>

<style scoped>
td {
  vertical-align: middle; 
}

/* 2. Спільні правила для обох (і картинки, і заглушки) */
.item-img, .no-photo {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  
  /* СЕКРЕТ №1: Робимо їх блоками, щоб вбити зайві 3px знизу */
  display: flex; 
  margin: 0 auto; /* Центруємо в комірці */
  
  /* СЕКРЕТ №2: Вказуємо, що рамка входить у ці 60px, а не додається зверху */
  box-sizing: border-box; 
  filter: grayscale(100%);
}

/* 3. Індивідуальні правила для справжнього фото */
.item-img {
  object-fit: cover;
  border: 1px solid rgba(255, 255, 255, 0.2);
  background-color: white; /* Щоб деталі на прозорому фоні виглядали акуратно */
}
.item-img:hover{
  scale: 2;
  position: relative;
  z-index: 999;
}

/* 4. Індивідуальні правила для заглушки */
.no-photo {
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.05);
  
  /* СЕКРЕТ №3: Додаємо прозору рамку 1px, щоб розмір збігався з .item-img */
  border: 1px solid transparent; 
  
  font-size: 0.7rem;
  color: #888;
  text-align: center;
  line-height: 1.1; /* Щоб текст "Немає фото" не розпирав блок */
}
tr:hover{
  border: 1px dashed black;
  box-shadow: 0px 0px 10px #635BFF;
}
</style>