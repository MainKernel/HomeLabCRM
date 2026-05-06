<template>
  <div class="input-group">
    <label v-if="label">{{ label }}</label>

    <div class="input-wrapper">
      <div class="input-container">
        <input
            v-model="searchQuery"
            :disabled="disabled"
            :placeholder="placeholder"
            :required="required"
            type="text"
            @blur="handleBlur"
            @focus="isDropdownOpen = true"
            @input="handleInput"
        />
        <!-- Динамічне повідомлення -->
        <small v-if="showNewItemMessage">{{ newItemMessage }}</small>
      </div>

      <ul
          v-if="isDropdownOpen && filteredOptions.length > 0"
          class="dropdown-list"
      >
        <li
            v-for="(option, index) in filteredOptions"
            :key="index"
            @mousedown.prevent="selectItem(option)"
        >
          <!-- Виводимо властивість об'єкта за ключем (наприклад, 'name') -->
          {{ option[displayKey] || option }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import {computed, ref, watch} from 'vue';

// 1. ОПИСУЄМО ВХІДНІ ДАНІ (PROPS)
const props = defineProps({
  modelValue: {type: [String, Object], default: ''},
  options: {type: Array, default: () => []}, // Масив варіантів (locations, categories тощо)
  label: {type: String, default: ''},
  placeholder: {type: String, default: ''},
  newItemMessage: {type: String, default: 'Буде створено новий елемент'},
  displayKey: {type: String, default: 'name'}, // Яке поле об'єкта показувати (за замовчуванням 'name')
  disabled: {type: Boolean, default: false},
  required: {type: Boolean, default: false}
});

// 2. ОПИСУЄМО ВИХІДНІ ДАНІ (EMITS)
const emit = defineEmits(['update:modelValue']);

// 2. СТВОРЮЄМО ФУНКЦІЮ ДЛЯ РОЗПАКУВАННЯ ТЕКСТУ
// Вона перевіряє: якщо прийшов об'єкт, дістає з нього 'name', якщо текст - залишає текст
const getDisplayText = (value) => {
  if (!value) return '';
  if (typeof value === 'object') return value[props.displayKey] || '';
  return String(value);
};

const isDropdownOpen = ref(false);

// 3. Ініціалізуємо поле вводу очищеним текстом (замість [object Object])
const searchQuery = ref(getDisplayText(props.modelValue));

// Слідкуємо за змінами ззовні і теж розпаковуємо текст
watch(() => props.modelValue, (newVal) => {
  searchQuery.value = getDisplayText(newVal);
});


// 3. УНІВЕРСАЛЬНА ЛОГІКА ФІЛЬТРАЦІЇ
const filteredOptions = computed(() => {
  if (!props.options.length) return [];
  if (!searchQuery.value) return props.options;

  const query = searchQuery.value.toLowerCase();

  return props.options.filter(opt => {
    const valueToSearch = opt[props.displayKey] || opt;
    return String(valueToSearch).toLowerCase().includes(query);
  });
});

const showNewItemMessage = computed(() => {
  const query = searchQuery.value.trim().toLowerCase();
  if (!query) return false;

  const exactMatch = props.options.find(opt => {
    const valueToCompare = opt[props.displayKey] || opt;
    return String(valueToCompare).toLowerCase() === query;
  });

  return !exactMatch;
});

// 4. ОБРОБНИКИ ПОДІЙ
const handleInput = () => {
  isDropdownOpen.value = true;
  // Передаємо введене значення батькові
  emit('update:modelValue', searchQuery.value);
};

const selectItem = (item) => {
  searchQuery.value = item[props.displayKey] || item;

  // Передаємо обране значення батькові
  emit('update:modelValue', item);
  isDropdownOpen.value = false;
};

const handleBlur = () => {
  isDropdownOpen.value = false;
};
</script>
<style scoped src="./style/inputfield.css">
</style>