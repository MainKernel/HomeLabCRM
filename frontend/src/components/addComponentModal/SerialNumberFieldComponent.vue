<template>
    <div class="input-field-container">
        <label>Серійний номер</label>
        <input v-model="formData.serialNumber" type="text" placeholder="SN00000">
    </div>
</template>
<script setup>
import { storeToRefs } from 'pinia';
import { useModalStore } from './modalStore';

const modelStore = useModalStore()
const {formData} = storeToRefs(modelStore)

</script>
<style src="./style/serialnumberfieldcomponent.css" scoped></style>