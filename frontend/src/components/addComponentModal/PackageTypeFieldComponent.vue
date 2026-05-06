<template>
    <div class="input-field-container">
        <label>Тип корпусу</label>
        <input class="input-wrapper"
        v-model="formData.packageType"
        type="text"
        placeholder="THT"
        />
    </div>
</template>
<script setup>
import { useModalStore } from './modalStore';
import { storeToRefs } from 'pinia';

const useStore = useModalStore()
const { formData } = storeToRefs(useStore)

</script>
<style src="./style/packagetypefieldcomponent.css" scoped></style>