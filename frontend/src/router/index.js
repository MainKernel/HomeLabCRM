import { createRouter, createWebHistory } from 'vue-router'
import InventoryList from '../modules/inventory/views/InventoryList.vue' 
import LoginView from '../modules/auth/LoginView.vue'
import SandBoxView from '../modules/inventory/views/SandBoxView.vue'

const routes = [
  {
    path: '/inventory',
    name: 'Inventory',
    component: InventoryList
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { hideNavbar: true }
  },
  {
    path: '/sandbox',
    name: 'sandbox',
    component: SandBoxView
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router