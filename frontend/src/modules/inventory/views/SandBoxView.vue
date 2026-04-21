<template>
  <div class="sandbox-layout">
    <aside class="controls-panel">
      <h2>Конфігуратор</h2>
      <div class="control-group">
        <label>Обрати компонент</label>
        <select v-model="activeComponent">
          <option value="CategoryField">Category Field</option>
          <option value="ImageField">Image Field</option>
          <option value="ParametersField">Parameters Field</option>
          <option value="FileField">File Upload Field</option>
        </select>
      </div>
      
      <div class="control-group">
        <label>Режим помилки</label>
        <input type="checkbox" v-model="componentProps.hasError" />
      </div>
    </aside>

    <main class="preview-area">
      <div class="component-container">
        <component 
          :is="components[activeComponent]" 
          v-bind="componentProps"
          v-model="testData"
        />
      </div>

      <pre class="state-debugger">{{ testData }}</pre>
    </main>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import CategoryField from '../../../components/addComponentModal/CategoryFieldComponent.vue';
import ImageField from '../../../components/addComponentModal/ImageFieldComponent.vue';
import FileField from '../../../components/addComponentModal/FileUploadFieldComponent.vue';

const components = { CategoryField, ImageField, FileField };
const activeComponent = ref('CategoryField');

const componentProps = reactive({
  hasError: false,
  label: 'Тестове поле'
});

const testData = ref(null);
</script>
