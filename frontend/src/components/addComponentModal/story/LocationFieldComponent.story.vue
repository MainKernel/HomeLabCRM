<script setup>
import LocationFieldComponent from '../LocationFieldComponent.vue'
</script>

<template>
  <Story title="Form / Location Field Component">
    
    <Variant title="Default">
      <LocationFieldComponent />
    </Variant>

    <Variant title="With Error">
      <LocationFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>