<script setup>
import MinStickFieldComponent from '../MinStickFieldComponent.vue';
</script>

<template>
  <Story title="Form / Min Stock Field Component">
    
    <Variant title="Default">
      <MinStickFieldComponent />
    </Variant>

    <Variant title="With Error">
      <MinStickFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>