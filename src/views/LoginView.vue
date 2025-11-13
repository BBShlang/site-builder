<!-- src/views/RegisterView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-[#f9f7ff] via-[#fdf9ff] to-[#fff0f8] flex flex-col justify-center py-12 px-4 overflow-hidden relative">
    <!-- Фоновые элементы -->
    <div class="absolute top-1/3 -right-16 w-44 h-44 rounded-full bg-[#ffdde8]/20 blur-3xl animate-float-fast"></div>
    <div class="absolute bottom-1/3 -left-12 w-52 h-52 rounded-full bg-[#e0d6ff]/20 blur-3xl animate-float-slow animation-delay-1500"></div>

    <div class="max-w-md mx-auto w-full relative z-10">
      <!-- Единая карточка -->
      <div class="bg-white/70 backdrop-blur-lg rounded-2xl shadow-[0_10px_30px_rgba(100,80,180,0.08)] border border-white/40 overflow-hidden">
        <!-- Верхняя часть: иконка + заголовок -->
        <div class="text-center py-8 px-6 border-b border-white/40 bg-white/60">
          <div class="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-gradient-to-br from-[#e8dfff] to-[#ffe6f6] shadow-[0_4px_16px_rgba(138,107,255,0.12)] ring-1 ring-white/40 border border-white/30 mx-auto">
            <Icon icon="heroicons:user-plus" class="w-7 h-7 text-[#8a6bff]" />
          </div>
          <h1 class="text-3xl font-medium text-gray-800 mt-5 mb-2">
            Войдите в аккаунт
          </h1>
          <p class="text-gray-600 max-w-xs mx-auto leading-relaxed">
            Начните создавать лендинги уже сегодня
          </p>
        </div>

        <!-- Форма -->
        <div class="p-6 md:p-8">
          <form @submit.prevent="handleSubmit" class="space-y-5">
            <!-- Email -->
            <div>
              <label for="email" class="block text-sm font-medium text-gray-700 mb-2.5 tracking-tight">
                Email
              </label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 flex items-center pl-3.5 pointer-events-none">
                  <Icon icon="heroicons:envelope" class="w-5 h-5 text-gray-400" />
                </div>
                <input
                  id="email"
                  type="email"
                  placeholder="ваша@почта.ru"
                  class="w-full pl-11 pr-4 py-3.5 bg-white/80 border border-[#eae6ff] rounded-xl focus:outline-none focus:ring-2 focus:ring-[#8a6bff]/30 focus:border-[#a66bff] transition-all duration-300"
                  required
                />
              </div>
            </div>

            <!-- Пароль -->
            <div>
              <label for="password" class="block text-sm font-medium text-gray-700 mb-2.5 tracking-tight">
                Пароль
              </label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 flex items-center pl-3.5 pointer-events-none">
                  <Icon icon="heroicons:lock-closed" class="w-5 h-5 text-gray-400" />
                </div>
                <input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  class="w-full pl-11 pr-4 py-3.5 bg-white/80 border border-[#eae6ff] rounded-xl focus:outline-none focus:ring-2 focus:ring-[#8a6bff]/30 focus:border-[#a66bff] transition-all duration-300"
                  required
                />
              </div>
            </div>

            <!-- Кнопка -->
            <button
              type="submit"
              class="w-full py-3.5 bg-gradient-to-r from-[#8a6bff] via-[#a66bff] to-[#c977e0] text-white font-medium rounded-xl shadow-[0_6px_18px_rgba(138,107,255,0.25)] hover:shadow-lg hover:from-[#7a5bff] hover:via-[#965bff] hover:to-[#c066d0] transform hover:-translate-y-0.5 transition-all duration-300"
            >
              Войти
            </button>
          </form>

          <!-- Ссылка на вход -->
          <div class="mt-7 pt-6 border-t border-[#eae6ff] text-center">
            <p class="text-gray-600 text-sm">
              Ещё нет аккаунта?
              <router-link
                to="/register"
                class="text-[#8a6bff] font-medium hover:underline ml-1 transition-colors"
              >
                Зарегистрироваться
              </router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Icon } from '@iconify/vue'

const handleSubmit = () => {
  console.log('Форма отправлена')
}
</script>

<style scoped>
@keyframes float-slow {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-16px) rotate(1deg); }
}
@keyframes float-fast {
  0%, 100% { transform: translateX(0) rotate(0deg); }
  50% { transform: translateX(10px) rotate(2deg); }
}

.animate-float-slow { animation: float-slow 12s ease-in-out infinite; }
.animate-float-fast { animation: float-fast 10s ease-in-out infinite; }
.animation-delay-1500 { animation-delay: 1.5s; }
</style>