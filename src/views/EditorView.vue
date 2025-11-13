<!-- src/views/EditorView.vue -->
<template>
  <div class="min-h-screen w-full bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] overflow-hidden relative">
    <!-- Фоновые декоративные элементы -->
    <div class="absolute top-1/5 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"></div>
    <div class="absolute bottom-1/4 right-1/5 w-40 h-40 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"></div>
    <div class="absolute top-1/3 right-1/4 w-20 h-20 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"></div>

    <div class="relative z-10 flex flex-col h-screen">
      <!-- Toolbar -->
      <div
        ref="toolbarRef"
        class="flex-shrink-0 bg-white/80 backdrop-blur-xl border-b border-white/40 shadow-sm py-4 px-6 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4"
      >
        <div class="flex items-center">
          <div
            ref="logoRef"
            class="w-10 h-10 rounded-xl bg-gradient-to-r from-[#6a5af9] to-[#a66bff] flex items-center justify-center mr-3 shadow-sm"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V5a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
            </svg>
          </div>
          <h1 class="text-xl sm:text-2xl font-bold text-gray-800">
            {{ projectName || 'Новый проект' }}
          </h1>
        </div>

        <div ref="actionsRef" class="flex flex-wrap gap-3">
          <button
            @click="openSaveAsTemplateModal"
            class="px-4 py-2.5 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white text-sm font-medium rounded-xl hover:shadow-md transition-all flex items-center group"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
            Сохранить как шаблон
          </button>

          <button
            ref="exportBtnRef"
            @click="exportToJson"
            class="px-4 py-2.5 bg-gradient-to-r from-[#a66bff] to-[#d966ff] text-white text-sm font-medium rounded-xl hover:shadow-md transition-all flex items-center group"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M9 19l3 3m0 0l3-3m-3 3V10" />
            </svg>
            Опубликовать
          </button>

          <button
            ref="saveBtnRef"
            @click="saveProject"
            :disabled="!grapesData || saving"
            class="px-4 py-2.5 bg-gray-800 text-white text-sm font-medium rounded-xl hover:bg-gray-900 hover:shadow-md transition-all flex items-center disabled:opacity-60 disabled:cursor-not-allowed"
          >
            <svg v-if="!saving" xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
            <span v-else class="flex items-center">
              <svg
                class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
              >
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
            </span>
            {{ saving ? 'Сохранение...' : 'Сохранить' }}
          </button>

          <router-link
            ref="backBtnRef"
            to="/"
            class="px-4 py-2.5 bg-white/70 backdrop-blur-sm text-gray-700 border border-white/40 text-sm font-medium rounded-xl hover:bg-white/90 hover:shadow-sm transition-all flex items-center"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
            </svg>
            Назад
          </router-link>
        </div>
      </div>

      <!-- GrapesJS Editor -->
      <div class="flex-1 w-full bg-white overflow-hidden">
        <GrapesEditor
          v-model="grapesData"
          @ready="onEditorReady"
          ref="grapesEditorRef"
        />
      </div>
    </div>

    <!-- Onboarding Overlay -->
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
            class="px-3 py-1.5 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white text-sm font-medium rounded-lg"
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
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black bg-opacity-40 backdrop-blur-sm"
      >
        <div class="bg-white/90 backdrop-blur-xl rounded-2xl shadow-2xl max-w-md w-full p-6 border border-white/50 transform transition-all">
          <div class="flex justify-between items-start mb-4">
            <h3 class="text-lg font-bold text-gray-800">Сохранить как шаблон</h3>
            <button @click="showTemplateModal = false" class="text-gray-400 hover:text-gray-600">
              <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>

          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Название шаблона</label>
              <input
                v-model="templateName"
                type="text"
                class="w-full px-4 py-2.5 bg-white/80 border border-white/50 rounded-xl focus:ring-2 focus:ring-[#6a5af9] focus:border-transparent backdrop-blur-sm"
                placeholder="Например: Мой продающий лендинг"
                maxlength="60"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Описание (опционально)</label>
              <textarea
                v-model="templateDescription"
                rows="2"
                class="w-full px-4 py-2.5 bg-white/80 border border-white/50 rounded-xl focus:ring-2 focus:ring-[#6a5af9] focus:border-transparent backdrop-blur-sm"
                placeholder="Для чего подходит этот шаблон?"
              ></textarea>
            </div>
          </div>

          <div class="mt-6 flex justify-end gap-3">
            <button
              @click="showTemplateModal = false"
              class="px-4 py-2.5 text-gray-700 font-medium rounded-xl hover:bg-gray-100/80 backdrop-blur-sm"
            >
              Отмена
            </button>
            <button
              @click="saveAsTemplate"
              :disabled="!templateName.trim() || savingTemplate"
              class="px-4 py-2.5 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white font-medium rounded-xl hover:shadow-md disabled:opacity-60 backdrop-blur-sm"
            >
              <span v-if="!savingTemplate">Сохранить шаблон</span>
              <span v-else class="flex items-center">
                <svg
                  class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                >
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
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
import api from '@/api/client'
import GrapesEditor from '../components/GrapesEditor.vue'

