<template>
  <Teleport to="body">
    <Transition name="modal-fade">

      <div v-if="isOpen" class="modal-backdrop" @click.self="closeModal">

        <div class="modal-card">

          <header class="modal-header">
            <h3>Додати компонент</h3>
            <button class="close-btn" @click="closeModal">✕</button>
          </header>

          <main class="modal-body">
            <div class="fields-container">
              <div class="image-container">
                <ImageFieldComponent/>
              </div>
              <div class="name-container">
                <NameFieldComponent/>
              </div>
              <div class="sku-container">
                <SkuFieldComponent/>
              </div>
              <div class="type-container">
                <TypeFieldComponent/>
              </div>
              <div class="category-container">
                <CategoryFieldComponent/>
              </div>
              <div class="stock-container">
                <StockFieldComponent/>
              </div>
              <div class="min-stock-container">
                <MinStackFieldComponent/>
              </div>
              <div class="units-container">
                <UnitsOfMeasureComponent/>
              </div>

              <div class="package-container">
                <PakageFieldComponent/>
              </div>
              <div class="location">
                <LocationFieldComponent/>
              </div>
              <div class="serial-container">
                <SerialFieldComponent/>
              </div>
              <div class="parameters-container">
                <ParametersFieldComponent/>
              </div>
              <div class="file-container">
                <FileUploadFieldComponent/>
              </div>
              <div class="note-container">
                <NoteFieldComponent/>
              </div>
            </div>
          </main>

          <footer class="modal-footer">
            <slot name="footer">
              <button class="btn-cancel" @click="closeModal">Скасувати</button>
              <button class="btn-submit" @click="submitForm">Зберігти</button>
            </slot>
          </footer>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>
<script setup>
import ImageFieldComponent from './addComponentModal/ImageFieldComponent.vue';
import NameFieldComponent from './addComponentModal/NameFieldComponent.vue';
import SkuFieldComponent from './addComponentModal/SkuFieldComponent.vue';
import TypeFieldComponent from './addComponentModal/TypeFieldComponent.vue';
import StockFieldComponent from './addComponentModal/StockFieldComponent.vue';
import MinStackFieldComponent from './addComponentModal/MinStackFieldComponent.vue';
import UnitsOfMeasureComponent from './addComponentModal/UnitsOfMeasureComponetn.vue'
import CategoryFieldComponent from './addComponentModal/CategoryFieldComponent.vue';
import PakageFieldComponent from './addComponentModal/PackageTypeFieldComponent.vue'
import LocationFieldComponent from './addComponentModal/LocationFieldComponent.vue';
import SerialFieldComponent from './addComponentModal/SerialNumberFieldComponent.vue';
import ParametersFieldComponent from './addComponentModal/ParametersFieldComponent.vue';
import FileUploadFieldComponent from './addComponentModal/FileUploadFieldComponent.vue';
import NoteFieldComponent from './addComponentModal/NoteFieldComponent.vue';

const props = defineProps({isOpen: Boolean})
const emit = defineEmits(['close', 'save'])

const closeModal = () => emit('close')

const submitForm = () => {
  emit('save')
}
</script>

<style scoped>
.btn-cancel {
  background-color: transparent;
  border: 2px solid red;
  border-radius: 5px;
  padding: 5px;
  color: whitesmoke;
  width: 83px;
  height: 32px;
}

.btn-submit {
  background-color: #9c50ff;
  border-radius: 5px;
  border: 3px solid green;
  color: whitesmoke;
  padding: 5px;
  width: 83px;
  height: 32px;
}

.fields-container {
  display: grid;
  gap: 5px;
  grid-template-columns: repeat(5, 1fr);
}

.image-container {
  grid-row-start: span 5;
}

.file-container, .parameters-container, .note-container {
  grid-column-start: span 5;
}


/* Плавна анімація появи/зникнення */
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

/* Підкладка на весь екран */
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.75); /* Темний напівпрозорий фон */
  backdrop-filter: blur(4px); /* Ефект розмиття фону */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999; /* Поверх усього */
}

/* Сама модалка в стилі Кіберпанк */
.modal-card {
  background-color: #1e1e1e;
  border: 1px solid #9D50FF;
  box-shadow: 0 0 20px rgba(157, 80, 255, 0.2);
  border-radius: 8px;
  width: 80%;
  max-height: 90vh; /* Не більше висоти екрану */
  display: flex;
  flex-direction: column;
}

.modal-header {
  padding: 15px 10px;
  border-bottom: 1px solid #333;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: whitesmoke;
}

h3 {
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  color: #EF4444;
  font-size: 1.2rem;
  cursor: pointer;
}

.modal-body {
  padding: 20px;
  overflow-y: auto; /* Дозволяє скролити саму форму, якщо вона довга */
}

.modal-footer {
  padding: 15px 20px;
  border-top: 1px solid #333;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>