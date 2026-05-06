<template>
    <div class="input-field-container">
        <label>Назва</label>
        <input class="input-wrapper"
        v-model="formData.name"
        type="text"
        placeholder="TinySA Ultra ZS-407"
        :class="{ 'input-error': isError }"
        />
    </div>
</template>
<script setup>
import { computed } from 'vue';
import { storeToRefs } from 'pinia';
import { useModalStore } from './modalStore';

const useStore = useModalStore()
const { formData, isSubmitAtempted } = storeToRefs(useStore)

const isError = computed(() => {
    return isSubmitAtempted.value && !formData.value.name.trim();
});

</script>
<style src="./style/namefieldcomponent.css" scoped></style>