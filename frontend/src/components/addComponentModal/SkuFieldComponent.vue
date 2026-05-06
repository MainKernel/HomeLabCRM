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
import {computed} from "vue";

const useStore = useModalStore()
const { formData, isSubmitAtempted} = storeToRefs(useStore)

const isError = computed(() => {
  return isSubmitAtempted.value && !formData.value.name.trim();
});


</script>
<style src="./style/skufieldcomponent.css" scoped></style>