<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-[#eef1ff] to-[#fde8ff] px-4">
    <div class="bg-white/80 backdrop-blur-lg p-10 rounded-3xl shadow-lg max-w-md w-full">
      <h1
        class="text-3xl font-bold text-center bg-gradient-to-r from-[#6a5af9] to-[#d966ff] bg-clip-text text-transparent mb-6"
      >
        Регистрация
      </h1>

      <form @submit.prevent="handleRegister" class="space-y-6">
        <div>
          <label class="block text-gray-700 font-medium mb-1">Имя пользователя</label>
          <input
            v-model="username"
            type="text"
            required
            class="w-full px-4 py-3 rounded-xl border border-gray-300 focus:ring-2 focus:ring-[#6a5af9] outline-none"
          />
        </div>

        <div>
          <label class="block text-gray-700 font-medium mb-1">Email</label>
          <input
            v-model="email"
            type="email"
            required
            class="w-full px-4 py-3 rounded-xl border border-gray-300 focus:ring-2 focus:ring-[#6a5af9] outline-none"
          />
        </div>

        <div>
          <label class="block text-gray-700 font-medium mb-1">Пароль</label>
          <input
            v-model="password"
            type="password"
            required
            class="w-full px-4 py-3 rounded-xl border border-gray-300 focus:ring-2 focus:ring-[#6a5af9] outline-none"
          />
        </div>

        <button
          type="submit"
          class="w-full py-3 rounded-xl bg-gradient-to-r from-[#6a5af9] to-[#d966ff] text-white font-semibold shadow-md hover:shadow-lg transition"
        >
          Создать аккаунт
        </button>
      </form>

      <p class="mt-6 text-center text-gray-600">
        Уже есть аккаунт?
        <router-link to="/login" class="text-[#6a5af9] font-medium hover:underline">
          Войти
        </router-link>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/client'

const username = ref('')
const email = ref('')
const password = ref('')

const router = useRouter()

const handleRegister = async () => {
  try {
    const res = await register(username.value, email.value, password.value)

    localStorage.setItem('authToken', res.token)

    router.push('/')
  } catch (err) {
    console.error(err)
    alert('Ошибка регистрации. Возможно пользователь уже существует.')
  }
}

</script>
