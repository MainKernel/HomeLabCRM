// src/stores/authStore.js
import { defineStore } from 'pinia';
import { ref } from 'vue';
import api from '@/api/axiosInstance'; // Ваш axios без interceptors (або базовий axios)
import axios from 'axios';

export const useAuthStore = defineStore('auth', () => {
    // Читаємо токен з LocalStorage при старті
    const token = ref(localStorage.getItem('jwt_token') || null);
    const user = ref(null);
    const error = ref(null);

    const login = async (email, password) => {
        try {
            // Використовуємо чистий axios для логіну, щоб interceptors не заважали
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                email, password
            });
            
            // Зберігаємо токен
            token.value = response.data.token;
            localStorage.setItem('jwt_token', token.value);
            
            return true;
        } catch (err) {
            error.value = "Невірний логін або пароль";
            return false;
        }
    };

    const logout = () => {
        token.value = null;
        user.value = null;
        localStorage.removeItem('jwt_token');
        // Редірект на сторінку логіну
        window.location.href = '/login';
    };

    return { token, user, error, login, logout };
});