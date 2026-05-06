<template>
  <Teleport to="body">
    <div v-if="confirmStore.isOpen" class="modal-overlay" @click.self="confirmStore.cancel()">
      <div class="modal-content">
        <p class="modal-title">Cтворення нового воркспесу:</p>
        <label>Введіть назву для воркспейсу</label>
        <input v-model="confirmStore.workspaceName" placeholder="Склад" required type="text">
        <div class="modal-actions">
          <button class="btn-cancel" @click="confirmStore.cancel">Скасувати</button>
          <button class="btn-confirm" @click="confirmStore.confirm">Створити</button>
        </div>
      </div>
    </div>
  </Teleport>
</template>
<script setup>
import {useNewWorkspaceModalStore} from "@/modules/settings/components/store/workspaceModal.js";

const confirmStore = useNewWorkspaceModalStore();

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
  border: 2px solid;
  border-image: linear-gradient(to right, #101018, #FFA500, #501d95) 1;
  padding: 24px;
  border-radius: 8px;
  min-width: 300px;
  max-width: 400px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5);
  color: #fff;
  display: flex;
  flex-direction: column;
}

label {
  text-align: center;
  font-weight: bold;
}

input {
  background: #1e1e1e;
  border: 2px solid #501d95;
  border-radius: 8px;
  position: relative;
  align-self: center;
  margin: 16px 0;
  height: 2vw;
  width: 20vw;
  text-align: center;
  font-weight: bold;
  color: whitesmoke;
}

/* 3. Кнопки */
.modal-actions {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 12px;
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
  background: #33c514;
  color: white;
}

/* Червона для небезпечних дій */
.btn-confirm:hover {
  background: #d32f2f;
}

.modal-title {
  font-size: 1.2em;
  margin: 0;
}

.modal-workspace {
  margin-top: 0;
  margin-bottom: 15px;
  font-style: oblique;
  font-weight: bolder;
  color: #FFA500;
}
</style>