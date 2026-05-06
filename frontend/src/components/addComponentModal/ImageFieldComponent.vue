<template>
  <div class="image-upload-container">
    <input
        ref="fileInputRef"
        accept="image/*"
        style="display: none;"
        type="file"
        @change="handleFileChange"
    />

    <div class="upload-box" @click="triggerFileInput">
      <img v-if="previewUrl" :src="previewUrl" alt="Preview" class="preview-image"/>
      <span v-else>Завантажити зображення</span>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {storeToRefs} from 'pinia';
import {useModalStore} from './modalStore';

const modalStore = useModalStore()

// 1. Обов'язково дістаємо ПРАВИЛЬНУ змінну зі стору. 
// Якщо у вас в modalStore.js вона називається formDataImage, залишаємо так:
const {formDataImage} = storeToRefs(modalStore)

const fileInputRef = ref(null)

// 2. Локальна змінна для відображення картинки
const previewUrl = ref(null)

const triggerFileInput = () => {
  if (fileInputRef.value) {
    fileInputRef.value.click()
  }
};

const handleFileChange = (event) => {
  const file = event.target.files[0];

  if (!file) {
    return;
  }
  // 3. Зберігаємо файл у Стор
  // Використовуємо вашу змінну formDataImage
  formDataImage.value = file;

  // 4. Створюємо прев'ю
  const reader = new FileReader();

  reader.onload = (e) => {
    previewUrl.value = e.target.result;
  };

  // Обробка помилок при читанні файлу
  reader.onerror = (error) => {
    console.log(error);
  };

  reader.readAsDataURL(file);
};
</script>

<style scoped src="./style/imagefildcomponent.css"></style>