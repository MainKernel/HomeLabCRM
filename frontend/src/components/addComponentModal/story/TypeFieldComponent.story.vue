<script setup>
import TypeFieldComponent from '../TypeFieldComponent.vue';
</script>

<template>
  <Story title="Form / Type Field Component">
    
    <Variant title="Default">
      <TypeFieldComponent />
    </Variant>

    <Variant title="With Error">
      <TypeFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>