<template>
  <div class="input-group">
    <label>Місцезнаходження</label>

    <div class="input-wrapper">
      <div class="input-container">
        <input
            v-model="searchQuery"
            placeholder="Полиця 1 | Бокс 7 | Комірка 3"
            required="true"
            type="text"
            @blur="handleBlur"
            @focus="isDropdownOpen = true"
            @input="handleInput"
        />
        <small v-if="showNewCategoryMessage">Буде створено нову категорію</small>
      </div>

      <ul
          v-if="isDropdownOpen && filterLocatons.length > 0"
          class="dropdown-list"
      >
        <li
            v-for="loc in filterLocatons"
            :key="loc.id"
            @mousedown.prevent="selectItem(loc)"
        >
          {{ loc.name }}
        </li>
      </ul>
    </div>
  </div>
</template>
<script setup>
import {computed, onMounted, ref} from 'vue';
import {storeToRefs} from 'pinia';
import {useModalStore} from './modalStore';

const modalStore = useModalStore()
const {formData, locations} = storeToRefs(modalStore)
const {fetchLocations} = modalStore

const isDropdownOpen = ref(false)
const searchQuery = ref(formData.value.location || '')

onMounted(async () => {
  await fetchLocations()
})

const filterLocatons = computed(() => {
  if (!locations.value) return [];
  if (!searchQuery.value) return locations.value;

  const query = searchQuery.value.toLowerCase();

  return locations.value.filter(loc =>
      loc.name?.toLowerCase().includes(query))
});

const showNewCategoryMessage = computed(() => {
  const query = searchQuery.value.trim().toLowerCase();

  // Не показуємо, якщо інпут порожній
  if (!query) return false;

  // Шукаємо точний збіг у базі
  const exactMatch = locations.value.find(
      loc => loc.name?.toLowerCase() === query
  );

  // Якщо точного збігу НЕМАЄ, значить категорія нова (показуємо повідомлення)
  return !exactMatch;
});
const handleInput = () => {
  isDropdownOpen.value = true
  formData.value.location = searchQuery.value
}

const selectItem = (item) => {
  searchQuery.value = item.name       // Підставляємо гарну назву в інпут
  formData.value.location = item.name // Передаємо фінальне значення в Pinia Store (або item.id, якщо бекенд чекає ID)
  isDropdownOpen.value = false        // Ховаємо меню
}

const handleBlur = () => {
  isDropdownOpen.value = false
}

</script>
<style scoped src="./style/locationfildcomponent.css"></style>