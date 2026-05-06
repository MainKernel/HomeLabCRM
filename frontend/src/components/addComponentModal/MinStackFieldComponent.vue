<template>
    <div class="input-field-container">
        <label>Мін. кількість (Alert)</label>
        <input
        v-model="formData.minStock"
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
<style src="./style/minstackfieldcomponent.css" scoped></style>