<template>
  <tr @dblclick="goToItem(item.id)">
    <td>
      <img v-if="imageSrc" :src="imageSrc" alt="Фото деталі"
           class="item-img" height="50" width="50"/>
      <div v-else class="no-photo">
        <span>Немає фото</span>
      </div>
    </td>
    <td>{{ item.name }}</td>
    <td>{{ item.location }}</td>
    <td>{{ item.totalQuantity }} {{ item.unitsOfMeasure }}</td>
    <td>
      <RowEditButton
          :itemId="item.id"
      />
    </td>
  </tr>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue';
import RowEditButton from './RowEditButton.vue';
import api from "@/api/axiosInstance.js";
import {useRouter} from "vue-router";

const router = useRouter();

const goToItem = (itemId) => {
  router.push(`inventory/item/${itemId}`);
}

const props = defineProps({
  item: Object
});

// Реактивна змінна для зберігання готового URL картинки
const imageSrc = ref(null);

const fetchImage = async () => {
  // Якщо імені файлу немає, навіть не робимо запит
  if (!props.item.image) return;

  try {
    const response = await api.get(`/inventory/resources/preview/${props.item.image}`, {
      responseType: "blob"
    });

    // МАГІЯ: Перетворюємо потік байтів (Blob) на тимчасове посилання для браузера
    imageSrc.value = URL.createObjectURL(response.data);

  } catch (error) {
    console.error(`Не вдалося завантажити фото для ${props.item.name}:`, error);
  }
}

// Запускаємо запит ОДИН РАЗ при появі рядка таблиці на екрані
onMounted(() => {
  fetchImage();
});

// ВАЖЛИВО: Очищаємо пам'ять, коли рядок зникає (наприклад, при зміні сторінки пагінації)
onUnmounted(() => {
  if (imageSrc.value) {
    URL.revokeObjectURL(imageSrc.value);
  }
});
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

.item-img:hover {
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

tr:hover {
  border: 1px dashed black;
  box-shadow: 0px 0px 10px #635BFF;
}
</style>