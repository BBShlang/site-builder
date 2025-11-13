<template>
  <div class="min-h-screen bg-gradient-to-b from-gray-50 to-pink-50 py-10">
    <div class="max-w-4xl mx-auto px-4">
      <!-- Hero Banner -->
      <div class="text-center mb-10">
        <div class="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-gradient-to-br from-purple-100 to-pink-100 mb-5">
          <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8 text-purple-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
          </svg>
        </div>
        <h1 class="text-3xl md:text-4xl font-bold bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent mb-3">
          Ваш профиль
        </h1>
        <p class="text-gray-600 max-w-xl mx-auto">
          Управляйте своей учётной записью
        </p>
      </div>

      <!-- Card -->
      <div class="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden transition-all duration-300 hover:shadow-xl">
        <!-- Header -->
        <div class="px-6 py-5 bg-gradient-to-r from-purple-50 to-pink-50 border-b border-gray-100">
          <h2 class="text-xl font-semibold text-gray-800">Настройки аккаунта</h2>
          <p class="text-sm text-gray-500 mt-1">
            Обновите ваш email
          </p>
        </div>

        <!-- Form -->
        <div class="p-6 md:p-8">
          <form @submit.prevent="handleSubmit" class="space-y-7">
            <!-- Email Section -->
            <div class="space-y-2">
              <label class="block text-sm font-medium text-gray-700" for="email">
                Адрес электронной почты
              </label>
              <div class="relative">
                <input
                  id="email"
                  v-model="form.email"
                  type="email"
                  :disabled="isSubmitting"
                  class="w-full px-5 py-3.5 text-sm border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent placeholder:text-gray-400 transition-all"
                  placeholder="you@example.com"
                  @input="clearError('email')"
                />
                <div v-if="form.email && !formErrors.email" class="absolute inset-y-0 right-4 flex items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-green-500" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
                  </svg>
                </div>
              </div>
              <p v-if="formErrors.email" class="mt-1 text-sm text-red-600 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"/></svg>
                {{ formErrors.email }}
              </p>
            </div>

            <!-- Login Info (Read-only) -->
            <div class="space-y-2 pt-2">
              <label class="block text-sm font-medium text-gray-700">
                Логин
              </label>
              <div class="px-5 py-3.5 text-sm bg-gray-50 rounded-xl border border-gray-200 text-gray-500 cursor-not-allowed">
                {{ form.login }}
              </div>
            </div>

            <!-- Actions -->
            <div class="flex flex-wrap gap-4 pt-2">
              <button
                type="submit"
                :disabled="isSubmitting || !hasChanges"
                class="px-6 py-3 bg-gradient-to-r from-purple-600 to-pink-600 hover:from-purple-700 hover:to-pink-700 disabled:from-gray-400 disabled:to-gray-400 text-white font-medium rounded-xl shadow-md hover:shadow-lg transition-all transform hover:-translate-y-0.5 flex items-center"
              >
                <span v-if="isSubmitting" class="flex items-center">
                  <svg class="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Сохранение...
                </span>
                <span v-else>
                  Сохранить изменения
                </span>
              </button>

              <button
                type="button"
                :disabled="isSubmitting || !hasChanges"
                @click="resetForm"
                class="px-6 py-3 border border-gray-300 hover:bg-gray-50 disabled:text-gray-400 disabled:border-gray-200 text-gray-700 font-medium rounded-xl transition-colors"
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

// Fetch user data from backend on mount
onMounted(async () => {
  try {
    // Замените этот вызов на реальный запрос к вашему API
    const userData: User = await fetchUserProfile() // ← Ваша функция API
    form.value = { ...userData }
    originalForm.value = { ...userData }
  } catch (error) {
    console.error('Не удалось загрузить профиль:', error)
    // Можно показать toast или уведомление
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
    // Отправка email на бэкенд
    await updateProfile({ email: form.value.email }) // ← Ваша функция API
    // Обновляем оригинальные данные после успешного сохранения
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
// Удалите или замените на реальные вызовы API

async function fetchUserProfile(): Promise<User> {
  // Пример: GET /api/user/profile
  return new Promise((resolve) => {
    setTimeout(() => resolve({ email: 'user@example.com', login: 'user123' }), 300)
  })
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
async function updateProfile(_data: { email: string }): Promise<void> {
  // Пример: PATCH /api/user/profile
  return new Promise((resolve) => {
    setTimeout(resolve, 500) // имитация задержки
  })
}
</script>