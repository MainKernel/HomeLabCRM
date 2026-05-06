<template>
  <div class="input-field-container">
    <label>Тип</label>
    <div class="input-wrapper">
      <select id="2" v-model="formData.type">
        <option value="UNIQUE">УНІКАЛЬНИЙ</option>
        <option value="REGULAR">ЗВИЧАЙНИЙ</option>
      </select>
    </div>
  </div>
</template>
<script setup>
import {useModalStore} from './modalStore';
import {storeToRefs} from 'pinia';

const useStore = useModalStore()
const {formData} = storeToRefs(useStore)

</script>
<style scoped src="./style/typefieldcomponent.css"></style>