<!-- src/views/TemplatesView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] py-16 px-4 overflow-hidden relative">
    <!-- Фоновые декоративные элементы -->
    <div class="absolute top-1/5 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"></div>
    <div class="absolute bottom-1/4 right-1/5 w-48 h-48 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"></div>
    <div class="absolute top-1/3 right-1/4 w-20 h-20 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"></div>

    <div class="max-w-6xl mx-auto relative z-10">
      <!-- Заголовок -->
      <div class="text-center mb-12 px-4">
        <h1 class="text-3xl font-bold bg-gradient-to-r from-[#6a5af9] via-[#a66bff] to-[#d966ff] bg-clip-text text-transparent mb-2 animate-fade-in">
          Шаблоны лендингов
        </h1>
        <p class="text-gray-600 max-w-2xl mx-auto leading-relaxed animate-fade-in-delay-1 text-sm md:text-base">
          Выберите готовый шаблон — или создайте что-то новое с помощью волшебства ИИ.
        </p>
      </div>

      <!-- Вкладки -->
      <div class="flex justify-center mb-12 animate-fade-in-delay-2">
        <div class="inline-flex bg-white/60 backdrop-blur-sm rounded-2xl p-2 border border-white/30 shadow-sm">
          <button
            @click="activeTab = 'built-in'"
            class="px-6 py-3 rounded-xl font-medium transition-all duration-300 relative"
            :class="activeTab === 'built-in'
              ? 'bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white shadow-md'
              : 'text-gray-700 hover:text-[#6a5af9] hover:bg-white/50'"
          >
            Встроенные
          </button>
          <button
            @click="activeTab = 'my'"
            class="px-6 py-3 rounded-xl font-medium transition-all duration-300 relative"
            :class="activeTab === 'my'
              ? 'bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white shadow-md'
              : 'text-gray-700 hover:text-[#6a5af9] hover:bg-white/50'"
          >
            Мои шаблоны
            <span
              v-if="userTemplates.length > 0"
              class="absolute -top-2 -right-2 w-5 h-5 bg-[#d966ff] text-white text-xs rounded-full flex items-center justify-center animate-pulse-slow"
            >
              {{ userTemplates.length }}
            </span>
          </button>
          <button
            @click="activeTab = 'ai'"
            class="px-6 py-3 rounded-xl font-medium transition-all duration-300 relative"
            :class="activeTab === 'ai'
              ? 'bg-gradient-to-r from-[#a66bff] to-[#d966ff] text-white shadow-md'
              : 'text-gray-700 hover:text-[#d966ff] hover:bg-white/50'"
          >
            С ИИ
          </button>
        </div>
      </div>

      <!-- Built-in Templates -->
      <div v-if="activeTab === 'built-in'" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 animate-fade-in-delay-3">
        <div
          v-for="tpl in builtInTemplates"
          :key="tpl.id"
          @click="useTemplate(tpl)"
          class="bg-white/70 backdrop-blur-sm rounded-2xl border border-white/40 overflow-hidden shadow-lg hover:shadow-xl transition-all duration-400 cursor-pointer group hover:-translate-y-1 hover:scale-[1.01]"
        >
          <div class="p-6">
            <div class="flex justify-between items-start mb-4">
              <h3 class="font-bold text-lg text-gray-800 group-hover:text-[#6a5af9] truncate">
                {{ tpl.name }}
              </h3>
              <span class="px-2.5 py-1 bg-[#e6dfff] text-[#6a5af9] text-xs font-medium rounded-full">
                Готов
              </span>
            </div>
            <p class="text-sm text-gray-600 mb-4">
              {{ tpl.description }}
            </p>

            <!-- Цветовая палитра -->
            <div class="flex gap-2 mb-5">
              <div
                v-for="(color, idx) in tpl.colorPalette"
                :key="idx"
                :style="{ backgroundColor: color }"
                class="w-6 h-6 rounded-full border border-white/50 shadow-sm"
              ></div>
            </div>

            <!-- Preview -->
            <div class="aspect-video bg-gradient-to-br from-indigo-50/70 to-purple-50/70 rounded-xl border border-white/40 flex items-center justify-center mb-4">
              <div class="text-center px-2">
                <div class="w-10 h-10 bg-[#e6dfff] rounded-xl flex items-center justify-center mx-auto mb-2">
                  <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-[#6a5af9]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />
                  </svg>
                </div>
                <p class="text-xs text-gray-500">Встроенный шаблон</p>
              </div>
            </div>

            <div class="flex justify-end">
              <span class="text-sm font-medium text-[#6a5af9] group-hover:text-[#d966ff] transition-colors">
                → Использовать
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- User Templates -->
      <div v-else-if="activeTab === 'my'" class="animate-fade-in-delay-3">
        <div v-if="loadingUserTemplates" class="text-center py-16">
          <div class="inline-block w-8 h-8 border-4 border-[#6a5af9] border-t-transparent rounded-full animate-spin"></div>
          <p class="mt-4 text-gray-600">Загрузка ваших шаблонов...</p>
        </div>

        <div v-else-if="userTemplates.length === 0" class="max-w-2xl mx-auto">
          <div class="bg-white/70 backdrop-blur-sm rounded-2xl border-2 border-dashed border-[#6a5af9]/30 p-12 text-center shadow-lg">
            <div class="mx-auto w-16 h-16 bg-[#e6dfff] rounded-2xl flex items-center justify-center mb-5">
              <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8 text-[#6a5af9]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-800 mb-3">У вас пока нет шаблонов</h3>
            <p class="text-gray-600 mb-6 max-w-md mx-auto">
              Откройте проект → нажмите «Сохранить как шаблон» → и он появится здесь!
            </p>
            <router-link
              to="/"
              class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white font-medium rounded-2xl shadow-md hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5"
            >
              <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
              К проектам
            </router-link>
          </div>
        </div>

        <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          <div
            v-for="tpl in userTemplates"
            :key="tpl.id"
            @click="useTemplate(tpl)"
            class="bg-white/70 backdrop-blur-sm rounded-2xl border border-white/40 overflow-hidden shadow-lg hover:shadow-xl transition-all duration-400 cursor-pointer group hover:-translate-y-1 hover:scale-[1.01]"
          >
            <div class="p-6">
              <div class="flex justify-between items-start mb-4">
                <h3 class="font-bold text-lg text-gray-800 group-hover:text-[#6a5af9] truncate">
                  {{ tpl.name }}
                </h3>
                <span class="px-2.5 py-1 bg-[#e0f2ff] text-[#0ea5e9] text-xs font-medium rounded-full">
                  Мой
                </span>
              </div>
              <p class="text-sm text-gray-600 mb-4">
                {{ tpl.description || 'Создано вами' }}
              </p>

              <div class="flex gap-2 mb-5">
                <div
                  v-for="(color, idx) in tpl.colorPalette || ['#6b7280', '#4b5563', '#374151']"
                  :key="idx"
                  :style="{ backgroundColor: color }"
                  class="w-6 h-6 rounded-full border border-white/50 shadow-sm"
                ></div>
              </div>

              <div class="aspect-video bg-gradient-to-br from-slate-50/70 to-blue-50/70 rounded-xl border border-white/40 flex items-center justify-center mb-4">
                <div class="text-center px-2">
                  <div class="w-10 h-10 bg-[#e0f2ff] rounded-xl flex items-center justify-center mx-auto mb-2">
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-[#0ea5e9]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                    </svg>
                  </div>
                  <p class="text-xs text-gray-500">Ваш шаблон</p>
                </div>
              </div>

              <div class="flex justify-between items-center text-xs text-gray-500">
                <span>{{ formatDate(tpl.updatedAt) }}</span>
                <span class="text-sm font-medium text-[#6a5af9] group-hover:text-[#d966ff]">
                  → Использовать
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- AI Templates -->
      <div v-else-if="activeTab === 'ai'" class="max-w-3xl mx-auto animate-fade-in-delay-3">
        <div class="bg-white/70 backdrop-blur-sm rounded-2xl border border-white/40 p-8 text-center shadow-lg">
          <div class="w-16 h-16 bg-[#fde8ff] rounded-2xl flex items-center justify-center mx-auto mb-5">
            <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8 text-[#d966ff]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
            </svg>
          </div>
          <h2 class="text-2xl font-bold text-gray-800 mb-3">Создайте шаблон с помощью ИИ</h2>
          <p class="text-gray-600 mb-6 max-w-md mx-auto">
            Опишите идею — и ИИ за секунды сгенерирует лендинг под ваш запрос.
          </p>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8 text-left">
            <div class="p-4 bg-[#fde8ff]/60 rounded-xl border border-[#d966ff]/20">
              <div class="w-8 h-8 bg-[#d966ff] rounded-lg flex items-center justify-center text-white text-sm font-bold mb-2">1</div>
              <h4 class="font-medium text-gray-800 mb-1">Откройте конструктор</h4>
              <p class="text-sm text-gray-600">Начните с чистого листа</p>
            </div>
            <div class="p-4 bg-[#e6dfff]/60 rounded-xl border border-[#6a5af9]/20">
              <div class="w-8 h-8 bg-[#6a5af9] rounded-lg flex items-center justify-center text-white text-sm font-bold mb-2">2</div>
              <h4 class="font-medium text-gray-800 mb-1">Нажмите «AI Конструктор»</h4>
              <p class="text-sm text-gray-600">Опишите, что хотите видеть</p>
            </div>
          </div>

          <div class="mb-8 p-4 bg-gradient-to-r from-[#fde8ff]/50 to-[#e6dfff]/50 rounded-xl border border-white/40 text-left">
            <p class="font-medium text-gray-800 mb-2">Примеры запросов:</p>
            <ul class="text-sm text-gray-600 space-y-1">
              <li>• «Создай лендинг для онлайн-курса по йоге»</li>
              <li>• «Добавь таймер скидки и форму заявки»</li>
              <li>• «Сделай дизайн в стиле Apple — минимализм, белый фон»</li>
            </ul>
          </div>

          <router-link
            to="/ai-builder"
            class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-[#a66bff] to-[#d966ff] text-white font-medium rounded-2xl shadow-md hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />
            </svg>
            Начать с ИИ
          </router-link>
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
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
}

.animate-fade-in-delay-1 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.15s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-2 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.3s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-3 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.45s forwards;
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

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/api/client'

const router = useRouter()
const activeTab = ref<'built-in' | 'my' | 'ai'>('built-in')
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const userTemplates = ref<any[]>([])
const loadingUserTemplates = ref(true)

const builtInTemplates = [
  {
    id: 'template-online-course',
    name: 'Онлайн-курс',
    description: 'Идеален для продажи курсов: таймер, отзывы, форма заявки',
    colorPalette: ['#4f46e5', '#7c3aed', '#ec4899'],
    jsonModel: {}
  },
  {
    id: 'template-fitness-product',
    name: 'Фитнес-продукт',
    description: 'Продающий лендинг для товаров: акцент на выгоде и отзыве',
    colorPalette: ['#0ea5e9', '#3b82f6', '#6366f1'],
    jsonModel: {}
  },
  {
    id: 'template-saas-service',
    name: 'SaaS-сервис',
    description: 'Для IT-продуктов: упор на функционал и интеграции',
    colorPalette: ['#1e293b', '#334155', '#64748b'],
    jsonModel: {}
  }
]

const fetchUserTemplates = async () => {
  loadingUserTemplates.value = true
  try {
    const { data } = await api.get('/templates')
    userTemplates.value = Array.isArray(data) ? data : []
  } catch (e) {
    console.error('Ошибка загрузки шаблонов:', e)
    alert('❌ Не удалось загрузить ваши шаблоны')
    userTemplates.value = []
  } finally {
    loadingUserTemplates.value = false
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const useTemplate = async (template: any) => {
  try {
    const response = await api.post('/projects', {
      name: `Копия: ${template.name}`,
      jsonModel: template.jsonModel,
      framesCount: 1,
    })
    router.push(`/editor/${response.data.id}`)
  } catch (error) {
    console.error('Ошибка при создании проекта:', error)
    alert('❌ Не удалось создать проект на основе шаблона')
  }
}

const formatDate = (iso: string) => {
  return new Date(iso).toLocaleDateString('ru-RU', {
    day: 'numeric',
    month: 'short',
  })
}

// Загружаем шаблоны при открытии вкладки "Мои"
watch(activeTab, (newTab) => {
  if (newTab === 'my' && userTemplates.value.length === 0) {
    fetchUserTemplates()
  }
})

onMounted(() => {
  if (activeTab.value === 'my') {
    fetchUserTemplates()
  }
})
</script>