import { defineConfig } from 'histoire'
import { HstVue } from '@histoire/plugin-vue'

export default defineConfig({
  plugins: [
    HstVue(), // Обов'язково з дужками! Це запускає плагін
  ],
  setupFile: './src/histoire.setup.js',
  vite: {
    resolve: {
      // Змушуємо Vite завжди брати лише одну головну копію цих бібліотек
      dedupe: ['vue', 'pinia'] 
    }
    }
})