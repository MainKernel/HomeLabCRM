<script setup>
import ParametersFieldComponent from '../ParametersFieldComponent.vue'
</script>

<template>
  <Story title="Form / Parameters Field Component">
    
    <Variant title="Default">
      <ParametersFieldComponent />
    </Variant>

    <Variant title="With Error">
      <ParametersFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>