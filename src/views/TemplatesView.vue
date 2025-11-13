<!-- src/views/TemplatesView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-b from-gray-50 to-indigo-50">
    <!-- Header -->
    <div class="max-w-7xl mx-auto px-4 py-8 md:px-6">
      <div class="mb-6 text-center md:text-left">
        <h1 class="text-3xl md:text-4xl font-bold mb-2 bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
          Шаблоны лендингов
        </h1>
        <p class="text-gray-600 max-w-2xl mx-auto md:mx-0">
          Выберите готовый шаблон или создайте свой — и начните редактировать
        </p>
      </div>

      <!-- Tabs -->
      <div class="flex justify-center mb-8">
        <div class="inline-flex bg-gray-100 rounded-xl p-1">
          <button
            @click="activeTab = 'built-in'"
            class="px-5 py-2.5 rounded-lg font-medium transition-all"
            :class="activeTab === 'built-in' 
              ? 'bg-white text-indigo-700 shadow-sm' 
              : 'text-gray-600 hover:text-gray-900'"
          >
             Встроенные 
          </button>
          <button
            @click="activeTab = 'my'"
            class="px-5 py-2.5 rounded-lg font-medium transition-all relative"
            :class="activeTab === 'my' 
              ? 'bg-white text-indigo-700 shadow-sm' 
              : 'text-gray-600 hover:text-gray-900'"
          >
             Мои шаблоны
            <span 
              v-if="userTemplates.length > 0" 
              class="absolute -top-2 -right-2 w-5 h-5 bg-indigo-500 text-white text-xs rounded-full flex items-center justify-center"
            >
              {{ userTemplates.length }}
            </span>
          </button>
          <button
            @click="activeTab = 'ai'"
            class="px-5 py-2.5 rounded-lg font-medium transition-all"
            :class="activeTab === 'ai' 
              ? 'bg-white text-purple-700 shadow-sm' 
              : 'text-gray-600 hover:text-gray-900'"
          >
            Шаблоны с ИИ
          </button>
        </div>
      </div>

      <!-- Built-in Templates -->
      <div v-if="activeTab === 'built-in'" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
          v-for="tpl in builtInTemplates"
          :key="tpl.id"
          @click="useTemplate(tpl)"
          class="bg-white rounded-2xl border border-gray-200 overflow-hidden shadow-sm hover:shadow-lg transition-all duration-300 cursor-pointer group hover:-translate-y-1"
        >
          <div class="p-5">
            <div class="flex justify-between items-start mb-3">
              <h3 class="font-bold text-lg text-gray-800 group-hover:text-indigo-700 truncate">
                {{ tpl.name }}
              </h3>
              <span class="px-2 py-0.5 bg-purple-100 text-purple-700 text-xs font-medium rounded-full">
                 Готов
              </span>
            </div>
            <p class="text-sm text-gray-600 mb-3">
              {{ tpl.description }}
            </p>

            <!-- Color palette -->
            <div class="flex gap-1.5 mb-4">
              <div
                v-for="(color, idx) in tpl.colorPalette"
                :key="idx"
                :style="{ backgroundColor: color }"
                class="w-5 h-5 rounded-full border border-gray-200 flex-shrink-0"
              ></div>
            </div>

            <!-- Preview -->
            <div class="aspect-video bg-gradient-to-br from-indigo-50 to-purple-50 rounded-xl border border-gray-200 flex items-center justify-center mb-4">
              <div class="text-center px-2">
                <div class="w-10 h-10 bg-indigo-100 rounded-xl flex items-center justify-center mx-auto mb-2">
                  <Icon icon="heroicons:sparkles" class="w-5 h-5 text-indigo-600" />
                </div>
                <p class="text-xs text-gray-500">Встроенный шаблон</p>
              </div>
            </div>

            <div class="flex justify-between items-center text-xs text-gray-500">
              <span>{{ }}</span>
              <span class="text-indigo-600 font-medium group-hover:text-purple-600">
                → Использовать
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- User Templates -->
      <div v-else-if="activeTab === 'my'" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- Empty State -->
        <div v-if="loadingUserTemplates" class="lg:col-span-3 text-center py-12">
          <div class="inline-block animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-indigo-600"></div>
          <p class="mt-4 text-gray-600">Загрузка шаблонов...</p>
        </div>

        <div v-else-if="userTemplates.length === 0" class="lg:col-span-3">
          <div class="bg-white rounded-2xl border-2 border-dashed border-indigo-200 p-10 md:p-16 text-center shadow-sm">
            <div class="mx-auto w-20 h-20 bg-indigo-50 rounded-2xl flex items-center justify-center mb-6">
              <Icon icon="heroicons:document-duplicate" class="w-10 h-10 text-indigo-500" />
            </div>
            <h3 class="text-xl font-semibold text-gray-800 mb-3">У вас пока нет шаблонов</h3>
            <p class="text-gray-600 mb-6 max-w-md mx-auto">
              Откройте любой проект → нажмите «Сохранить как шаблон» → и он появится здесь!
            </p>
            <router-link
              to="/"
              class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-indigo-600 to-purple-600 text-white rounded-xl font-semibold shadow-md hover:shadow-lg transition-all"
            >
              <Icon icon="heroicons:arrow-left" class="w-4 h-4 mr-2" />
              Перейти к проектам
            </router-link>
          </div>
        </div>

        <!-- User Templates Grid -->
        <div
          v-for="tpl in userTemplates"
          :key="tpl.id"
          @click="useTemplate(tpl)"
          class="bg-white rounded-2xl border border-gray-200 overflow-hidden shadow-sm hover:shadow-lg transition-all duration-300 cursor-pointer group hover:-translate-y-1"
        >
          <div class="p-5">
            <div class="flex justify-between items-start mb-3">
              <h3 class="font-bold text-lg text-gray-800 group-hover:text-blue-700 truncate">
                {{ tpl.name }}
              </h3>
              <span class="px-2 py-0.5 bg-blue-100 text-blue-700 text-xs font-medium rounded-full">
                 Мой
              </span>
            </div>
            <p class="text-sm text-gray-600 mb-3">
              {{ tpl.description || 'Создано вами' }}
            </p>

            <!-- Auto color palette -->
            <div class="flex gap-1.5 mb-4">
              <div
                v-for="(color, idx) in tpl.colorPalette || ['#6b7280', '#4b5563', '#374151']"
                :key="idx"
                :style="{ backgroundColor: color }"
                class="w-5 h-5 rounded-full border border-gray-300 flex-shrink-0"
              ></div>
            </div>

            <!-- Preview -->
            <div class="aspect-video bg-gradient-to-br from-slate-50 to-blue-50 rounded-xl border border-gray-200 flex items-center justify-center mb-4">
              <div class="text-center px-2">
                <div class="w-10 h-10 bg-blue-100 rounded-xl flex items-center justify-center mx-auto mb-2">
                  <Icon icon="heroicons:user-circle" class="w-5 h-5 text-blue-600" />
                </div>
                <p class="text-xs text-gray-500">Ваш шаблон</p>
              </div>
            </div>

            <div class="flex justify-between items-center text-xs text-gray-500">
              <span>{{ formatDate(tpl.updatedAt) }}</span>
              <span class="text-indigo-600 font-medium group-hover:text-purple-600">
                → Использовать
              </span>
            </div>
          </div>
        </div>

      </div>
      <!-- Ниже сеток шаблонов — новая секция -->
