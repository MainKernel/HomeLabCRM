import {defineStore} from 'pinia';
import {ref} from 'vue';


export const useSharedModalStore
    = defineStore('sharedModalStore', () => {

    const isOpen = ref(false);
    const email = ref('');
    const title = ref('');

    let resolveCallback = null;

    const ask = (workspaceTitle) => {
        isOpen.value = true;
        email.value = '';
        title.value = workspaceTitle;

        return new Promise(resolve => {
            resolveCallback = resolve;
        });
    };

    const confirm = () => {
        if (!email.value.trim()) {
            return;
        }
        isOpen.value = false;

        if (resolveCallback) resolveCallback(email.value.trim());
    };

    const cancel = () => {
        isOpen.value = false;
        if (resolveCallback) resolveCallback(null);
    }

    return {
        isOpen,
        email,
        title,
        ask,
        confirm,
        cancel
    }
})
