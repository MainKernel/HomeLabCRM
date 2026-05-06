<template>

  <div class="app-container">
    <ConfirmModal/>
    <nav v-if="!route.meta.hideNavbar">
      <div class="link-container">
        <div class="workspace-container">
          <WorkSpaceSelector/>
        </div>
        <div class="link-container-main">
          <router-link to="/dashboard">ДАШБОРД</router-link>
          <router-link to="/inventory">СКЛАД</router-link>
          <router-link to="/">ПРОЕКТИ</router-link>
          <router-link to="/">ДОВІДНИК</router-link>
        </div>
        <div class="link-container-second">
          <router-link to="/settings"><img height="30px" src="../public/settings.svg" width="30px"></router-link>
          <router-link to="/"><img height="30px" src="../public/exit.svg" width="20px" @click="authStore.logout()">
          </router-link>
        </div>
      </div>
    </nav>

    <main>
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
import {useRoute} from 'vue-router'
import WorkSpaceSelector from './components/WorkSpaceSelector.vue'
import ConfirmModal from "@/components/ConfirmModal.vue";
import {useAuthStore} from "@/modules/auth/authStore.js";

const route = useRoute();
const authStore = useAuthStore();
</script>

<style scoped>

nav {
  position: sticky;
  top: 0;
  height: 2rem;
  background-color: rgba(30, 41, 59, 0.1);
  padding: 0 2rem;
  z-index: 9999;
}

nav .link-container {
  display: grid;
  grid-template-columns: 1fr 1fr 4fr 1fr 1fr;
  grid-template-rows: 1fr;
  height: 100%;
}

nav .link-container-main a {
  margin: 0 1rem 0 1rem;
  padding: 0 1rem 0 1rem;
  background: linear-gradient(170deg, rgba(30, 41, 59, 1.0), rgba(157, 80, 255, 0.01));
  border-radius: 5px;
  color: whitesmoke;
  text-decoration: none;
}

nav .link-container-main a:hover {
  background: linear-gradient(170deg, rgba(30, 41, 59, 1.0), rgba(157, 80, 255, 1.0));
  border-radius: 5px;

}

nav .link-container-main {
  grid-column-start: span 2;
  margin-left: auto;
  align-self: center;
}

.link-container-second {
  grid-column-start: -1;
  margin-left: auto;
  align-self: center;
}

nav .link-container-second img {
  margin-left: 10px;
  margin-right: 10px;
  align-self: center;
  justify-content: center;
}

.workspace-container {
  align-self: center;
  justify-content: center;
}
</style>

