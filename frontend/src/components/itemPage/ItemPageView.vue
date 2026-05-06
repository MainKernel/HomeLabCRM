<template>
  <div v-if="isLoading" class="item-not-found">
    6
  </div>
  <div v-else-if="item" class="content-loader">
    <div class="page-header">
      <h1 class="title">Компонент</h1>
    </div>

    <div class="content">
      <div class="item-header">
        <input v-model="item.name" :disabled="!isEditing" autocomplete="off" class="item-name" type="text"/>
        <div class="editing-box">
          <div v-if="isEditing" class="buttons">
            <button class="cancel-btn" @click="cancelChanges()">Скасувати</button>
            <button class="save-btn" @click="saveChanges()">Зберігти</button>
          </div>
          <div class="switch" @click="store.isEditing = !store.isEditing">
            <div :class="{ 'active': isEditing }" class="knob">
            </div>
          </div>
        </div>
      </div>
      <div class="main-content">
        <div class="image">
          <!-- Прихований інпут -->
          <input
              ref="fileInputRef"
              accept="image/*"
              style="display: none;"
              type="file"
              @change="handleFileChange"
          />
          <!-- Пріоритет 1: Показуємо НОВЕ прев'ю, якщо воно є -->
          <img v-if="previewUrl"
               :class="{ 'clickable': isEditing }"
               :src="previewUrl"
               alt="Preview"
               class="item-img"
               @click="triggerImgInput"/>
          <!-- Пріоритет 2: Показуємо СТАРЕ фото з сервера, якщо немає прев'ю -->
          <img v-else-if="loadedImg"
               :class="{ 'clickable': isEditing }"
               :src="loadedImg"
               alt="Фото деталі"
               class="item-img"
               @click="triggerImgInput"/>
          <!-- Пріоритет 3: Заглушка, ЯКА ТЕЖ КЛІКАБЕЛЬНА -->
          <div v-else
               :class="{ 'clickable': isEditing }"
               class="item-img placeholder"
               @click="triggerImgInput">
            Натисніть, щоб додати фото
          </div>
        </div>
        <div class="main-info">
          <!-- Локація компонентів -->
          <InputField
              v-model="item.location"
              :disabled="!isEditing"
              :options="locations"
              :required=true
              displayKey="name"
              label="Місцезнаходження"
              placeholder="Полиця 1 | Коробка А"
              showNewItemMessage="Додано нове місцезнаходження"
          />
          <!-- Кількість -->
          <div class="input-wrapper">
            <label>Кількість</label>
            <input v-model="item.totalQuantity"
                   :disabled="!isEditing"
                   class="text-field"
                   min="0"
                   required
                   type="number"/>
          </div>
          <!-- Одиниці виміру -->
          <div class="input-wrapper">
            <label>Одиниці вимірювання</label>
            <div class="input-select-wrapper">
              <select id="1" v-model="item.unitsOfMeasure" :disabled="!isEditing" required>
                <option value="PCS">ШТУК</option>
                <option value="PACK">ПАЧОК</option>
                <option value="KG">КІЛОГРАМ</option>
                <option value="GRAMS">ГРАММ</option>
                <option value="METERS">МЕТРІВ</option>
                <option value="UDEF">НЕВИЗНАЧЕНО</option>
              </select>
            </div>
          </div>
        </div>
        <div class="main-info-table">
          <div class="main-info-box-1">
            <!-- Тип -->
            <div class="input-wrapper">
              <label>Тип</label>
              <div class="input-select-wrapper">
                <select id="2" v-model="item.itemType" :disabled="!isEditing" required>
                  <option value="REGULAR">Звичайний</option>
                  <option value="UNIQUE">Унікальний</option>
                </select>
              </div>
            </div>

            <!-- SKU -->
            <div class="input-wrapper">
              <label>SKU</label>
              <input v-model="item.sku"
                     :disabled="!isEditing"
                     class="text-field"
                     type="text"/>
            </div>
            <!-- Серійний номер -->
            <div class="input-wrapper">
              <label>Серійний номер</label>
              <input v-model="item.serialNumber"
                     :disabled="!isEditing"
                     class="text-field"
                     type="text"/>
            </div>
          </div>
          <div class="main-info-box-2">
            <!-- Категорія -->
            <InputField
                v-model="item.category"
                :disabled="!isEditing"
                :options="category"
                :required=true
                displayKey="name"
                label="Категорія"
                placeholder="Резистор"
                showNewItemMessage="Додано нову категорю"
            />
            <!-- Мінімальна кількість -->
            <div class="input-wrapper">
              <label>Мін. кількість (Alert)</label>
              <input v-model="item.minStock" :disabled="!isEditing" min="0" required type="number"/>
            </div>

            <!-- Тип корпусу -->
            <div class="input-wrapper">
              <label>Тип корпусу</label>
              <input v-model="item.packageType" :disabled="!isEditing"
                     class="text-field"
                     type="text"
              />
            </div>
          </div>
        </div>
        <div class="params-wrapper">
          <div class="param-wrapper">
            <label>Параметри</label>
            <div class="param-input-wrapper">
              <input v-model="newKeyValue" :disabled="!isEditing" type="text"/>
              <input v-model="newParamValue" :disabled="!isEditing" type="text"/>
              <button :disabled="!isEditing" class="param-add-btn" @click.prevent="addParam()">+</button>
            </div>
            <div class="param-list">
              <div v-for="(val, key) in item.parameters" :key="key" class="param-item">
                <div class="param-holder">
                  <strong>{{ key }}:</strong>
                  <p>{{ val }}</p>
                  <button :disabled="!isEditing" class="param-del-btn" @click.prevent="removeParam(key)">X</button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="change-log">
          <label>Лог</label>
          <template v-if="changeLog && changeLog.length > 0">
            <div v-for="log in sortedChangeLog" :key="log.id" class="change-log-card">
              <div class="log-header">
                <p class="change-log-title">{{ log.changeType }}</p>
                <p class="change-log-time">{{ formatDateTime(log.createdAt) }}</p>
                <p class="change-log-user">{{ log.changedBy }}</p>
              </div>
              <div class="log-body">
                <p class="log-note">Що змінено: {{ log.note }}</p>
                <p class="log-quantity-changed">Змінено на: {{ log.quantityChanged }}</p>
                <p class="log-unit-price">Ціна за одиницю: {{ log.unitPrice }}</p>
                <p class="log-unit-total-price">Загальна ціна: {{ log.totalPrice }}</p>
              </div>
            </div>
          </template>
          <div v-else>
            <p>Лог відсутній</p>
          </div>
        </div>
        <div class="documents">
          documents
        </div>
        <div class="note-wrapper">
          <div class="input-wrapper">
            <label>Примітка</label>
            <textarea id="3" v-model="item.note" :disabled="!isEditing"/>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import InputField from "@/components/itemPage/components/InputField.vue";
