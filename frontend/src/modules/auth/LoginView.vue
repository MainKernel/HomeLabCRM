<template>
  <div class="login-form-container">
    <div class="login-form">
    
    
    <form @submit.prevent="handleLogin">
        <h3>HomeLabCRM</h3>
      <h1>Вхід до системи</h1>
      <div class="email ifield">
        <label for="email">Електронна пошта:</label>
        <input 
          id="email" 
          type="email" 
          v-model="email" 
          required 
          placeholder="admin@homelab.com"
        />
      </div>

      <div class="password ifield">
        <label for="password">Пароль:</label>
        <input 
          id="password" 
          type="password" 
          v-model="password" 
          required 
          placeholder="password123"
        />
      </div>

      <div v-if="authStore.error" style="color: red;">
        {{ authStore.error }}
      </div>

      <div class="btn">
        <button type="submit" :disabled="isLoading">
          {{ isLoading ? 'Завантаження...' : 'Увійти' }}
        </button>
      </div>

    </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore} from '../auth/authStore' // Переконайтеся, що шлях правильний

const router = useRouter()
const authStore = useAuthStore()

// Локальний стан для полів форми
const email = ref('')
const password = ref('')
const isLoading = ref(false)

const handleLogin = async () => {
  isLoading.value = true;
  
  // Викликаємо метод login з нашого Pinia стору
  const success = await authStore.login(email.value, password.value);
  
  isLoading.value = false;

  if (success) {
    router.push('/inventory');
  }
}
</script>

<style scoped>
.login-form{
    
    display: grid;
    grid-template-columns: 1fr 3fr 1fr;
    grid-template-rows: 2fr 3fr 2fr;
    height: 100vh;
}
form{
    background-color: var(--color-surface);
    grid-column-start: 2;
    grid-row-start: 2;
    padding: 50px;
    border-color: var(--color-text-secondary);
    border-radius: 10px;
    border: 5px solid;
}
form h1{
    text-align: center;
    color: var(--);
}
form h3{
    margin-top: 0px;
    padding-top: 0px;
    color: var(--color-text-secondary)
}
.ifield{
    display: grid;

}
input{
    height: 30px;
    margin: 10px 0px 10px 0;
    text-align: center;
}
.btn{
    display: flex;
    align-items: center;
    justify-content: center;
}
button{
    margin-top: 10px;
    background-color: var(--color-accenet);
    border-radius: 5px;
    height: 3rem;
    width: 5rem;
    border: 5px solid black;
}
</style>