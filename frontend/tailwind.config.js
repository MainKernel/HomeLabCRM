/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  // Обов'язково додаємо підтримку dark mode через клас
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        lab: {
          // Базові темні кольори
          bg: '#101018',         // Головний фон (Dark Base)
          surface: '#1A1A26',    // Фон панелей (Sidebar, Header)
          surface2: '#252536',   // Ховери та бордери

          // Акцентні кольори з палітри
          primary: '#004EFA',    // Яскраво-синій (Головний акцент)
          accent: '#A2FA00',     // Лаймовий (Успіх, маркери)
          danger: '#F92A01',     // Червоно-помаранчевий (Помилки, видалення)
          muted: '#3D507A',      // Приглушений синій (Вторинний текст, неактивні елементи)
          brown: '#A54937',      // Коричневий
          olive: '#657A3D'       // Оливковий
        }
      }
    },
  },
  plugins: [],
}