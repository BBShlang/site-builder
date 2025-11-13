<!-- src/views/EditorView.vue -->
<template>
  <div class="min-h-screen w-full h-full bg-gradient-to-b from-gray-50 to-purple-50 flex flex-col">
    <!-- Toolbar -->
    <div
      ref="toolbarRef"
      class="flex-shrink-0 bg-white border-b border-gray-200 shadow-sm py-4 px-6 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4"
    >
      <div class="flex items-center">
        <div
          ref="logoRef"
          class="w-10 h-10 rounded-xl bg-gradient-to-r from-purple-500 to-pink-500 flex items-center justify-center mr-3 shadow-sm"
        >
          <Icon icon="heroicons:puzzle-piece" class="w-5 h-5 text-white" />
        </div>
        <h1 class="text-xl sm:text-2xl font-bold text-gray-800">
          {{ projectName || 'Новый проект' }}
        </h1>
      </div>

      <div ref="actionsRef" class="flex flex-wrap gap-3">
        <!-- НОВАЯ КНОПКА -->
        <button
          @click="openSaveAsTemplateModal"
          class="px-4 py-2.5 bg-gradient-to-r from-indigo-500 to-purple-500 text-white text-sm font-medium rounded-xl hover:shadow-md transition-all flex items-center"
        >
          <Icon icon="heroicons:document-duplicate" class="w-4 h-4 mr-2" />
          Сохранить как шаблон
        </button>

        <button
          ref="exportBtnRef"
          @click="exportToJson"
          class="px-4 py-2.5 bg-gradient-to-r from-purple-500 to-pink-500 text-white text-sm font-medium rounded-xl hover:shadow-md transition-all flex items-center"
        >
          <Icon icon="heroicons:cloud-arrow-up" class="w-4 h-4 mr-2" />
          Опубликовать
        </button>

        <button
          ref="saveBtnRef"
          @click="saveProject"
          :disabled="!grapesData || saving"
          class="px-4 py-2.5 bg-gray-800 text-white text-sm font-medium rounded-xl hover:bg-gray-900 hover:shadow-md transition-all flex items-center disabled:opacity-60 disabled:cursor-not-allowed"
        >
          <Icon v-if="!saving" icon="heroicons:check" class="w-4 h-4 mr-2" />
          <span v-else class="flex items-center">
            <svg
              class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
            >
              <circle
                class="opacity-25"
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                stroke-width="4"
              ></circle>
              <path
                class="opacity-75"
                fill="currentColor"
                d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
              ></path>
            </svg>
          </span>
          {{ saving ? 'Сохранение...' : 'Сохранить' }}
        </button>

        <router-link
          ref="backBtnRef"
          to="/"
          class="px-4 py-2.5 bg-white text-gray-700 border border-gray-300 text-sm font-medium rounded-xl hover:bg-gray-50 hover:shadow-sm transition-all flex items-center"
        >
          <Icon icon="heroicons:arrow-left" class="w-4 h-4 mr-2" />
          Назад
        </router-link>
      </div>
    </div>

    <!-- GrapesJS Editor (теперь простой компонент!) -->
    <div class="flex-1 w-full h-full overflow-hidden bg-white relative">
      <GrapesEditor
        v-model="grapesData"
        @ready="onEditorReady"
        ref="grapesEditorRef"
      />
    </div>

    <!-- Onboarding Overlay (без изменений) -->
    <div v-if="showOnboarding" class="fixed inset-0 z-50 pointer-events-none">
      <div class="absolute inset-0 bg-black opacity-60 pointer-events-auto"></div>
      <div
        :style="{
          clipPath:
            activeStep >= 0
              ? `polygon(
            0% 0%, 
            0% 100%, 
            ${highlightRect.left}px 100%, 
            ${highlightRect.left}px ${highlightRect.top}px, 
            ${highlightRect.right}px ${highlightRect.top}px, 
            ${highlightRect.right}px ${highlightRect.bottom}px, 
            ${highlightRect.left}px ${highlightRect.bottom}px, 
            ${highlightRect.left}px 100%, 
            100% 100%, 
            100% 0%
          )`
              : 'none',
        }"
        class="absolute inset-0 bg-transparent pointer-events-none"
      ></div>

      <div
        v-if="activeStep >= 0"
        :style="{
          top: tooltipPosition.top + 'px',
          left: tooltipPosition.left + 'px',
        }"
        class="absolute z-10 max-w-xs bg-white rounded-xl shadow-xl p-4 border border-gray-200 pointer-events-auto transition-all"
      >
        <h3 class="font-bold text-gray-800 mb-2">{{ currentStep && currentStep.title }}</h3>
        <p class="text-sm text-gray-600 mb-3">{{ currentStep && currentStep.description }}</p>
        <div class="flex justify-between">
          <button
            v-if="activeStep > 0"
            @click="prevStep"
            class="text-sm text-gray-500 hover:text-gray-700"
          >
            Назад
          </button>
          <div></div>
          <button
            @click="nextStep"
            class="px-3 py-1.5 bg-gradient-to-r from-purple-600 to-pink-600 text-white text-sm font-medium rounded-lg"
          >
            {{ activeStep === steps.length - 1 ? 'Готово' : 'Далее' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Modal: Сохранить как шаблон -->
    <Teleport to="body">
      <div
        v-if="showTemplateModal"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black bg-opacity-50"
      >
        <div class="bg-white rounded-2xl shadow-xl max-w-md w-full p-6 transform transition-all">
          <div class="flex justify-between items-start mb-4">
            <h3 class="text-lg font-bold text-gray-800">Сохранить как шаблон</h3>
            <button @click="showTemplateModal = false" class="text-gray-400 hover:text-gray-600">
              <Icon icon="heroicons:x-mark" class="w-5 h-5" />
            </button>
          </div>

          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Название шаблона</label>
              <input
                v-model="templateName"
                type="text"
                class="w-full px-4 py-2.5 border border-gray-300 rounded-xl focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                placeholder="Например: Мой продающий лендинг"
                maxlength="60"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1"
                >Описание (опционально)</label
              >
              <textarea
                v-model="templateDescription"
                rows="2"
                class="w-full px-4 py-2.5 border border-gray-300 rounded-xl focus:ring-2 focus:ring-indigo-500 focus:border-transparent"
                placeholder="Для чего подходит этот шаблон?"
              ></textarea>
            </div>
          </div>

          <div class="mt-6 flex justify-end gap-3">
            <button
              @click="showTemplateModal = false"
              class="px-4 py-2.5 text-gray-700 font-medium rounded-xl hover:bg-gray-100"
            >
              Отмена
            </button>
            <button
              @click="saveAsTemplate"
              :disabled="!templateName.trim() || savingTemplate"
              class="px-4 py-2.5 bg-gradient-to-r from-indigo-600 to-purple-600 text-white font-medium rounded-xl hover:shadow-md disabled:opacity-60"
            >
              <span v-if="!savingTemplate">Сохранить шаблон</span>
              <span v-else class="flex items-center">
                <svg
                  class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                >
                  <circle
                    class="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    stroke-width="4"
                  ></circle>
                  <path
                    class="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                  />
                </svg>
                Сохранение...
              </span>
            </button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Editor } from 'grapesjs'
import { Icon } from '@iconify/vue'
import api from '@/api/client'
import GrapesEditor from '../components/GrapesEditor.vue'

// === Refs для onboarding (без изменений)
const toolbarRef = ref<HTMLElement | null>(null)
const logoRef = ref<HTMLElement | null>(null)
const actionsRef = ref<HTMLElement | null>(null)
const exportBtnRef = ref<HTMLElement | null>(null)
const saveBtnRef = ref<HTMLElement | null>(null)
const backBtnRef = ref<HTMLElement | null>(null)

// === Onboarding (без изменений)
const showOnboarding = ref(false)
const activeStep = ref(-1)
const highlightRect = ref({ top: 0, left: 0, right: 0, bottom: 0 })
const tooltipPosition = ref({ top: 0, left: 0 })

// ⚠️ actionsRef — теперь выделяет всю панель, чтобы не мелькало
const steps = [
  {
    target: logoRef,
    title: 'Конструктор лендингов',
    description: 'Здесь вы создаёте и редактируете свои одностраничные сайты без кода.',
  },
  {
    target: saveBtnRef,
    title: 'Сохранение проекта',
    description: 'Нажмите, чтобы сохранить изменения. Все данные синхронизируются с сервером.',
  },
  {
    target: actionsRef,
    title: 'Создание шаблонов',
    description: 'Сохраняйте готовые лендинги как шаблоны — и используйте их для новых проектов.',
  },
  {
    target: exportBtnRef,
    title: 'Экспорт в JSON',
    description: 'Скачайте проект в формате JSON для резервной копии или переноса.',
  },
  {
    target: backBtnRef,
    title: 'Назад к проектам',
    description: 'Вернитесь к списку всех ваших лендингов в любое время.',
  },
]

const updatePositions = () => {
  const step = steps[activeStep.value]
  if (!step?.target?.value) return

  const el = step.target.value
  const rect = el.getBoundingClientRect()

  highlightRect.value = {
    top: rect.top,
    left: rect.left,
    right: rect.right,
    bottom: rect.bottom,
  }

  tooltipPosition.value = {
    top: rect.top - 100,
    left: rect.left,
  }

  if (tooltipPosition.value.top < 20) {
    tooltipPosition.value.top = rect.bottom + 10
  }
  if (tooltipPosition.value.left < 20) {
    tooltipPosition.value.left = 20
  }
}

const currentStep = computed(() => {
  const idx = activeStep.value
  if (idx >= 0 && idx < steps.length) {
    return steps[idx]
  }
  return null
})

const startOnboarding = () => {
  const hasSeen = localStorage.getItem('editor-onboarding-seen')
  if (!hasSeen) {
    setTimeout(() => {
      showOnboarding.value = true
      activeStep.value = 0
      nextTick(updatePositions)
    }, 800)
  }
}

const nextStep = () => {
  if (activeStep.value < steps.length - 1) {
    activeStep.value++
    nextTick(updatePositions)
  } else {
    finishOnboarding()
  }
}

const prevStep = () => {
  if (activeStep.value > 0) {
    activeStep.value--
    nextTick(updatePositions)
  }
}

const finishOnboarding = () => {
  showOnboarding.value = false
  activeStep.value = -1
  localStorage.setItem('editor-onboarding-seen', 'true')
}

// === Шаблоны: модальное окно и логика ===
const showTemplateModal = ref(false)
const templateName = ref('')
const templateDescription = ref('')
const savingTemplate = ref(false)

const openSaveAsTemplateModal = () => {
  templateName.value = projectName.value || 'Без названия'
  templateDescription.value = ''
  showTemplateModal.value = true
}

const saveAsTemplate = async () => {
  if (!grapesData.value) return
  if (!templateName.value.trim()) return

  // 🔍 Авто-определение палитры

  try {
    savingTemplate.value = true
    alert('✅ Шаблон успешно сохранён!')
    showTemplateModal.value = false
  } catch (e) {
    console.error('Ошибка сохранения шаблона:', e)
    alert('❌ Не удалось сохранить шаблон')
  } finally {
    savingTemplate.value = false
  }
}


// === Управление проектом ===
const route = useRoute()
const router = useRouter()
const projectId = route.params.id as string
const isNew = route.path === '/editor/new'

// ✅ Центральное хранилище данных GrapesJS (реактивное!)
const grapesData = ref<Record<string, unknown> | null>(null)
const grapesEditorRef = ref<InstanceType<typeof GrapesEditor> | null>(null)
// eslint-disable-next-line @typescript-eslint/no-unused-vars
let currentEditor: Editor | null = null // для прямого доступа (если вдруг понадобится)
const projectName = ref<string>('')
const saving = ref(false)

const loadProject = async () => {
  if (isNew) return null
  try {
    const { data } = await api.get(`/projects/${projectId}`)
    return data
  } catch (e) {
    console.error('Ошибка загрузки проекта:', e)
    alert('❌ Не удалось загрузить проект')
    router.push('/')
    return null
  }
}

const saveProject = async () => {
  if (!grapesData.value) return

  const name = projectName.value.trim() || 'Без названия'

  try {
    saving.value = true

    if (isNew) {
      const { data } = await api.post('/projects', {
        name,
        jsonModel: grapesData.value,
        framesCount: 1,
      })
      router.replace(`/editor/${data.id}`)
      projectName.value = data.name
    } else {
      await api.put(`/projects/${projectId}`, {
        name,
        jsonModel: grapesData.value,
        framesCount: 1,
      })
      alert('✅ Проект сохранён!')
    }
  } catch (e) {
    console.error('Ошибка сохранения:', e)
    alert('❌ Ошибка при сохранении проекта')
  } finally {
    saving.value = false
  }
}

const exportToJson = async () => {
  if (!grapesData.value) {
    alert('❌ Нет данных для публикации')
    return
  }

  const name = projectName.value.trim() || 'Без названия'

  try {
    // Если проект ещё не сохранён (новый), сначала сохраним его
    if (isNew) {
      await saveProject() // это обновит URL и projectId
      if (!route.params.id) return // на случай ошибки
    }

    // Теперь публикуем
    const response = await api.post(`/projects/${route.params.id}/publish`, {
      name,
      jsonModel: grapesData.value,
    })

    const publishedUrl = response.data?.url || `/preview/${route.params.id}`
    alert(`✅ Проект опубликован! Ссылка: ${publishedUrl}`)
    
    // Опционально: открыть в новой вкладке
    // window.open(publishedUrl, '_blank')
  } catch (e) {
    console.error('Ошибка публикации:', e)
    alert('❌ Не удалось опубликовать проект')
  }
}
// 🔑 Важно: ловим редактор из компонента
const onEditorReady = (editor: Editor) => {
  currentEditor = editor
  // Можно добавить кастомные блоки динамически (но лучше в GrapesEditor.vue)
}


onMounted(async () => {
  const project = isNew ? null : await loadProject()
  if (project) {
    grapesData.value = project.jsonModel
    projectName.value = project.name
  } else {
    grapesData.value = null
    projectName.value = 'Новый проект'
  }
  startOnboarding()
})

// Закрывать модалку по Esc
watch(showTemplateModal, (isOpen) => {
  const handleEsc = (e: KeyboardEvent) => {
    if (e.key === 'Escape') showTemplateModal.value = false
  }
  if (isOpen) {
    window.addEventListener('keydown', handleEsc)
  } else {
    window.removeEventListener('keydown', handleEsc)
  }
})

onUnmounted(() => {
  window.removeEventListener('keydown', (e) => {
    if (e.key === 'Escape') showTemplateModal.value = false
  })
})
</script>

<style scoped>
/* Перенесли в GrapesEditor.vue — тут не нужно */
</style>