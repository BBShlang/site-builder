<!-- src/views/AIBuilderView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-b from-gray-50 to-purple-50 flex flex-col items-center justify-center p-4">
    <div class="w-full max-w-2xl mx-auto">
      <div class="text-center mb-8">
        <div class="w-16 h-16 bg-purple-100 rounded-2xl flex items-center justify-center mx-auto mb-4">
          <Icon icon="heroicons:cpu-chip" class="w-8 h-8 text-purple-600" />
        </div>
        <h1 class="text-3xl font-bold text-gray-800 mb-3">Создайте лендинг с помощью ИИ</h1>
        <p class="text-gray-600 max-w-lg mx-auto">
          Опишите словами, какой лендинг вы хотите — и нейросеть LLaMA-3-8B создаст его за секунды.
        </p>
      </div>

      <!-- AI Panel Embedded (without Teleport, simplified) -->
      <div class="bg-white rounded-2xl shadow-xl overflow-hidden border border-gray-200">
        <!-- Header -->
        <div class="flex items-center justify-between p-5 border-b border-gray-200 bg-gray-50">
          <div class="flex items-center gap-3">
            <div class="w-10 h-10 rounded-xl bg-gradient-to-r from-purple-500 to-pink-500 flex items-center justify-center">
              <Icon icon="heroicons:cpu-chip" class="w-5 h-5 text-white" />
            </div>
            <div>
              <h2 class="font-bold text-gray-800">AI Конструктор</h2>
              <p class="text-xs text-gray-500">LLaMA-3-8B-Instruct</p>
            </div>
          </div>
        </div>

        <!-- Instructions -->
        <div class="px-5 py-4 bg-purple-50 border-b border-purple-100">
          <p class="text-sm text-purple-700">
            🔹 Опишите идею лендинга целиком<br />
            🔹 Например: <span class="font-medium">«Создай продающий лендинг для онлайн-курса по Python для новичков»</span><br />
            🔹 Или: <span class="font-medium">«Лендинг для кофейни в стиле лофт: меню, адрес, кнопка заказа»</span>
          </p>
        </div>

        <!-- Input -->
        <div class="p-5">
          <textarea
            v-model="aiPrompt"
            @keydown.enter.exact.prevent="handleSubmit"
            placeholder="Например: создай лендинг для стартапа по доставке еды — с ярким градиентом, таймером скидки и формой заявки..."
            class="w-full h-32 px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent resize-none"
          ></textarea>

          <div class="mt-4 space-y-3">
            <button
              @click="handleSubmit"
              :disabled="!aiPrompt.trim() || aiProcessing"
              class="w-full px-4 py-3 bg-gradient-to-r from-purple-600 to-pink-600 text-white font-medium rounded-xl hover:shadow-md disabled:opacity-60 flex items-center justify-center"
            >
              <Icon v-if="!aiProcessing" icon="heroicons:sparkles" class="w-4 h-4 mr-2" />
              <svg v-else class="animate-spin w-4 h-4 mr-2 text-white" fill="currentColor" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
              </svg>
              {{ aiProcessing ? 'Генерация лендинга...' : 'Создать с ИИ' }}
            </button>

            <!-- Примеры -->
            <div class="text-xs text-gray-500">
              <p class="font-medium mb-1">Идеи для запроса:</p>
              <div class="flex flex-wrap gap-2">
                <button
                  v-for="example in aiExamples"
                  :key="example"
                  @click="aiPrompt = example"
                  class="px-3 py-1 bg-gray-100 hover:bg-gray-200 rounded-full text-gray-700 text-xs"
                >
                  {{ example }}
                </button>
              </div>
            </div>
          </div>

          <!-- History (not needed here — fresh start every time) -->
        </div>
      </div>

      <div class="mt-6 text-center">
        <router-link to="/templates" class="text-gray-500 hover:text-gray-700 flex items-center justify-center gap-1">
          <Icon icon="heroicons:arrow-left" class="w-4 h-4" />
          Вернуться к шаблонам
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Icon } from '@iconify/vue'
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
    // 🔁 Отправляем пустой проект + промпт на `/ai/generate-full`
    // Новый эндпоинт: генерирует ПОЛНЫЙ лендинг с нуля
    const { data } = await api.post('/ai/generate-full', {
      prompt: aiPrompt.value.trim(),
      model: 'llama-3-8b-instruct',
    })

    // ✅ Ожидаем ответ: { projectId: string } или { jsonModel: ..., name: ... }
    // Допустим, бек возвращает уже созданный проект
    if (data.projectId) {
      // Перенаправляем в редактор
      router.push(`/editor/${data.projectId}`)
    } else if (data.jsonModel) {
      // fallback: создаём сами
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