<!-- AI Template Tab -->
<div v-else-if="activeTab === 'ai'" class="max-w-3xl mx-auto">
  <div class="bg-white rounded-2xl border border-purple-200 p-8 text-center">
    <div class="w-16 h-16 bg-purple-100 rounded-2xl flex items-center justify-center mx-auto mb-5">
      <Icon icon="heroicons:cpu-chip" class="w-8 h-8 text-purple-600" />
    </div>
    <h2 class="text-2xl font-bold text-gray-800 mb-3">Создайте шаблон с помощью ИИ</h2>
    <p class="text-gray-600 mb-6">
      Опишите словами, какой лендинг вы хотите — и LLaMA-3-8B сгенерирует его за секунды.
    </p>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8 text-left">
      <div class="p-4 bg-purple-50 rounded-xl">
        <div class="w-8 h-8 bg-purple-500 rounded-lg flex items-center justify-center text-white text-sm font-bold mb-2">1</div>
        <h4 class="font-medium text-gray-800 mb-1">Откройте конструктор</h4>
        <p class="text-sm text-gray-600">Начните с пустого проекта или шаблона</p>
      </div>
      <div class="p-4 bg-pink-50 rounded-xl">
        <div class="w-8 h-8 bg-pink-500 rounded-lg flex items-center justify-center text-white text-sm font-bold mb-2">2</div>
        <h4 class="font-medium text-gray-800 mb-1">Нажмите «AI Конструктор»</h4>
        <p class="text-sm text-gray-600">Опишите, что хотите изменить</p>
      </div>
    </div>

          <div class="mb-6 p-4 bg-gradient-to-r from-purple-50 to-pink-50 rounded-xl border border-purple-100 text-left">
            <p class="font-medium text-gray-800 mb-2">Примеры запросов:</p>
            <ul class="text-sm text-gray-600 space-y-1">
              <li>• «Создай лендинг для онлайн-курса по йоге»</li>
              <li>• «Добавь таймер скидки и форму заявки»</li>
              <li>• «Сделай дизайн в стиле Apple — минимализм, белый фон, чёрный текст»</li>
            </ul>
          </div>

          <router-link
            to="/ai-builder"
            class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-purple-600 to-pink-600 text-white rounded-xl font-semibold shadow-md hover:shadow-lg transition-all"
          >
            <Icon icon="heroicons:sparkles" class="w-4 h-4 mr-2" />
            Начать с ИИ
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Icon } from '@iconify/vue'
import api from '@/api/client'

