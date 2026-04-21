<script setup>
import NameFieldComponent from '../NameFieldComponent.vue';
</script>

<template>
  <Story title="Form / Name Field Component">
    
    <Variant title="Default">
      <NameFieldComponent />
    </Variant>

    <Variant title="With Error">
      <NameFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>