import { ref, computed } from 'vue'

export function useCombobox(existingDataRef) {
  const searchQuery = ref('')
  const isDropdownOpen = ref(false)

  const filteredItems = computed(() => {
    if (!existingDataRef || !existingDataRef.value) return []
    if (!searchQuery.value) return existingDataRef.value
    
    const lowerQuery = searchQuery.value.toLowerCase()
    return existingDataRef.value.filter(item => 
      item.name.toLowerCase().includes(lowerQuery)
    )
  })

  const isNewItem = computed(() => {
    if (!existingDataRef || !existingDataRef.value) return false
    
    const query = searchQuery.value.trim()
    return query.length > 0 && !existingDataRef.value.some(item => item.name === query)
  })

  const handleInput = () => {
    isDropdownOpen.value = true
  }

  const selectItem = (item) => {
    searchQuery.value = item.name
    isDropdownOpen.value = false
  }

  const handleBlur = () => {
    setTimeout(() => {
      isDropdownOpen.value = false
    }, 200)
  }

  const resetSearch = () => {
    searchQuery.value = ''
    isDropdownOpen.value = false
  }

  return {
    searchQuery,
    isDropdownOpen,
    filteredItems,
    isNewItem,
    handleInput,
    selectItem,
    handleBlur,
    resetSearch
  }
}