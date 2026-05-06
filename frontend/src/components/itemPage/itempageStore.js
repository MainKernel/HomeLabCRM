import {defineStore} from 'pinia';
import api from "@/api/axiosInstance.js";
import {ref} from "vue";


export const useItemPageStore = defineStore(
    'useItemPageStore', () => {

        const isEditing = ref(false);
        const item = ref({
            id: null,
            name: null,
            sku: null,
            imageUrl: null,
            workspaceId: Number(localStorage.getItem("workspaceId")),
            unitsOfMeasure: null,
            category: null,
            packageType: null,
            totalQuantity: null,
            minStock: null,
            location: null,
            parameters: {},
            serialNumber: null,
            note: null,
            itemType: null,
            documents: null
        });
        const updateDto = ref({
            id: null,
            name: null,
            sku: null,
            imageUrl: null,
            workspaceId: Number(localStorage.getItem("workspaceId")),
            unitsOfMeasure: null,
            categoryId: null,
            categoryName: null,
            packageType: null,
            totalQuantity: null,
            minStock: null,
            locationId: null,
            locationName: null,
            parameters: {},
            serialNumber: null,
            note: null,
            itemType: null,
            documents: null
        });
        const changeLog = ref([]);

        const locations = ref([])
        const category = ref([]);
        const loadedImg = ref(null);

        const fetchItem = async (id) => {
            try {
                const res = await api.get(`/inventory/item/${id}`);
                item.value = {
                    id: res.data.id,
                    name: res.data.name,
                    sku: res.data.sku,
                    imageUrl: res.data.imageUrl,
                    unitsOfMeasure: res.data.unitsOfMeasure,
                    category: res.data.category,
                    packageType: res.data.packageType,
                    totalQuantity: res.data.totalQuantity,
                    minStock: res.data.minStock,
                    location: res.data.location,
                    parameters: res.data.parameters,
                    serialNumber: res.data.serialNumber,
                    note: res.data.note,
                    itemType: res.data.type,
                    documents: res.data.documents
                }
                changeLog.value = res.data.historyLogs;
            } catch (error) {
                console.log(error);
            }
        }
        const fetchLocations = async () => {
            try {
                const res = await api.get(`/inventory/locations`);
                locations.value = res.data;
            } catch (error) {
                console.log(error);
            }
        }
        const fetchCategories = async () => {
            try {
                const res = await api.get(`/inventory/categories`);
                category.value = res.data;
            } catch (error) {
                console.log(error);
            }
        }
        const imgLoader = async (imgUrl) => {
            if (imgUrl != null) {
                try {
                    const res = await api.get(`/inventory/resources/preview/${imgUrl}`, {
                        responseType: 'blob'
                    });
                    loadedImg.value = URL.createObjectURL(res.data);
                } catch (error) {
                    console.log(error);
                }
            }
        }

        return {
            isEditing,
            item,
            changeLog,
            locations,
            category,
            loadedImg,
            updateDto,
            fetchItem,
            fetchLocations,
            fetchCategories,
            imgLoader,
        }

    })
