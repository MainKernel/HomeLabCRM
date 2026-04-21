// src/modules/inventory/composables/useImageUpload.js
import { ref } from 'vue'

export function useImageUpload() {
  const fileInput = ref(null)
  const imageFile = ref(null)
  const previewUrl = ref(null)

  const triggerUpload = () => {
    if (fileInput.value) {
      fileInput.value.click()
    }
  }

  const handleFileChange = (event) => {
    const file = event.target.files[0]
    if (!file) return
    
    if (!file.type.startsWith('image/')) {
      alert('Будь ласка, виберіть зображення!')
      return
    }

    imageFile.value = file
    if (previewUrl.value) URL.revokeObjectURL(previewUrl.value)
    previewUrl.value = URL.createObjectURL(file)
  }

  const resetImage = () => {
    imageFile.value = null
    previewUrl.value = null
    if (fileInput.value) {
      fileInput.value.value = '' // Очищаємо HTML інпут
    }
  }

  return {
    fileInput,
    imageFile,
    previewUrl,
    triggerUpload,
    handleFileChange,
    resetImage
  }
}