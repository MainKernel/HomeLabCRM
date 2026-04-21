<template>
    <div class="image-upload-container">
        <input 
            type="file"
            ref="fileInputRef"
            accept="image/*"
            style="display: none;"
            @change="handleFileChange"
        />
        
        <div class="upload-box" @click="triggerFileInput">
            <img v-if="previewUrl" :src="previewUrl" alt="Preview" class="preview-image"/>
            <span v-else>Завантажити зображення</span>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useModalStore } from './modalStore';

const modalStore = useModalStore()

// 1. Обов'язково дістаємо ПРАВИЛЬНУ змінну зі стору. 
// Якщо у вас в modalStore.js вона називається formDataImage, залишаємо так:
const { formDataImage } = storeToRefs(modalStore) 

const fileInputRef = ref(null)

// 2. Локальна змінна для відображення картинки
const previewUrl = ref(null)

const triggerFileInput = () => {
    if(fileInputRef.value) {
        fileInputRef.value.click()
    }
};

const handleFileChange = (event) => {
    console.log("1. Початок завантаження...");
    const file = event.target.files[0];
    
    if(!file) {
        console.log("Файл не обрано (натиснули Скасувати)");
        return;
    }
    
    console.log("2. Файл отримано:", file.name);

    // 3. Зберігаємо файл у Стор
    // Використовуємо вашу змінну formDataImage
    formDataImage.value = file; 
    console.log("3. Файл збережено в Pinia");

    // 4. Створюємо прев'ю
    const reader = new FileReader();
    
    reader.onload = (e) => {
        console.log("4. FileReader успішно прочитав файл!");
        previewUrl.value = e.target.result; 
    };

    // Обробка помилок при читанні файлу
    reader.onerror = (error) => {
        console.error("Помилка FileReader: ", error);
    };

    reader.readAsDataURL(file);
};
</script>

<style scoped>

.upload-box {
  width: 200px;
  height: 200px;
  border: 2px dashed #9D50FF; 
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  background-color: var(--color-surface, #1e1e1e);
  color: white;
  text-align: center;
  overflow: hidden; 
}

.upload-box:hover {
  background-color: rgba(157, 80, 255, 0.1);
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: fill; 
}
</style>