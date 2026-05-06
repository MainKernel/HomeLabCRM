import axios from 'axios';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
    timeout: 5000,
});

api.interceptors.request.use(
    (config) => {
        // 1. JWT Токен
        const token = localStorage.getItem('jwt_token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        // 2. Робочий простір (Workspace)
        config.headers['X-Workspace-Id'] = localStorage.getItem('workspaceId') || null;

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && (error.response.status === 401 || error.response.status === 403)) {
            console.warn('Помилка авторизації. Виходимо...');

            localStorage.removeItem('token');
            localStorage.removeItem('workspaceId');

            if (window.location.pathname !== '/login') {
                window.location.href = '/login';
            }
        }
        return Promise.reject(error);
    }
);

export default api;