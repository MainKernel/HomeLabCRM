import api from '@/api/axiosInstance.js';
import {ref} from "vue";
import {defineStore} from "pinia";


export const useSettingsStore
    = defineStore("useSettingsStore", () => {

    const myWorkspaces = ref([]);
    const sharedToMeWorkspaces = ref([]);
    const isLoading = ref(true);


    const loadMyWorkspaces = async () => {
        try {
            const response = await api.get(`/workspaces/owner`);
            myWorkspaces.value = response.data;
        } catch (error) {
            console.error(error);
        }
    };
    const loadSharedWorkspaces = async () => {
        try {
            const response = await api.get(`/workspaces/member`);
            sharedToMeWorkspaces.value = response.data;
        } catch (error) {
            console.error(error);
        }
    };
    const loadAllWorkspaces = async () => {
        isLoading.value = true;
        try {
            await Promise.all([
                loadMyWorkspaces(),
                loadSharedWorkspaces()
            ]);
        } finally {
            isLoading.value = false;
        }
    };

    return {
        myWorkspaces,
        sharedToMeWorkspaces,
        loadMyWorkspaces,
        loadSharedWorkspaces,
        loadAllWorkspaces,
    }
})