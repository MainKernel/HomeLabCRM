<template>
  <div class="container">
    <p class="page-name"><strong>СКЛАД</strong></p>
    <input
        id="searchQuery"
        v-model="searchQuery"
        class="search-field"
        placeholder="IRFZ44N"
        type="text"
        @input="handleSearch"
    />
    <button class="btn-create-component" @click="isModalOpen = true">+КОМПОНЕНТ</button>
  </div>
  <TableMain/>

  <ItemCreateModal
      :isOpen="isModalOpen"
      @close="handleClose"
      @save="handleSaveNewItem"
  />
</template>

<script setup>
import {ref} from 'vue'
import {storeToRefs} from 'pinia';
import {useInventoryStore} from '@/modules/inventory/store/inventoryStore.js';

import {useModalStore} from '@/components/addComponentModal/modalStore.js';

import TableMain from '../../../components/TableMain.vue';
import ItemCreateModal from '../../../components/ItemCreateModal.vue';

const inventoryStore = useInventoryStore();
const createModal = useModalStore();

const {searchQuery, activeLocationId} = storeToRefs(inventoryStore);
const {fetchItemsForLocation} = inventoryStore;

let searchTimeout = null;
const isModalOpen = ref(false);

// 3. ВИПРАВЛЕНО: Додаємо $reset сюди, щоб функція save могла його знайти
const {submitForm, $reset} = createModal;

const handleClose = () => {
  isModalOpen.value = false;
  $reset()
}

const handleSaveNewItem = async () => {
  // Відправляємо форму
  const isSuccess = await submitForm();

  if (isSuccess) {
    // Якщо успіх - закриваємо модалку
    isModalOpen.value = false;

    // Тепер $reset знайдено, очищаємо форму
    if ($reset) $reset();

    // Оновлюємо таблицю
    await fetchItemsForLocation(activeLocationId.value, 0);
  } else {
    console.warn("Не вдалося зберегти деталь. Перевірте форму.");
  }
}

const handleSearch = () => {
  clearTimeout(searchTimeout);

  searchTimeout = setTimeout(async () => {
    await fetchItemsForLocation(activeLocationId.value, 0);
  }, 200);
}
</script>

<style scoped>

.container {
  background-color: var(--color-surface);
  padding-left: 10px;
  border-radius: 1rem;
  display: grid;
  grid-template-columns: 1fr 3fr 1fr;
  margin: 10px 30px 10px 30px;
}

.page-name {
  justify-content: start;
  font-size: large;
  margin: 10px 0px 10px 0px;
  padding: 0;
}

@keyframes slideUpDown {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

input {
  width: 15rem;
  height: 20px;
  align-self: center;
  justify-self: end;
  text-align: center;

  border: 2px solid;
  border-radius: 5px;
  background-color: rgba(97, 71, 176, 0.2);
}

.search-field {
  animation: slideUpDown 0.6s ease-out forwards;
}

button {
  width: 8rem;
  height: 2rem;
  align-self: center;
  justify-self: center;
}

.btn-create-component {
  background-color: #5758BB;
  border: none;
  border-radius: 5px;
  box-shadow: 0px 0px 5px black;

}

.btn-create-component:hover {
  scale: 0.98;
}


</style>