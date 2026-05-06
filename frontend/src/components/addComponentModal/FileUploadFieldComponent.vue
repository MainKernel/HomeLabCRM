<template>
    <div class="multi-upload-container">
        <label>Документація</label>
        <div class="documents-wrapper">
            <input type="file"
            ref="fileInputRef"
            multiple
            style="display: none;"
            @change="handleFilesAdd"
            />
            <div class="upload-zone"
            @click="triggerInput">
            <span class="trigger">+ Додати файл (PDF, TXT, IMG, OBJ...)</span>
            </div>
        <ul v-if="selectedFiles.length > 0" class="file-list">
            <li v-for="(file, index) in selectedFiles" :key="index" class="file-item">
                
                <span :class="['file-badge', getBadgeColorClass(getExtension(file.name))]">
                    {{ getExtension(file.name) }}
                </span>
                
                <div class="file-text">
                    <span class="file-name" :title="file.name">{{ file.name }}</span>
                    <span class="file-size">{{ (file.size/1024).toFixed(1) }} KB</span>
                </div>
                
                <button class="del-btn" @click.prevent="removeFile(index)">✕</button>
                
            </li>
        </ul>
        </div>
    </div>
</template>
<script setup>
import { ref } from 'vue';
import { useModalStore } from './modalStore';
import { storeToRefs } from 'pinia';

const useStore = useModalStore();
const {selectedFiles } = storeToRefs(useStore);
const fileInputRef = ref(null);

const triggerInput = () => {
    if(fileInputRef) fileInputRef.value.click();
};

const getExtension = (filename) => {
    // Перевіряємо, чи взагалі є крапка у назві (іноді файли бувають без розширення)
    if (!filename || !filename.includes('.')) return 'FILE';
    
    // Розбиваємо рядок по крапці і беремо останній елемент. 
    // toUpperCase() робить його великими літерами (pdf -> PDF)
    return filename.split('.').pop().toUpperCase();
}

const handleFilesAdd = (event) => {
    const incomingFiles = Array.from(event.target.files);
    if(incomingFiles.length === 0) return;

    selectedFiles.value = [...selectedFiles.value, ...incomingFiles];
    event.target.value = '';

};

const removeFile = (index) =>{
    selectedFiles.value.splice(index, 1);
}

// ... ваші попередні імпорти та функції

const getBadgeColorClass = (extension) => {
    // Словник розширень та їхніх класів
    const colorMap = {
        'PDF': 'badge-red',
        'TXT': 'badge-gray',
        'JSON': 'badge-yellow',
        'XML': 'badge-yellow',
        'JPG': 'badge-blue',
        'JPEG': 'badge-blue',
        'PNG': 'badge-blue',
        'SVG': 'badge-blue',
        'OBJ': 'badge-orange',
        'STL': 'badge-orange',
        'ZIP': 'badge-red',
        'RAR': 'badge-red'
    };

    // Якщо розширення є в словнику - повертаємо його клас. 
    // Якщо немає - повертаємо базовий фіолетовий (badge-default)
    return colorMap[extension] || 'badge-default';
};
</script>
<style src="./style/fieluploadfieldcomponent.css" scoped></style>