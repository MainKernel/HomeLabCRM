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
<style scoped>
.input-container{
    display: flex;
    flex-direction: column;
}
label{
    padding-left: 10px;
    margin-right: 5px;
    color: whitesmoke;
    font-weight: 900;
}
textarea{
    margin-top: 5px;
    margin-bottom: 5px;
    margin-left: 5px;
    margin-right: 5px;
    color: whitesmoke;
    background-color: var(--color-surface, #1e1e1e);
    border: 2px solid #9c50ff8d;
    min-height: 25px;
    padding-left: 5px;
    border-radius: 5px;
    height: 100px;
    width: 100%;
    resize: none;
    box-sizing: border-box;
    
}
.note-wrapper{
   margin: 10px;
   padding-right: 20px;
   border: 2px dashed black;
}
textarea:focus{
    outline: none;
    box-shadow: 0 0 5px rgba(0, 255, 65, 0.5), 
              0 0 15px rgba(0, 255, 65, 0.4);
}
</style>