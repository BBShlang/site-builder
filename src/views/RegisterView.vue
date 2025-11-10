<template>
  <div class="min-h-screen bg-gradient-to-b from-gray-50 to-pink-50 flex flex-col justify-center py-12 px-4">
    <div class="max-w-md mx-auto w-full">
      <!-- Иконка регистрации -->
      <div class="flex justify-center mb-6">
        <div class="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-gradient-to-br from-purple-100 to-pink-100">
          <Icon icon="heroicons:user-plus" class="w-8 h-8 text-purple-600" />
        </div>
      </div>

      <!-- Заголовок -->
      <h1 class="text-3xl font-bold text-center mb-3 bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">
        Регистрация
      </h1>
      <p class="text-gray-600 text-center mb-8">
        Регистрация аккаунта
      </p>
      <!-- Форма -->
      <div class="bg-white rounded-2xl shadow-md p-6 md:p-8 border border-gray-100">
        <form @submit.prevent="handleSubmit" class="space-y-5">
          <!-- Email -->
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700 mb-2">
              Email
            </label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <Icon icon="heroicons:envelope" class="w-5 h-5 text-gray-400" />
              </div>
              <input
                id="email"
                type="email"
                placeholder="ваша@почта.ru"
                class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition"
                required
              />
            </div>
          </div>

          <!-- Пароль -->
          <div>
            <label for="password" class="block text-sm font-medium text-gray-700 mb-2">
              Пароль
            </label>
            <div class="relative">
              <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                <Icon icon="heroicons:lock-closed" class="w-5 h-5 text-gray-400" />
              </div>
              <input
                id="password"
                type="password"
                placeholder="••••••••"
                class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition"
                required
              />
            </div>
          </div>

          <!-- Кнопка регистрации -->
          
          <button
            type="submit"
            class="w-full py-3.5 bg-gradient-to-r from-purple-600 to-pink-600 text-white font-semibold rounded-xl shadow-md hover:shadow-lg hover:from-purple-700 hover:to-pink-700 transform hover:-translate-y-0.5 transition-all"
          >
            <span>Зарегистрироваться</span>
          </button>

        </form>


        <!-- Ссылка на вход -->
        <div class="mt-6 pt-6 border-t border-gray-100 text-center">
          <p class="text-gray-600 text-sm">
            Уже есть аккаунт?
            <router-link
              to="/login"
              class="text-purple-600 font-medium hover:underline ml-1"
            >
              Вход
            </router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Icon } from '@iconify/vue'

// Логика будет на бэкенде — оставляем заглушку
const handleSubmit = () => {
  // Отправка формы будет обрабатываться на бэке
  console.log('Форма отправлена (логика на бэкенде)')
}
</script>