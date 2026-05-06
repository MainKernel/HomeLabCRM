// src/stores/authStore.js
import {defineStore} from 'pinia';
import {computed, ref} from 'vue'; // НОВЕ: імпортуємо computed
import axios from 'axios';
import {jwtDecode} from "jwt-decode"; // НОВЕ: імпортуємо декодер

export const useAuthStore = defineStore('auth', () => {

    const token = ref(localStorage.getItem('jwt_token') || null);
    const user = ref(null);
    const error = ref(null);

    // НОВЕ: Змінна для зберігання ролі
    const userRole = ref(null);

    // НОВЕ: Функція для безпечного читання токена під час завантаження сторінки
    const initRoleFromToken = () => {
        if (token.value) {
            try {
                const decoded = jwtDecode(token.value);
                // Зверніть увагу: перевірте, як саме називається поле з роллю у вашому бекенді.
                // Зазвичай це "role", "roles" або "authorities"
                userRole.value = decoded.role;
            } catch (e) {
                console.error("Помилка читання токена:", e);
                // Якщо токен зламаний, очищаємо його
                token.value = null;
                localStorage.removeItem('jwt_token');
            }
        }
    };

    // Викликаємо функцію одразу при створенні стору (щоб роль не зникала після F5)
    initRoleFromToken();

    // НОВЕ: Зручний геттер, який можна використовувати у v-if="authStore.isAdmin"
    const isAdmin = computed(() => userRole.value === 'ROLE_ADMIN');

    const login = async (email, password) => {
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                email, password
            });

            token.value = response.data.token;
            localStorage.setItem('jwt_token', token.value);

            // НОВЕ: Після успішного логіну одразу декодуємо новий токен і записуємо роль
            const decoded = jwtDecode(token.value);
            userRole.value = decoded.role;
            console.log(decoded.role);

            return true;
        } catch (err) {
            error.value = "Невірний логін або пароль";
            return false;
        }
    };

    const logout = () => {
        token.value = null;
        user.value = null;
        userRole.value = null; // НОВЕ: Очищаємо роль при виході
        localStorage.removeItem('jwt_token');

        window.location.href = '/login';
    };

    // НОВЕ: Експортуємо userRole та isAdmin, щоб їх бачили компоненти
    return {token, user, error, userRole, isAdmin, login, logout};
});