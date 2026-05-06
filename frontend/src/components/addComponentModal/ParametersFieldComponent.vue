<template>
  <div class="input-group full-width">
  <label style="color: whitesmoke; font-weight: 800;">Додаткові параметри</label>
  <div class="field-holder">
  <div class="add-parameter-box" style="display: flex; gap: 10px; margin-bottom: 10px;">
    <div class="input-wrapper">
    <input v-model="newKeyValue" placeholder="Назва (напр. 'Опір')" ref="keyInputRef"/>
    </div>
    <div class="input-wrapper">
    <input v-model="newParamValue" placeholder="Значення (напр. '10 кОм')" @keyup.enter="handleAddAndFocus"/>
    </div>
    <button class="add-btn" @click.prevent="handleAddAndFocus" type="button">Додати</button>
  </div>

  <div class="parameters-list" v-if="Object.keys(formData.parameters).length > 0">
    <div class="parameter-card"
      v-for="(val, key) in formData.parameters" 
      :key="key">
      <div class="parameter-holder">
        <strong class="parameter-key">{{ key }}:</strong>
        <div class="parameter-value">{{ val }}</div>
        </div>
      <button class="del-btn" @click.prevent="removeParam(key)" >✕</button>
    </div>
  </div>
  </div>
</div>
</template>
<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useModalStore } from './modalStore';
const modalStore = useModalStore()
const { formData, newParamValue, newKeyValue } = storeToRefs(modalStore)
const { removeParam,addParameter } = modalStore

const keyInputRef = ref(null)

const handleAddAndFocus = () => {
  addParameter()

  if (keyInputRef.value) {
    keyInputRef.value.focus()
  }
}

</script>
<style src="./style/parametersfieldcomponent.css" scoped></style>