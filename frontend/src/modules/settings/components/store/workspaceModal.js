import {defineStore} from 'pinia';
import {ref} from 'vue';


export const useNewWorkspaceModalStore
    = defineStore('newWorkspaceModalStore', () => {

    const isOpen = ref(false);
    const workspaceName = ref('');

    let resolveCallback = null;

    const ask = () => {
        isOpen.value = true;
        workspaceName.value = '';

        return new Promise(resolve => {
            resolveCallback = resolve;
        });
    };

    const confirm = () => {
        if (!workspaceName.value.trim()) {
            return;
        }
        isOpen.value = false;

        if (resolveCallback) resolveCallback(workspaceName.value.trim());
    };

    const cancel = () => {
        isOpen.value = false;
        if (resolveCallback) resolveCallback(null);
    }

    return {
        isOpen,
        workspaceName,
        ask,
        confirm,
        cancel
    }
})
