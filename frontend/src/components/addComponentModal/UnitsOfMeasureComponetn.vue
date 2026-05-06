<template>
  <div class="input-field-container">
    <label>Одиниці вимірювання</label>
    <div class="input-wrapper">
      <select id="1" v-model="formData.unitsOfMeasure">
        <option selected value="PCS">ШТУК</option>
        <option value="PACK">ПАЧОК</option>
        <option value="KG">КІЛОГРАМ</option>
        <option value="GRAMS">ГРАМ</option>
        <option value="METERS">МЕТРІВ</option>
        <option value="UDEF">НЕВИЗНАЧЕНО</option>
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
<style scoped src="./style/unitsofmeasurecomponent.css"></style>