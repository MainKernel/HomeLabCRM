<template>
    <div class="input-field-container">
        <label>Кількість</label>
        <input
        v-model="formData.stock"
        type="number"
        placeholder="Кількість"/>
    </div>
</template>
<script setup>
import { useModalStore } from './modalStore';
import { storeToRefs } from 'pinia';

const useStore = useModalStore ()
const {formData} = storeToRefs(useStore)

</script>
<style src="./style/stockfieldcomponent.css" scoped></style>