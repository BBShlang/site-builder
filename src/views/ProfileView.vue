<!-- src/views/ProfileView.vue -->
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
            Ваш профиль
          </h1>
          <p class="text-gray-600 max-w-md mx-auto">
            Управляйте своей учётной записью
          </p>
        </div>

        <!-- Форма -->
        <div class="p-6 md:p-8">
          <form @submit.prevent="handleSubmit" class="space-y-6">
            <!-- Email Section -->
            <div class="space-y-2">
              <label class="block text-sm font-medium text-gray- 800" for="email">
                Адрес электронной почты
              </label>
              <div class="relative">
                <input
                  id="email"
                  v-model="form.email"
                  type="email"
                  :disabled="isSubmitting"
                  class="w-full px-5 py-3.5 text-sm border border-white/40 rounded-xl bg-white/60 focus:ring-2 focus:ring-[#a66bff] focus:border-transparent placeholder:text-gray-400 text-gray-800 transition-all backdrop-blur-sm"
                  placeholder="you@example.com"
                  @input="clearError('email')"
                />
                <div v-if="form.email && !formErrors.email" class="absolute inset-y-0 right-4 flex items-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-5 h-5 text-green-500"
                    viewBox="0 0 20 20"
                    fill="currentColor"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                      clip-rule="evenodd"
                    />
                  </svg>
                </div>
              </div>
              <p v-if="formErrors.email" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path
                    fill-rule="evenodd"
                    d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
                    clip-rule="evenodd"
                  />
                </svg>
                {{ formErrors.email }}
              </p>
            </div>

            <!-- Login Info (Read-only) -->
            <div class="space-y-2">
              <label class="block text-sm font-medium text-gray-800"> Логин </label>
              <div
                class="px-5 py-3.5 text-sm bg-white/50 rounded-xl border border-white/40 text-gray-600 backdrop-blur-sm cursor-not-allowed"
              >
                {{ form.login }}
              </div>
            </div>

            <!-- Actions -->
            <div class="flex flex-wrap gap-4 pt-2 animate-fade-in-delay-1">
              <button
                type="submit"
                :disabled="isSubmitting || !hasChanges"
                class="px-6 py-3 bg-gradient-to-r from-[#6a5af9] via-[#8a6bff] to-[#a66bff] text-white font-medium rounded-xl shadow-md hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5 flex items-center border border-white/20 backdrop-blur-sm disabled:opacity-70"
              >
                <span v-if="isSubmitting" class="flex items-center">
                  <svg
                    class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                  >
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path
                      class="opacity-75"
                      fill="currentColor"
                      d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                    ></path>
                  </svg>
                  Сохранение...
                </span>
                <span v-else> Сохранить изменения </span>
              </button>

              <button
                type="button"
                :disabled="isSubmitting || !hasChanges"
                @click="resetForm"
                class="px-6 py-3 border-2 border-[#a66bff] text-[#6a5af9] font-medium rounded-xl bg-white/60 backdrop-blur-sm hover:bg-white/80 transition-all duration-300 shadow-sm hover:shadow-md transform hover:-translate-y-0.5 disabled:opacity-70"
              >
                Отменить
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

interface User {
  email: string
  login: string
}

const form = ref<User>({ email: '', login: '' })
const originalForm = ref<User>({ email: '', login: '' })
const isSubmitting = ref(false)
const formErrors = ref<Record<string, string>>({})

onMounted(async () => {
  try {
    const userData: User = await fetchUserProfile()
    form.value = { ...userData }
    originalForm.value = { ...userData }
  } catch (error) {
    console.error('Не удалось загрузить профиль:', error)
  }
})

const hasChanges = computed(() => {
  return JSON.stringify(form.value) !== JSON.stringify(originalForm.value)
})

const clearError = (field: string) => {
  if (formErrors.value[field]) {
    formErrors.value = { ...formErrors.value, [field]: '' }
  }
}

const validate = () => {
  const errors: Record<string, string> = {}

  if (!form.value.email) {
    errors.email = 'Email обязателен'
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.value.email)) {
    errors.email = 'Некорректный email'
  }

  formErrors.value = errors
  return Object.keys(errors).length === 0
}

const handleSubmit = async () => {
  if (!validate()) return

  isSubmitting.value = true

  try {
    await updateProfile({ email: form.value.email })
    originalForm.value = { ...form.value }
    alert('✅ Профиль успешно обновлён!')
  } catch (error) {
    console.error('Ошибка при обновлении профиля:', error)
    alert('❌ Не удалось сохранить изменения. Попробуйте позже.')
  } finally {
    isSubmitting.value = false
  }
}

const resetForm = () => {
  form.value = { ...originalForm.value }
  formErrors.value = {}
}

// === МОКИ ДЛЯ ДЕМОНСТРАЦИИ ===
async function fetchUserProfile(): Promise<User> {
  return new Promise((resolve) => {
    setTimeout(() => resolve({ email: 'user@example.com', login: 'user123' }), 300)
  })
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
async function updateProfile(_data: { email: string }): Promise<void> {
  return new Promise((resolve) => {
    setTimeout(resolve, 500)
  })
}
</script>

<style scoped>
@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(16px) scale(0.98);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(-12px) rotate(2deg);
  }
}

@keyframes pulse-slow {
  0%,
  100% {
    opacity: 0.3;
    transform: scale(1);
  }
  50% {
    opacity: 0.5;
    transform: scale(1.05);
  }
}

.animate-fade-in {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-1 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.25s forwards;
  animation-fill-mode: both;
}

.animate-float {
  animation: float 8s ease-in-out infinite;
}

.animate-pulse-slow {
  animation: pulse-slow 6s ease-in-out infinite;
}

.animation-delay-2000 {
  animation-delay: 2s;
}
</style>