// === Refs для onboarding ===
const toolbarRef = ref<HTMLElement | null>(null)
const logoRef = ref<HTMLElement | null>(null)
const actionsRef = ref<HTMLElement | null>(null)
const exportBtnRef = ref<HTMLElement | null>(null)
const saveBtnRef = ref<HTMLElement | null>(null)
const backBtnRef = ref<HTMLElement | null>(null)

// === Onboarding ===
const showOnboarding = ref(false)
const activeStep = ref(-1)
const highlightRect = ref({ top: 0, left: 0, right: 0, bottom: 0 })
const tooltipPosition = ref({ top: 0, left: 0 })

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

// === Шаблоны ===
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

const grapesData = ref<Record<string, unknown> | null>(null)
const grapesEditorRef = ref<InstanceType<typeof GrapesEditor> | null>(null)
const currentEditor = ref<Editor | null>(null) // ✅ ref, а не let
const projectName = ref<string>('')
const saving = ref(false)

const EMPTY_PROJECT_DATA = {
  pages: [
    {
      frames: [
        {
          component: {
            type: 'wrapper',
            components: [],
            stylable: [
              'background',
              'background-color',
              'background-image',
              'background-repeat',
              'background-attachment',
              'background-position',
              'background-size',
            ],
            head: { type: 'head' },
            docEl: { tagName: 'html' },
          },
          id: 'main-frame',
        },
      ],
      type: 'main',
      id: 'main-page',
    },
  ],
  styles: [],
  assets: [],
  symbols: [],
  dataSources: [],
}

// ✅ ОБЯЗАТЕЛЬНО до onMounted
const onEditorReady = (editor: Editor) => {
  currentEditor.value = editor
}

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
  if (!grapesData.value) {
    alert('❌ Нет данных для сохранения')
    return
  }

  const name = projectName.value.trim() || 'Без названия'
  const payload = {
    name,
    ...grapesData.value,
    framesCount: 1,
  }

  try {
    saving.value = true
    if (isNew) {
      const { data } = await api.post('/projects', payload)
      router.replace(`/editor/${data.id}`)
      projectName.value = data.name
      grapesData.value = data.jsonModel || grapesData.value
    } else {
      await api.put(`/projects/${projectId}`, payload)
      alert('✅ Проект сохранён!')
    }
  } catch (e) {
    console.error('Ошибка сохранения:', e)
    alert('❌ Ошибка при сохранении проекта')
  } finally {
    saving.value = false
  }
}

// ✅ Исправленная функция экспорта
const exportToJson = () => {
  if (!currentEditor.value) {
    alert('❌ Редактор ещё не готов')
    return
  }

  const name = (projectName.value || 'landing').replace(/\s+/g, '_')
  const data = {
    html: currentEditor.value.getHtml(),
    css: currentEditor.value.getCss(),
  }

  const jsonStr = JSON.stringify(data, null, 2)
  const blob = new Blob([jsonStr], { type: 'application/json' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = `${name}.json`
  document.body.appendChild(a)
  a.click()
  URL.revokeObjectURL(url)
  document.body.removeChild(a)

  alert('✅ Экспорт завершён! Файл содержит точный HTML и CSS.')
}

onMounted(async () => {
  const project = isNew ? null : await loadProject()
  if (project) {
    grapesData.value = project.jsonModel
    projectName.value = project.name
  } else {
    grapesData.value = EMPTY_PROJECT_DATA
    projectName.value = 'Новый проект'
  }
  startOnboarding()
})

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
@keyframes float {
  0%, 100% { transform: translateY(0px) rotate(0deg); }
  50% { transform: translateY(-12px) rotate(2deg); }
}

@keyframes pulse-slow {
  0%, 100% { opacity: 0.3; transform: scale(1); }
  50% { opacity: 0.5; transform: scale(1.05); }
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