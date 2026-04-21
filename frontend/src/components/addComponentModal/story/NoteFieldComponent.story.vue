<script setup>
import NoteFieldComponent from '../NoteFieldComponent.vue'
</script>

<template>
  <Story title="Form / Note Field Component">
    
    <Variant title="Default">
      <NoteFieldComponent />
    </Variant>

    <Variant title="With Error">
      <NoteFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>