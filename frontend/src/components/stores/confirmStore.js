import {defineStore} from 'pinia';
import {ref} from 'vue';

export const useConfirmStore = defineStore('confirm', () => {
    // 1. Візуальний стан
    const isOpen = ref(false);
    const title = ref('');
    const message = ref('');

    // 2. Змінна для зберігання функції "вирішення" промісу
    let resolveCallback = null;

    // 3. Головна функція, яку ми будемо викликати з компонентів
    const ask = (newTitle, newMessage) => {
        title.value = newTitle;
        message.value = newMessage;
        isOpen.value = true;

        // Повертаємо новий Promise. Він буде "висіти" в очікуванні,
        // поки ми не викличемо resolveCallback.
        return new Promise((resolve) => {
            resolveCallback = resolve;
        });
    };

    // 4. Дії користувача
    const confirm = () => {
        isOpen.value = false;
        if (resolveCallback) resolveCallback(true); // Відповідаємо "Так"
    };

    const cancel = () => {
        isOpen.value = false;
        if (resolveCallback) resolveCallback(false); // Відповідаємо "Ні"
    };

    return {
        isOpen, title, message,
        ask, confirm, cancel
    };
});