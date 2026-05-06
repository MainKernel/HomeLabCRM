import {defineStore} from 'pinia'
import {computed, ref} from 'vue'
import api from '@/api/axiosInstance' // Наш налаштований Axios

export const useAppStore

    = defineStore('app', () => {
    // Читаємо при ініціалізації додатку, або ставимо 1 (Домашня майстерня)
    const activeWorkspaceId = ref(localStorage.getItem('workspaceId') || '1')

    const setWorkspace = (id) => {
        activeWorkspaceId.value = id
        localStorage.setItem('workspaceId', id)
        window.location.reload()
    }

    return {activeWorkspaceId, setWorkspace}
})

export const useInventoryStore = defineStore('inventory', () => {

    const locations = ref([])
    const activeLocationId = ref(null)
    const items = ref([])
    const location = ref([])
    const category = ref([])
    const itemsPerPage = ref(Number(localStorage.getItem('itemsPerPage')) || 10)

    const totalPages = ref(0)
    const currentPage = ref(0)
    const searchQuery = ref('')


    const isLoading = ref(false)
    const error = ref(null)

    // ==============================
    // 2. ГЕТТЕРИ (Getters)
    // ==============================
    // Отримати об'єкт поточної активної локації
    const activeLocation = computed(() => {
        return locations.value.find(loc => loc.id === activeLocationId.value) || null
    })

    // Рахуємо загальну кількість унікальних позицій на поточному складі
    const totalUniqueItems = computed(() => items.value.length)

    // ==============================
    // 3. ДІЇ (Actions)
    // ==============================

    const fetchItemsForLocation = async (locationId, page = 0) => {
        isLoading.value = true
        error.value = null
        try {
            const response = await api.get(`/inventory`, {
                params: {
                    page: page,
                    size: itemsPerPage.value,
                    search: searchQuery.value || undefined
                },
            })

            // Spring Data JPA ховає масив у поле 'content'
            items.value = response.data.content

            // Зберігаємо метадані для кнопок пагінації на UI
            totalPages.value = response.data.page.totalPages
            currentPage.value = response.data.page.number

        } catch (err) {
            error.value = err.response?.data?.message || 'Помилка завантаження компонентів'
            console.error(err)
        } finally {
            isLoading.value = false
        }
    }

    const fetchLocations = async () => {
        isLoading.value = true
        error.value = null
        try {
            const response = await api.get(`/inventory/locations`)

            location.value = response.data
        } catch (err) {
            error.value = err.response?.data?.message || 'Помилка завантаження локацій'
            console.error(err)
        } finally {
            isLoading.value = false
        }
    }

    const fetchCategories = async () => {
        isLoading.value = true
        error.value = null
        try {
            const response = await api.get(`/inventory/categories`)
            category.value = response.data

        } catch (err) {
            error.value = err.response?.data?.message || 'Помилка завантаження категорій'
            console.error(err)
        } finally {
            isLoading.value = false
        }
    }

    // ==============================
    // ЕКСПОРТ (Щоб зробити доступним у Vue компонентах)
    // ==============================


    return {
        locations,
        activeLocationId,
        activeLocation,
        items,
        isLoading,
        error,
        totalUniqueItems,
        fetchItemsForLocation,
        fetchCategories,
        fetchLocations,
        totalPages,
        currentPage,
        itemsPerPage,
        searchQuery,
        location,
        category
    }
})