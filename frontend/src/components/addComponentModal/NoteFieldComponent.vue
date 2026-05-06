<template>
    <div class="input-container">
        <label>Нотатка</label>
        <div class="note-wrapper">
        <textarea
        v-model="formData.note"
        ></textarea>
        </div>
    </div>
</template>
<script setup>
import { useModalStore } from './modalStore';
import { storeToRefs } from 'pinia';

const storeModal = useModalStore()
const {formData} = storeToRefs(storeModal)

const res = () => {
    console.log(formData.value.note)
}
</script>
<style src="./style/notefieldcomponent.css" scoped></style>