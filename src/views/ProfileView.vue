<template>
  <div class="min-h-screen bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] py-12 px-4 overflow-hidden relative">
    <!-- Фоновые декоративные элементы -->
    <div class="absolute top-1/4 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"></div>
    <div class="absolute bottom-1/3 right-1/5 w-40 h-40 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"></div>
    <div class="absolute top-1/3 right-1/4 w-16 h-16 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"></div>

    <div class="max-w-2xl mx-auto relative z-10">
      <!-- Единая карточка -->
      <div
        class="bg-white/70 backdrop-blur-sm rounded-2xl shadow-xl border border-white/30 overflow-hidden transition-all duration-400 hover:shadow-2xl animate-fade-in"
      >
        <!-- Верхняя часть: иконка + заголовок -->
        <div class="text-center py-8 px-6 border-b border-white/40 bg-white/60">
          <div
            class="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-gradient-to-br from-[#e6dfff] via-[#fde8ff] to-[#ffe6f6] mb-5 shadow-[0_8px_20px_rgba(150,100,200,0.12)] border border-white/50 backdrop-blur-sm"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="w-8 h-8 text-[#6a5af9]"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
              />
            </svg>
          </div>
          <h1
            class="text-3xl font-bold bg-gradient-to-r from-[#6a5af9] via-[#a66bff] to-[#d966ff] bg-clip-text text-transparent mb-2"
          >
            Профиль пользователя
          </h1>
          <p class="text-gray-600 max-w-md mx-auto">
            Просмотр информации учётной записи
          </p>
        </div>

        <!-- Информация о пользователе -->
        <div class="p-6 md:p-8 space-y-6">
          <div>
            <label class="block text-sm font-medium text-gray-800 mb-1">Логин</label>
            <div
              class="px-5 py-3.5 text-sm bg-white/60 rounded-xl border border-white/40 text-gray-700 backdrop-blur-sm cursor-not-allowed"
            >
              {{ profile.login }}
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-800 mb-1">Email</label>
            <div
              class="px-5 py-3.5 text-sm bg-white/60 rounded-xl border border-white/40 text-gray-700 backdrop-blur-sm cursor-not-allowed"
            >
              {{ profile.email }}
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-800 mb-1">Пароль</label>
            <div
              class="px-5 py-3.5 text-sm bg-white/60 rounded-xl border border-white/40 text-gray-700 backdrop-blur-sm cursor-not-allowed"
            >
              ****** 
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

interface User {
  email: string
  login: string
}

// Данные пользователя
const profile = ref<User>({ email: '', login: '' })

onMounted(async () => {
  try {
    const userId = localStorage.getItem('userId')
    if (!userId) throw new Error('Пользователь не авторизован')

    // Проверяем локальное хранилище
    const saved = localStorage.getItem(`user_${userId}`)
    if (saved) {
      profile.value = JSON.parse(saved)
    } else {
      const userData: User = await fetchUserProfile(userId)
      profile.value = { ...userData }
      localStorage.setItem(`user_${userId}`, JSON.stringify(userData))
    }
  } catch (error) {
    console.error('Не удалось загрузить профиль:', error)
  }
})

// === МОКИ ДЛЯ ДЕМОНСТРАЦИИ ===
async function fetchUserProfile(userId: string): Promise<User> {
  // В реальном приложении здесь будет запрос к API
  return new Promise((resolve) => {
    setTimeout(() => resolve({ email: `${userId}@example.com`, login: `${userId}`, login: `${userId}` }), 300)
  })
}
</script>

<style scoped>
@keyframes fade-in { from { opacity: 0; transform: translateY(16px) scale(0.98); } to { opacity: 1; transform: translateY(0) scale(1); } }
@keyframes float { 0%,100% { transform: translateY(0px) rotate(0deg); } 50% { transform: translateY(-12px) rotate(2deg); } }
@keyframes pulse-slow { 0%,100% { opacity: 0.3; transform: scale(1); } 50% { opacity: 0.5; transform: scale(1.05); } }

.animate-fade-in { animation: fade-in 0.7s cubic-bezier(0.22,0.61,0.36,1) forwards; }
.animate-float { animation: float 8s ease-in-out infinite; }
.animate-pulse-slow { animation: pulse-slow 6s ease-in-out infinite; }
.animation-delay-2000 { animation-delay: 2s; }
</style>
