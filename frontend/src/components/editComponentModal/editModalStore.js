import { defineStore  } from "pinia";
import { ref } from "vue";
import api from '../../api/axiosInstance'

export const useExportModal = defineStore('createEditModalStore', () => {
    const componetnObject = ref(null)


    const fetchComponent = async (id) => {
        try{
            const response = await api.get('/inventory/api/' + id)
            componetnObject.value = response.data
        } catch (err){
            console.error(err)
        }
    }

    // TODO: Type, UnitsOfMeasuer, Location box hight fix 

    async function submitForm(){

    }
})