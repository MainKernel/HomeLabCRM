import { ref } from 'vue'

export function useFileUpload(options = { maxSize: 10, allowedTypes: [] }) {
  const fileInput = ref(null)
  const uploadedFiles = ref([]) // Масив об'єктів { file: File, id: String, type: String }

  const triggerUpload = () => fileInput.value?.click()

  const handleFileChange = (event) => {
    const files = Array.from(event.target.files)
    
    files.forEach(file => {
      const extension = file.name.split('.').pop().toLowerCase()
      
      // Валідація за типом (якщо вказано)
      if (options.allowedTypes.length && !options.allowedTypes.includes(extension)) {
        alert(`Файл .${extension} не дозволений`)
        return
      }

      // Валідація за розміром (напр. 10MB)
      if (file.size > options.maxSize * 1024 * 1024) {
        alert(`Файл занадто великий (макс ${options.maxSize}MB)`)
        return
      }

      uploadedFiles.value.push({
        id: Math.random().toString(36).substr(2, 9),
        file: file,
        name: file.name,
        extension: extension,
        size: (file.size / 1024).toFixed(1) + ' KB'
      })
    })

    if (fileInput.value) fileInput.value.value = ''
  }

  const removeFile = (index) => uploadedFiles.value.splice(index, 1)

  return { fileInput, uploadedFiles, triggerUpload, handleFileChange, removeFile }
}