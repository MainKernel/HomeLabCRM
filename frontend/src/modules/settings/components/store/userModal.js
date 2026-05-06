import {defineStore} from 'pinia';
import {ref} from 'vue';


export const useNewUserModalStore
    = defineStore('newUserModalStore', () => {

    const isOpen = ref(false);
    const email = ref('');
    const password = ref('');

    let resolveCallback = null;

    const ask = () => {
        isOpen.value = true;
        email.value = '';
        password.value = '';

        return new Promise(resolve => {
            resolveCallback = resolve;
        });
    };

    const confirm = () => {
        if (!email.value.trim() || !password.value.trim()) {
            return;
        }
        isOpen.value = false;

        if (resolveCallback) resolveCallback({
            email: email.value,
            password: password.value
        });
    };

    const cancel = () => {
        isOpen.value = false;
        if (resolveCallback) resolveCallback(null);
    }

    return {
        isOpen,
        email,
        password,
        ask,
        confirm,
        cancel
    }
})
