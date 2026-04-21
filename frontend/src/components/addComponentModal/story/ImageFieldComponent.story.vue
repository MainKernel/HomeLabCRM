<script setup>
import ImageFieldComponent from '../ImageFieldComponent.vue'
</script>

<template>
  <Story title="Form / Image Field Component">
    
    <Variant title="Default">
      <ImageFieldComponent />
    </Variant>

    <Variant title="With Error">
      <ImageFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>