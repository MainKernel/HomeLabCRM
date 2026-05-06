<template>
  <Teleport to="body">
    <!-- Оверлей. @click.self означає, що клік спрацює ТІЛЬКИ по сірому фону, а не по самій білій картці -->
    <div v-if="confirmStore.isOpen" class="modal-overlay" @click.self="confirmStore.cancel">

      <!-- Сама картка -->
      <div class="modal-content">
        <h3 class="modal-title">{{ confirmStore.title }}</h3>
        <p class="modal-text">{{ confirmStore.message }}</p>

        <div class="modal-actions">
          <button class="btn-cancel" @click="confirmStore.cancel">Скасувати</button>
          <button class="btn-confirm" @click="confirmStore.confirm">Так, я впевнений</button>
        </div>
      </div>

    </div>
  </Teleport>
</template>

<script setup>
import {useConfirmStore} from '@/components/stores/confirmStore'; // Вкажіть ваш шлях
const confirmStore = useConfirmStore();
</script>

<style scoped>
/* 1. Темний фон на весь екран */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999; /* Поверх усього */
}

/* 2. Біла картка по центру */
.modal-content {
  background: #1e1e1e; /* Темна тема, як у вас */
  border: 1px solid #444;
  padding: 24px;
  border-radius: 8px;
  min-width: 300px;
  max-width: 400px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5);
  color: #fff;
}

.modal-title {
  margin-top: 0;
  font-size: 1.2rem;
}

.modal-text {
  margin-bottom: 24px;
  color: #ccc;
}

/* 3. Кнопки */
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.btn-cancel, .btn-confirm {
  padding: 8px 16px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-weight: bold;
}

.btn-cancel {
  background: #333;
  color: white;
}

.btn-cancel:hover {
  background: #444;
}

.btn-confirm {
  background: #e53935;
  color: white;
}

/* Червона для небезпечних дій */
.btn-confirm:hover {
  background: #d32f2f;
}
</style>