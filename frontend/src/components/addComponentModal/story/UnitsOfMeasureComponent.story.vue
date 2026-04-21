<script setup>
import UnitsOfMeasureComponetn from '../UnitsOfMeasureComponetn.vue';
</script>

<template>
  <Story title="Form / Units Field Component">
    
    <Variant title="Default">
      <UnitsOfMeasureComponetn />
    </Variant>

    <Variant title="With Error">
      <UnitsOfMeasureComponetn :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>