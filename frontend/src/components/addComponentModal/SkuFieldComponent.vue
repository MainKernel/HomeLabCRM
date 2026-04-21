<template>
    <div class="input-field-container">
        <label>SKU</label>
        <input class="input-wrapper"
        v-model="formData.sku"
        type="text"
        placeholder="RES-10K-THT"
        :class="{ 'input-error': isError }"
        />
    </div>
</template>
<script setup>
import { useModalStore } from './modalStore';
import { storeToRefs } from 'pinia';

const useStore = useModalStore()
const { formData, isSubmitAtempted} = storeToRefs(useStore)



</script>
<style scoped>
.input-field-container{
    display: flex;
    flex-direction: column;
}
label{
    padding-left: 5px;
    margin-right: 5px;
    color: whitesmoke;
    font-weight: 900;
}
input{
    margin-top: 5px;
    margin-left: 5px;
    margin-right: 5px;
    color: whitesmoke;
    background-color: var(--color-surface, #1e1e1e);
    border: 2px solid #9c50ff8d;
    min-height: 25px;
    padding-left: 5px;
    border-radius: 5px;
    
}
.input-wrapper{
    margin-top: 5px;
    margin-left: 5px;
}
input:focus{
    outline: none;
    box-shadow: 0 0 5px rgba(0, 255, 65, 0.5), 
              0 0 15px rgba(0, 255, 65, 0.4);
}
.input-error {
    border-color: #EF4444; /* Червона рамка */
    background-color: rgba(239, 68, 68, 0.05); /* Ледь помітний червоний фон */
}

/* Якщо поле з помилкою в фокусі - світимо червоним неоном замість зеленого */
.input-error:focus {
    box-shadow: 0 0 5px rgba(239, 68, 68, 0.5), 0 0 15px rgba(239, 68, 68, 0.4);
}

.error-text {
    color: #EF4444;
    font-size: 0.75rem;
    margin-top: 4px;
    margin-left: 5px;
    display: block; /* Щоб текст завжди був з нового рядка під інпутом */
}
</style>