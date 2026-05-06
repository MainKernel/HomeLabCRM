<template>
  <ShareModal/>
  <NewWorkspaceModal/>
  <NewUserModal/>
  <div class="settings-view">
    <div class="header">
      <div class="title">
        <p class="title-text">Налаштування</p>
      </div>
    </div>

    <!-- Додаємо індикатор завантаження -->
    <div v-if="isLoading" class="loader">
      Завантаження просторів...
    </div>

    <div v-else class="content">
      <div class="workspaces">
        <div class="my-workspaces">

          <!-- Власні простори -->
          <div class="workspaces-wrapper">
            <p>Мої робочі простори</p>
            <!-- Правильна перевірка масиву на наявність елементів -->
            <div v-if="myWorkspaces && myWorkspaces.length > 0" class="workspaces-box">
              <div v-for="workspace in myWorkspaces" :key="workspace.id" class="workspace-item">
                <p>{{ workspace.name }}</p>
                <button @click="shareWorkspace(workspace.id, workspace.name)">Поділитись</button>
                <button @click="deleteWorkspace(workspace.id)">X</button>
              </div>
            </div>
            <div v-else class="workspaces-box">
              <p>У вас ще немає створеного простору</p>
            </div>
          </div>

          <!-- Спільні простори -->
          <div class="workspaces-wrapper">
            <p>Надані мені робочі простори</p>
            <!-- Правильна перевірка масиву -->
            <div v-if="sharedToMeWorkspaces && sharedToMeWorkspaces.length > 0" class="workspaces-box">
              <div v-for="workspace in sharedToMeWorkspaces" :key="workspace.id" class="workspace-item">
                {{ workspace.name }}
              </div>
            </div>
            <div v-else class="workspaces-box">
              <p>З вами ще ніхто не поділився простором</p>
            </div>
          </div>

        </div>
      </div>

      <div class="buttons">
        <p class="section-name">Налаштування робочого простору</p>
        <div class="item">
          <button @click="newWorkspace">Додати новий воркспейс</button>
        </div>
        <div class="item">
          <button>Додати категорію</button>
        </div>
        <div class="item">
          <button>Додати місцезнаходження</button>
        </div>
        <div v-if="authStore.isAdmin" class="item">
          <button @click="newUser">Додати нового користувача</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted} from "vue";
import {storeToRefs} from "pinia";
import {useConfirmStore} from "@/components/stores/confirmStore.js";
// Зверніть увагу: я використовую назву з приставкою Store, як ми домовились
import {useSettingsStore} from "./settingsStore.js";
import api from "@/api/axiosInstance.js";
import ShareModal from "@/modules/settings/components/ShareModal.vue";
import {useSharedModalStore} from "@/modules/settings/components/store/sharedModal.js";
import NewWorkspaceModal from "@/modules/settings/components/NewWorkspaceModal.vue";
import {useNewWorkspaceModalStore} from "@/modules/settings/components/store/workspaceModal.js";
import NewUserModal from "@/modules/settings/components/NewUserModal.vue";
import {useNewUserModalStore} from "@/modules/settings/components/store/userModal.js";
import {useAuthStore} from "@/modules/auth/authStore.js";

const settingsStore = useSettingsStore();
const authStore = useAuthStore();

// Витягуємо тільки РЕАКТИВНІ ЗМІННІ через storeToRefs
const {myWorkspaces, sharedToMeWorkspaces, isLoading} = storeToRefs(settingsStore);

onMounted(async () => {
  await settingsStore.loadAllWorkspaces();
});

const printTest = () => {
  console.log("Мої:", myWorkspaces.value);
  console.log("Спільні:", sharedToMeWorkspaces.value);
};

const confirmStore = useConfirmStore();

const deleteWorkspace = async (workspaceId) => {
  // 1. Викликаємо попап. Код зупиняється на цьому рядку і чекає на клік юзера.
  const isConfirmed = await confirmStore.ask(
      "Видалення простору",
      "Ви дійсно хочете видалити цей робочий простір? Цю дію неможливо скасувати."
  );

  // 2. Якщо юзер натиснув "Скасувати", ми просто перериваємо функцію.
  if (!isConfirmed) {
    return;
  }

  // 3. Якщо код дійшов сюди - юзер натиснув "Так". Робимо запит до Spring Boot.
  try {
    await api.delete(`/workspaces/${workspaceId}`);
    console.log("Успішно видалено!");

  } catch (error) {
    console.error("Помилка видалення:", error);
  }
};

const useSharedModal = useSharedModalStore();

const shareWorkspace = async (workspaceId, workspace) => {

  const userEmail = await useSharedModal.ask(workspace);
  if (!userEmail) {
    return;
  }
  try {
    await api.post(`/workspaces/share/${workspaceId}`, {
      email: userEmail
    });
    console.log("Успішно надано доступ користувачу");
  } catch (error) {
    console.error(error);
  }
}

const useNewWorkspaceModal = useNewWorkspaceModalStore();

const newWorkspace = async () => {
  const newWorkspaceName = await useNewWorkspaceModal.ask();

  if (!newWorkspaceName) {
    return;
  }
  try {
    console.log(`New WorkSpace Name ${newWorkspaceName}`)
    await api.post('/workspaces/new', {
      name: newWorkspaceName
    });
    console.log("Успішно додано новий воркспейс.")
  } catch (error) {
    console.error(error);
  }
}

const useUserModalStore = useNewUserModalStore();

const newUser = async () => {
  const newUserName = await useUserModalStore.ask();

  if (!newUserName) {
    return;
  }
  try {
    await api.post(`/admin/user/new`, {
      email: newUserName.email,
      password: newUserName.password
    });
  } catch (error) {
    console.error(error);
  }
}

</script>

<style scoped>
.workspace-item {
  padding: 8px;
  border: 1px solid #444;
  margin-bottom: 4px;
  border-radius: 4px;
  color: white;
}

/* Інші ваші стилі */
</style>