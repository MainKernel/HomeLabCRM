import {defineStore} from 'pinia'
import {ref} from 'vue'
import api from '../../api/axiosInstance'

export const useModalStore
    = defineStore('createModalStore', () => {

    // Створення об'єкту для модального вікна
    const formData = ref({
        sku: "",
        name: "",
        workspaceId: Number(localStorage.getItem("workspaceId")),
        unitsOfMeasure: 'PCS',
        category: "",
        packageType: "",
        stock: 0,
        minStock: 0,
        location: "",
        parameters: {},
        serialNumber: "",
        note: "",
        type: 'REGULAR',
        imageFiele: null,
        documents: []
    })

    const newParamValue = ref('')
    const newKeyValue = ref('')
    const categories = ref([])
    const locations = ref([])
    const formDataImage = ref()
    const selectedFiles = ref([])
    const isSubmitAtempted = ref(false)

    const fetchCategories = async () => {
        try {
            const response = await api.get(`/inventory/categories`)
            categories.value = response.data
        } catch (err) {
            console.error(err)
        }
    }
    const fetchLocations = async () => {
        try {
            const response = await api.get(`/inventory/locations`)
            locations.value = response.data
        } catch (err) {
            console.error(err)
        }
    }
    const addParameter = () => {
        if (newParamValue.value.trim() && newKeyValue.value.trim()) {
            const key = newKeyValue.value.trim()
            const val = newParamValue.value.trim()

            formData.value.parameters[key] = val

            newParamValue.value = ''
            newKeyValue.value = ''
        }
    }

    const removeParam = (keyToRemove) => {
        delete formData.value.parameters[keyToRemove]
    }

    function $reset() {
        formData.value = {
            sku: "",
            name: "", workspaceId: Number(localStorage.getItem("workspaceId")) || 0, unitsOfMeasure: "",
            category: "", packageType: "", stock: 0, minStock: 0, location: "", parameters: {},
            serialNumber: "", note: "", type: "", imageFiele: null, documents: []
        }
        formDataImage.value = null
        selectedFiles.value = []
        newParamValue.value = ''
        newKeyValue.value = ''
        isSubmitAtempted.value = false
    }

    async function submitForm() {
        isSubmitAtempted.value = true;
        const payload = new FormData();

        const itemDetails = {
            sku: formData.value.sku,
            name: formData.value.name,
            workspaceId: formData.value.workspaceId,
            unitsOfMeasure: formData.value.unitsOfMeasure,
            category: formData.value.category,
            packageType: formData.value.packageType,
            stock: formData.value.stock,
            minStock: formData.value.minStock,
            location: formData.value.location,
            parameters: formData.value.parameters,
            serialNumber: formData.value.serialNumber,
            note: formData.value.note,
            type: formData.value.type
        };
        const jsonBlob = new Blob([JSON.stringify(itemDetails)], {
            type: 'application/json'
        });

        payload.append('itemDetails', jsonBlob);

        if (formDataImage.value) {
            payload.append('imageFile', formDataImage.value);
        }
        if (selectedFiles.value.length > 0) {
            selectedFiles.value.forEach((file) => {
                payload.append('documents', file);
            });
        }
        if (!formData.value.name || !formData.value.sku || !formData.value.unitsOfMeasure
            || !formData.value.category || !formData.value.packageType || !formData.value.stock
            || !formData.value.location || !formData.value.type
            && ((formData.value.type === "UNIQUE") && !formData.value.serialNumber)
        ) {
            console.warn("Not all required field is filled");
            return false;
        } else {
            try {
                const response = await api.post(`/inventory`, payload, {
                    headers: {'Content-Type': 'multipart/form-data'}
                });
                console.log("Успіх", response.data);
                return true;
            } catch (error) {
                console.error("Помилка", error);
                return false;
            }
        }
    }


    return {
        formData,
        newParamValue,
        newKeyValue,
        categories,
        locations,
        formDataImage,
        selectedFiles,
        isSubmitAtempted,
        addParameter,
        removeParam,
        fetchCategories,
        fetchLocations,
        $reset,
        submitForm
    }
})