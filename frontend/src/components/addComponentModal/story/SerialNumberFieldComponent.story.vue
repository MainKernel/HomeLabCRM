<script setup>
import SerialNumberFieldComponent from '../SerialNumberFieldComponent.vue'
</script>

<template>
  <Story title="Form / Serial Number Field Component">
    
    <Variant title="Default">
      <SerialNumberFieldComponent />
    </Variant>

    <Variant title="With Error">
      <SerialNumberFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>