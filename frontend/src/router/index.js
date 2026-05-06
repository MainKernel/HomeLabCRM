import {createRouter, createWebHistory} from 'vue-router'
import InventoryList from '../modules/inventory/views/InventoryList.vue'
import LoginView from '../modules/auth/LoginView.vue'
import ItemPageView from '../components/itemPage/ItemPageView.vue'
import SettingsView from "@/modules/settings/SettingsView.vue";

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
        meta: {hideNavbar: true}
    },
    {
        path: '/inventory/item/:id',
        name: 'component',
        component: ItemPageView
    },
    {
        path: '/settings',
        name: 'settings',
        component: SettingsView
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router