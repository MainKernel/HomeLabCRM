<script setup>
import SkuFieldComponent from '../SkuFieldComponent.vue';
</script>

<template>
  <Story title="Form / SKU Field Component">
    
    <Variant title="Default">
      <SkuFieldComponent />
    </Variant>

    <Variant title="With Error">
      <SkuFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>