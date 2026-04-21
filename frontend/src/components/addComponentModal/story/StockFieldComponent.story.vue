<script setup>
import StockFieldComponent from '../StockFieldComponent.vue';
</script>

<template>
  <Story title="Form / Stock Field Component">
    
    <Variant title="Default">
      <StockFieldComponent />
    </Variant>

    <Variant title="With Error">
      <StockFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>