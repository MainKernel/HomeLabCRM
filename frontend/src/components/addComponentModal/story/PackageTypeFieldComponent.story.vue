<script setup>
import PackageTypeFieldComponent from '../PackageTypeFieldComponent.vue';
</script>

<template>
  <Story title="Form / Package Type Field Component">
    
    <Variant title="Default">
      <PackageTypeFieldComponent />
    </Variant>

    <Variant title="With Error">
      <PackageTypeFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>