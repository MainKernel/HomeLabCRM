<script setup>
import FileUploadFieldComponent from '../FileUploadFieldComponent.vue';
</script>

<template>
  <Story title="Form / File Upload Field Component">
    
    <Variant title="Default">
      <FileUploadFieldComponent />
    </Variant>

    <Variant title="With Error">
      <FileUploadFieldComponent :hasError="true" errorMessage="Назва обов'язкова" />
    </Variant>

  </Story>
</template>