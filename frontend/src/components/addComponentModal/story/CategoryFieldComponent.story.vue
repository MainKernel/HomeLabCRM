<script setup>
import CategoryFieldComponent from '../CategoryFieldComponent.vue'
</script>

<template>
  <Story title="Form / Category Field Component">
    
    <Variant title="Default">
      <CategoryFieldComponent />
    </Variant>

    <Variant title="With Error">
      <CategoryFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>