import {storeToRefs} from "pinia";
import {computed, onMounted, ref, toRaw} from "vue";
import {useItemPageStore} from "@/components/itemPage/itempageStore.js";
import {useRoute} from "vue-router";
import api from "@/api/axiosInstance.js";

const store = useItemPageStore();
const route = useRoute();
const {
  isEditing, item, loadedImg, locations,
  category, updateDto, changeLog
} = storeToRefs(store);
const {fetchItem, fetchLocations, fetchCategories, imgLoader} = store;

const isLoading = ref(true);
const newParamValue = ref("");
const newKeyValue = ref("");

const fileInputRef = ref(null);
const previewUrl = ref(null);
const formDataImage = ref(null);


onMounted(async () => {
  isLoading.value = true;

  await Promise.all([
    fetchItem(route.params.id),
    fetchLocations(),
    fetchCategories()
  ]);
  if (item.value.imageUrl) {
    await imgLoader(item.value.imageUrl);
  } else {
    loadedImg.value = null;
  }
  isLoading.value = false;
})

const saveChanges = async () => {
  updateDto.value.id = item.value.id;
  updateDto.value.name = item.value.name;
  updateDto.value.sku = item.value.sku;
  // ImageURL додати обробку якщо завантежено нове зображення imageUrl = null
  updateDto.value.imageUrl = item.value.imageUrl;
  updateDto.value.unitsOfMeasure = item.value.unitsOfMeasure;
  const cat = item.value.category;
  if (typeof cat === 'object' && cat !== null && cat.id) {
    // Якщо це об'єкт і в нього є ID -> це існуюча категорія
    updateDto.value.categoryId = cat.id;
  } else if (typeof cat === 'string' && cat.trim() !== '') {
    // Якщо це текст -> користувач хоче створити нову
    updateDto.value.categoryName = cat.trim();
  }
  updateDto.value.packageType = item.value.packageType;
  updateDto.value.totalQuantity = item.value.totalQuantity;
  updateDto.value.minStock = item.value.minStock;
  const loc = item.value.location;
  if (typeof loc === 'object' && loc !== null && loc.id) {
    // Якщо це об'єкт -> це існуюча локація
    updateDto.value.locationId = loc.id;
  } else if (typeof loc === 'string' && loc.trim() !== '') {
    // Якщо це текст -> створюємо нову локацію
    updateDto.value.locationName = loc.trim();
  }
  updateDto.value.parameters = item.value.parameters;
  updateDto.value.serialNumber = item.value.serialNumber;
  updateDto.value.note = item.value.note;
  updateDto.value.itemType = item.value.itemType;

  try {

    // 4. Пакуємо у FormData (якщо є фотографії) і відправляємо!
    const formData = new FormData();
    formData.append('itemDetails', new Blob([JSON.stringify(toRaw(updateDto.value))], {type: 'application/json'}));
    if (formDataImage.value != null) {
      formData.append('imageFile', formDataImage.value);
    }
    console.log(formData.values());
    await api.put(`/inventory/item/${item.value.id}`, formData, {
      headers: {'Content-Type': 'multipart/form-data'}
    });

    previewUrl.value = null;
    formDataImage.value = null;

    // 4. ВАЖЛИВО: Запитуємо свіжі дані з бази!
    // Це потрібно, щоб отримати нові ID створених категорій/локацій
    // та побачити новий запис в історії логів (changeLog).
    await fetchItem(route.params.id);
    if (item.value.imageUrl) {
      await imgLoader(item.value.imageUrl);
    }

    isEditing.value = false;
  } catch (e) {
    console.error(e);
  }
}

