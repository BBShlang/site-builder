<!-- src/views/EditorView.vue -->
<template>
  <div
    class="min-h-screen w-full bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] overflow-hidden relative"
  >
    <!-- Фоновые декоративные элементы -->
    <div
      class="absolute top-1/5 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"
    ></div>
    <div
      class="absolute bottom-1/4 right-1/5 w-40 h-40 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"
    ></div>
    <div
      class="absolute top-1/3 right-1/4 w-20 h-20 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"
    ></div>

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
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="w-5 h-5 text-white"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V5a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"
              />
            </svg>
          </div>

          <!-- === Rename Project === -->
          <div class="flex items-center gap-2">
            <div v-if="!isEditingName" class="flex items-center gap-2">
              <h1
                class="text-xl sm:text-2xl font-bold text-gray-800 cursor-pointer hover:text-[#6a5af9] transition"
                @click="startEditName"
              >
                {{ projectName || 'Новый проект' }}
              </h1>

              <button
                @click="startEditName"
                class="text-gray-500 hover:text-[#6a5af9] transition"
                title="Переименовать"
              >
              </button>
            </div>

            <input
              v-else
              v-model="editedProjectName"
              @keyup.enter="finishEditName"
              @blur="finishEditName"
              type="text"
              class="px-3 py-1.5 border border-gray-300 rounded-lg focus:ring-2 
                     focus:ring-[#6a5af9] outline-none text-lg font-medium"
              style="min-width: 240px"
            />
          </div>
        </div>

        <div ref="actionsRef" class="flex flex-wrap gap-3">
          <button
            @click="openSaveAsTemplateModal"
            class="px-4 py-2.5 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white text-sm font-medium rounded-xl hover:shadow-md transition-all flex items-center group"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none"
                 viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
            Сохранить как шаблон
          </button>

          <button
            ref="exportBtnRef"
            @click="exportToJson"
            class="px-4 py-2.5 bg-gradient-to-r from-[#a66bff] to-[#d966ff] text-white text-sm font-medium rounded-xl hover:shadow-md transition-all flex items-center group"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none"
                 viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M9 19l3 3m0 0l3-3m-3 3V10" />
            </svg>
            Опубликовать
          </button>

          <button
            ref="saveBtnRef"
            @click="saveProject"
            :disabled="!grapesData || saving"
            class="px-4 py-2.5 bg-gray-800 text-white text-sm font-medium rounded-xl hover:bg-gray-900 hover:shadow-md transition-all flex items-center disabled:opacity-60 disabled:cursor-not-allowed"
          >
            <svg v-if="!saving" xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2"
                 fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M5 13l4 4L19 7" />
            </svg>
            <span v-else class="flex items-center">
              <svg class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                   xmlns="http://www.w3.org/2000/svg" fill="none"
                   viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10"
                        stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor"
                      d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 
                         0 12h4zm2 5.291A7.962 7.962 0 014 
                         12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z">
                </path>
              </svg>
            </span>
            {{ saving ? 'Сохранение...' : 'Сохранить' }}
          </button>

          <router-link
            ref="backBtnRef"
            to="/"
            class="px-4 py-2.5 bg-white/70 backdrop-blur-sm text-gray-700 border border-white/40 text-sm font-medium rounded-xl hover:bg-white/90 hover:shadow-sm transition-all flex items-center"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none"
                 viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M15 19l-7-7 7-7" />
            </svg>
            Назад
          </router-link>
        </div>
      </div>

      <!-- GrapesJS Editor -->
      <div class="flex-1 w-full bg-white overflow-hidden">
        <GrapesEditor v-model="grapesData" @ready="onEditorReady" ref="grapesEditorRef" />
      </div>
    </div>

    <!-- Onboarding Overlay -->
    <!-- (Оставлено без изменений) -->
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
        :style="{ top: tooltipPosition.top + 'px', left: tooltipPosition.left + 'px' }"
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

          <button
            @click="nextStep"
            class="px-3 py-1.5 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white text-sm font-medium rounded-lg"
          >
            {{ activeStep === steps.length - 1 ? 'Готово' : 'Далее' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Modal: Save as template -->
    <!-- (Оставлено без изменений) -->
    <Teleport to="body">
      <div
        v-if="showTemplateModal"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black bg-opacity-40 backdrop-blur-sm"
      >
        <div
          class="bg-white/90 backdrop-blur-xl rounded-2xl shadow-2xl max-w-md w-full p-6 border border-white/50 transform transition-all"
        >
          <div class="flex justify-between items-start mb-4">
            <h3 class="text-lg font-bold text-gray-800">Сохранить как шаблон</h3>
            <button @click="showTemplateModal = false" class="text-gray-400 hover:text-gray-600">
              ✖
            </button>
          </div>

          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Название шаблона</label>
              <input
                v-model="templateName"
                type="text"
                class="w-full px-4 py-2.5 bg-white/80 border border-white/50 rounded-xl 
                       focus:ring-2 focus:ring-[#6a5af9] focus:border-transparent backdrop-blur-sm"
                placeholder="Например: Мой продающий лендинг"
                maxlength="60"
              />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Описание (опционально)</label>
              <textarea
                v-model="templateDescription"
                rows="2"
                class="w-full px-4 py-2.5 bg-white/80 border border-white/50 rounded-xl 
                       focus:ring-2 focus:ring-[#6a5af9] focus:border-transparent backdrop-blur-sm"
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
              class="px-4 py-2.5 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white font-medium 
                     rounded-xl hover:shadow-md disabled:opacity-60 backdrop-blur-sm"
            >
              <span v-if="!savingTemplate">Сохранить шаблон</span>

              <span v-else class="flex items-center">
                <svg class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                     fill="currentColor" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10"
                          stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor"
                        d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 
                           0 12h4zm2 5.291A7.962 7.962 0 014 
                           12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
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
import { ref, onMounted, nextTick, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Editor } from 'grapesjs'
import {
  fetchProjectById,
  updateProject,
  createProject,
  createTemplate,
  publishProject,
} from '@/api/client'
import GrapesEditor from '../components/GrapesEditor.vue'

// === Rename Project ===
const isEditingName = ref(false)
const editedProjectName = ref('')

const startEditName = () => {
  editedProjectName.value = projectName.value
  isEditingName.value = true

  nextTick(() => {
    const input = document.querySelector('input[type="text"]')
    input?.focus()
  })
}

const finishEditName = () => {
  if (!editedProjectName.value.trim()) {
    isEditingName.value = false
    return
  }

  projectName.value = editedProjectName.value.trim()
  isEditingName.value = false

  saveProject()
}

// === Refs для onboarding ===
const toolbarRef = ref<HTMLElement | null>(null)
const logoRef = ref<HTMLElement | null>(null)
const actionsRef = ref<HTMLElement | null>(null)
const exportBtnRef = ref<HTMLElement | null>(null)
const saveBtnRef = ref<HTMLElement | null>(null)
const backBtnRef = ref<HTMLElement | null>(null)

// === Onboarding system ===
const showOnboarding = ref(false)
const activeStep = ref(-1)
const highlightRect = ref({ top: 0, left: 0, right: 0, bottom: 0 })
const tooltipPosition = ref({ top: 0, left: 0 })

const steps = [
  { target: logoRef, title: 'Конструктор лендингов', description: 'Здесь вы создаёте и редактируете свои сайты.' },
  { target: saveBtnRef, title: 'Сохранение проекта', description: 'Сохраняет проект на сервере.' },
  { target: actionsRef, title: 'Создание шаблонов', description: 'Сохраняйте проект как шаблон.' },
  { target: exportBtnRef, title: 'Публикация', description: 'Экспортируйте проект в JSON.' },
  { target: backBtnRef, title: 'Назад', description: 'Вернуться к списку проектов.' },
]

const updatePositions = () => {
  const step = steps[activeStep.value]
  if (!step?.target?.value) return
  const rect = step.target.value.getBoundingClientRect()

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

  if (tooltipPosition.value.top < 20) tooltipPosition.value.top = rect.bottom + 10
  if (tooltipPosition.value.left < 20) tooltipPosition.value.left = 20
}

const currentStep = computed(() => steps[activeStep.value] || null)

const startOnboarding = () => {
  const seen = localStorage.getItem('editor-onboarding-seen')
  if (!seen) {
    setTimeout(() => {
      showOnboarding.value = true
      activeStep.value = 0
      nextTick(updatePositions)
    }, 600)
  }
}

const nextStep = () => {
  activeStep.value < steps.length - 1
    ? (activeStep.value++, nextTick(updatePositions))
    : finishOnboarding()
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

// === Modal: Save as template ===
const showTemplateModal = ref(false)
const templateName = ref('')
const templateDescription = ref('')
const savingTemplate = ref(false)

const projectName = ref('Новый проект')

const openSaveAsTemplateModal = () => {
  templateName.value = projectName.value
  showTemplateModal.value = true
}

const currentEditor = ref<Editor | null>(null)

const saveAsTemplate = async () => {
  if (!currentEditor.value) return

  savingTemplate.value = true

  try {
    const model = currentEditor.value.getProjectData()

    await createTemplate({
      name: templateName.value.trim(),
      jsonData: JSON.stringify(model),
    })

    alert('✅ Шаблон успешно сохранён!')
    showTemplateModal.value = false
  } catch (e) {
    console.error(e)
    alert('Ошибка сохранения шаблона')
  } finally {
    savingTemplate.value = false
  }
}

// === GRAPESJS ===
const EMPTY_MODEL = {
  components: [],
  styles: [],
  pages: [],
}

const route = useRoute()
const router = useRouter()

const grapesData = ref<any | null>(null)
const saving = ref(false)
const publishing = ref(false)

const isNew = computed(() => route.path === '/editor/new')
const projectId = computed(() => (isNew.value ? null : route.params.id as string))

// === Load project ===
const loadProject = async () => {
  if (!projectId.value) return null

  try {
    const data = await fetchProjectById(projectId.value)
    const model = JSON.parse(data.jsonData || '{}')

    projectName.value = model.name || 'Без названия'

    return model
  } catch (e) {
    console.error(e)
    alert('Ошибка загрузки проекта')
    router.push('/')
    return null
  }
}

const saveProject = async () => {
  if (!currentEditor.value) return

  saving.value = true

  try {
    const baseModel = grapesData.value || EMPTY_MODEL

    const model = {
      ...baseModel,
      name: (projectName.value || 'Без названия').trim(),
      updatedAt: new Date().toISOString(),
    }

    const json = JSON.stringify(model)

    if (isNew.value) {
      const created = await createProject(json)
      router.replace(`/editor/${created.id}`)
      alert('Проект создан!')
    } else if (projectId.value) {
      await updateProject(projectId.value, json)
      alert('Проект сохранён!')
    }
  } catch (e) {
    console.error(e)
    alert('Ошибка сохранения проекта')
  } finally {
    saving.value = false
  }
}

const exportToJson = async () => {
  if (!currentEditor.value || isNew.value || !projectId.value) {
    alert('Сначала сохраните проект')
    return
  }

  try {
    const model = currentEditor.value.getProjectData()

    const payload = JSON.stringify({
      name: projectName.value,
      ...model,
    })

    await publishProject(projectId.value, payload)

    alert('Проект опубликован!')
  } catch (e) {
    console.error(e)
    alert('Ошибка публикации')
  }
}

const onEditorReady = (editor: Editor) => {
  currentEditor.value = editor
}

onMounted(async () => {
  const model = isNew.value ? EMPTY_MODEL : await loadProject()
  grapesData.value = model || EMPTY_MODEL
  startOnboarding()
})

watch(showTemplateModal, (open) => {
  const esc = (e: KeyboardEvent) => {
    if (e.key === 'Escape') showTemplateModal.value = false
  }
  open ? window.addEventListener('keydown', esc) : window.removeEventListener('keydown', esc)
})
</script>