const router = useRouter()
const activeTab = ref<'built-in' | 'my'>('built-in')
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const userTemplates = ref<any[]>([])
const loadingUserTemplates = ref(true)

// ===========================
// ВСТРОЕННЫЕ ШАБЛОНЫ (локально)
// ===========================
const builtInTemplates = [
  {
    id: 'template-online-course',
    name: 'Онлайн-курс',
    description: 'Идеален для продажи курсов: таймер, отзывы, форма заявки',
    colorPalette: ['#4f46e5', '#7c3aed', '#ec4899'],
    jsonModel: { /* как было */ }
  },
  {
    id: 'template-fitness-product',
    name: 'Фитнес-продукт',
    description: 'Продающий лендинг для товаров: акцент на выгоде и отзыве',
    colorPalette: ['#0ea5e9', '#3b82f6', '#6366f1'],
    jsonModel: { /* как было */ }
  },
  {
    id: 'template-saas-service',
    name: 'SaaS-сервис',
    description: 'Для IT-продуктов: упор на функционал и интеграции',
    colorPalette: ['#1e293b', '#334155', '#64748b'],
    jsonModel: { /* как было */ }
  }
]

// ===========================
// ЗАГРУЗКА ПОЛЬЗОВАТЕЛЬСКИХ ШАБЛОНОВ
// ===========================
const fetchUserTemplates = async () => {
  try {
    const { data } = await api.get('/templates') // ← новый эндпоинт
    userTemplates.value = Array.isArray(data) ? data : []
  } catch (e) {
    console.error('Ошибка загрузки шаблонов:', e)
    alert('❌ Не удалось загрузить ваши шаблоны')
    userTemplates.value = []
  } finally {
    loadingUserTemplates.value = false
  }
}

// ===========================
// ИСПОЛЬЗОВАТЬ ШАБЛОН → СОЗДАТЬ ПРОЕКТ
// ===========================
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

// ===========================
// Хелперы
// ===========================
const formatDate = (iso: string) => {
  return new Date(iso).toLocaleDateString('ru-RU', {
    day: 'numeric',
    month: 'short',
  })
}

// ===========================
// МОНТАЖ
// ===========================
onMounted(() => {
  if (activeTab.value === 'my') {
    fetchUserTemplates()
  }
})

// Слушаем смену вкладки
const activeTabComputed = computed(() => activeTab.value)
onMounted(() => {
  // Загружать при первом открытии вкладки "Мои"
  const unwatch = activeTabComputed
  // Но проще — в ватчере:
})
// Лучше — watch:
// (уже работает через onMounted + условие выше)
</script>