const cancelChanges = async () => {
  // 1. Вимикаємо режим редагування
  isEditing.value = false;

  // 2. Очищаємо тимчасові дані вводу
  previewUrl.value = null;
  formDataImage.value = null;
  newKeyValue.value = "";
  newParamValue.value = "";

  // 3. ВАЖЛИВО: Перезавантажуємо оригінальні дані з бекенду!
  // Це зітре все, що користувач встиг змінити через v-model,
  // і поверне оригінальну категорію, локацію, текст тощо.
  isLoading.value = true;
  await fetchItem(route.params.id);

  // 4. Повертаємо оригінальне фото (якщо юзер встиг завантажити прев'ю, але передумав)
  if (item.value.imageUrl) {
    await imgLoader(item.value.imageUrl);
  } else {
    loadedImg.value = null;
  }
  isLoading.value = false;

}


const formatDateTime = (dateString) => {
  if (!dateString) return 'Невідома дата';

  const date = new Date(dateString);

  // Налаштовуємо формат (українська мова)
  return new Intl.DateTimeFormat('uk-UA', {
    year: 'numeric',   // 2026
    month: '2-digit',     // травня (можна '2-digit' для 05)
    day: 'numeric',    // 2 (або '2-digit' для 02)
    hour: '2-digit',   // 23
    minute: '2-digit', // 48
  }).format(date);
};

const removeParam = (key) => {
  delete item.value.parameters[key];
}
const addParam = () => {
  if (newKeyValue.value.trim() && newParamValue.value.trim()) {
    const key = newKeyValue.value.trim();
    const val = newParamValue.value.trim();

    item.value.parameters[key] = val;

    newParamValue.value = '';
    newKeyValue.value = '';
  }
}

const sortedChangeLog = computed(() => {
  // Захист від порожнього масиву
  if (!changeLog.value || changeLog.value.length === 0) return [];

  // Робимо копію і сортуємо (від найновішого до найстарішого)
  return [...changeLog.value].sort((a, b) => {
    return new Date(b.createdAt) - new Date(a.createdAt);
  });
});

const triggerImgInput = () => {
  // Додаємо захист: викликаємо вікно ТІЛЬКИ в режимі редагування
  if (isEditing.value && fileInputRef.value) {
    fileInputRef.value.click();
  }
};

const handleFileChange = (event) => {
  const file = event.target.files[0];

  if (!file) return;

  // Зберігаємо файл для відправки на сервер
  formDataImage.value = file;

  // ВАЖЛИВО: Очищаємо старе фото, щоб Vue перемкнувся на previewUrl
  loadedImg.value = null;
  item.value.imageUrl = null;

  // Створюємо прев'ю
  const reader = new FileReader();
  reader.onload = (e) => {
    previewUrl.value = e.target.result;
  };
  reader.readAsDataURL(file);
};
</script>
<style scoped src="./style/itempageview.css">
</style>