<!-- src/views/AIBuilderView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] py-12 px-4 overflow-hidden relative">
    <!-- Фоновые декоративные элементы -->
    <div class="absolute top-1/4 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"></div>
    <div class="absolute bottom-1/3 right-1/5 w-40 h-40 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"></div>
    <div class="absolute top-1/3 right-1/4 w-16 h-16 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"></div>

    <!-- Белая центральная карточка (как на страницах входа/регистрации) -->
    <div class="max-w-2xl mx-auto w-full animate-fade-in">
      <div class="bg-white rounded-2xl shadow-xl border border-white/50 overflow-hidden">
        <!-- Заголовок ВНУТРИ карточки -->
        <div class="text-center py-8 px-6 border-b border-gray-100">
          <div class="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-gradient-to-br from-[#e6dfff] via-[#fde8ff] to-[#ffe6f6] mb-5 mx-auto shadow-[0_6px_16px_rgba(150,100,200,0.12)] ring-1 ring-white/40 border border-white/50">
            <svg xmlns="http://www.w3.org/2000/svg" class="w-7 h-7 text-[#d966ff]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
            </svg>
          </div>
          <h1 class="text-2xl md:text-3xl font-semibold text-gray-800 mb-2">
            Создайте лендинг с помощью ИИ
          </h1>
          <p class="text-gray-600 max-w-md mx-auto text-sm md:text-base">
            Опишите словами, какой лендинг вы хотите — и нейросеть создаст его за секунды.
          </p>
        </div>

        <!-- Основное содержимое -->
        <div class="p-6">
          <!-- AI Header -->
          <div class="flex items-center gap-3 mb-5 p-4 bg-gray-50 rounded-xl border border-gray-100">
            <div class="w-10 h-10 rounded-xl bg-gradient-to-r from-[#6a5af9] to-[#d966ff] flex items-center justify-center flex-shrink-0">
              <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
              </svg>
            </div>
            <div>
              <h2 class="font-semibold text-gray-800">AI Конструктор</h2>
              <p class="text-xs text-gray-500">LLaMA-3-8B-Instruct</p>
            </div>
          </div>

          <!-- Инструкция -->
          <div class="mb-5 p-3 bg-purple-50 rounded-lg border border-purple-100">
            <p class="text-sm text-purple-700">
              🔹 Опишите идею целиком<br />
              🔹 Например: <span class="font-medium">«Лендинг для онлайн-курса по йоге»</span>
            </p>
          </div>

          <!-- Поле ввода -->
          <textarea
            v-model="aiPrompt"
            @keydown.enter.exact.prevent="handleSubmit"
            placeholder="Например: создай лендинг для стартапа по доставке еды — с таймером, формой заявки и ярким градиентом..."
            class="w-full h-32 px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-[#6a5af9] focus:border-[#6a5af9] resize-none mb-5"
          ></textarea>

          <!-- Кнопка -->
          <button
            @click="handleSubmit"
            :disabled="!aiPrompt.trim() || aiProcessing"
            class="w-full px-5 py-3.5 bg-gradient-to-r from-[#6a5af9] via-[#8a6bff] to-[#a66bff] text-white font-medium rounded-xl shadow-md hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5 flex items-center justify-center disabled:opacity-60 disabled:cursor-not-allowed"
          >
            <svg v-if="!aiProcessing" xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />
            </svg>
            <svg v-else class="animate-spin w-4 h-4 mr-2 text-white" fill="currentColor" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
            </svg>
            {{ aiProcessing ? 'Генерация лендинга...' : 'Создать с ИИ' }}
          </button>

          <!-- Примеры -->
          <div class="mt-6">
            <p class="text-xs font-medium text-gray-600 mb-2">Идеи для запроса:</p>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="example in aiExamples"
                :key="example"
                @click="aiPrompt = example"
                class="px-3 py-1.5 bg-gray-100 hover:bg-gray-200 text-gray-700 text-xs rounded-full transition-colors"
              >
                {{ example }}
              </button>
            </div>
          </div>

          <!-- Ссылка назад -->
          <div class="mt-8 pt-6 border-t border-gray-100 text-center">
            <router-link
              to="/templates"
              class="inline-flex items-center text-sm text-gray-600 hover:text-[#6a5af9] font-medium group"
            >
              <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-1 group-hover:-translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
              Вернуться к шаблонам
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@keyframes fade-in {
  from { opacity: 0; transform: translateY(16px) scale(0.98); }
  to { opacity: 1; transform: translateY(0) scale(1); }
}

@keyframes float {
  0%, 100% { transform: translateY(0px) rotate(0deg); }
  50% { transform: translateY(-12px) rotate(2deg); }
}

@keyframes pulse-slow {
  0%, 100% { opacity: 0.3; transform: scale(1); }
  50% { opacity: 0.5; transform: scale(1.05); }
}

.animate-fade-in {
  animation: fade-in 0.6s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
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

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/api/client'

const router = useRouter()
const aiPrompt = ref('')
const aiProcessing = ref(false)

const aiExamples = [
  'Создай продающий лендинг для онлайн-курса по йоге — с таймером, отзывами и формой заявки',
  'Лендинг для кофейни в центре города: меню, карта, Instagram, кнопка "Заказать доставку"',
  'Минималистичный лендинг SaaS-стартапа: заголовок, 3 фичи, CTA-кнопка и футер',
  'Лендинг для мастера маникюра: портфолио, прайс, Instagram, запись по WhatsApp',
]

const handleSubmit = async () => {
  if (!aiPrompt.value.trim()) return

  aiProcessing.value = true
  try {
    const { data } = await api.post('/ai/generate-full', {
      prompt: aiPrompt.value.trim(),
      model: 'llama-3-8b-instruct',
    })

    if (data.projectId) {
      router.push(`/editor/${data.projectId}`)
    } else if (data.jsonModel) {
      const createRes = await api.post('/projects', {
        name: data.name || 'Лендинг от ИИ',
        jsonModel: data.jsonModel,
        framesCount: 1,
      })
      router.push(`/editor/${createRes.data.id}`)
    } else {
      throw new Error('Некорректный ответ от AI')
    }
  } catch (e) {
    console.error('Ошибка AI-генерации:', e)
    alert('❌ Не удалось создать лендинг. Попробуйте уточнить запрос или повторить позже.')
  } finally {
    aiProcessing.value = false
  }
}
</script>