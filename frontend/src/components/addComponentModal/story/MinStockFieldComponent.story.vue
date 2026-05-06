<script setup>
import MinStackFieldComponent from '../MinStackFieldComponent.vue';
</script>

<template>
  <Story title="Form / Min Stock Field Component">
    
    <Variant title="Default">
      <MinStackFieldComponent />
    </Variant>

    <Variant title="With Error">
      <MinStackFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>