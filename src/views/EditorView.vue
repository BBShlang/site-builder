твоя задача пока проверять на ошибки в логике и тд
у меня есть страница конструктора одностраничных сайтов
<!-- /src/views/EditorView.vue -->
<template>
  <div class="min-h-screen w-full h-full bg-gradient-to-b from-gray-50 to-purple-50 flex flex-col">
    <!-- Toolbar -->
    <div
      class="flex-shrink-0 bg-white border-b border-gray-200 shadow-sm py-4 px-6 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4"
    >
      <div class="flex items-center">
        <div
          class="w-10 h-10 rounded-xl bg-gradient-to-r from-purple-500 to-pink-500 flex items-center justify-center mr-3 shadow-sm"
        >
          <Icon icon="heroicons:puzzle-piece" class="w-5 h-5 text-white" />
        </div>
        <h1 class="text-xl sm:text-2xl font-bold text-gray-800">
          {{ projectName || 'Новый проект' }}
        </h1>
      </div>

      <div class="flex flex-wrap gap-3">
        <button
          @click="exportToJson"
          class="px-4 py-2.5 bg-gradient-to-r from-purple-500 to-pink-500 text-white text-sm font-medium rounded-xl hover:shadow-md transition-all flex items-center"
        >
          <Icon icon="heroicons:arrow-down-tray" class="w-4 h-4 mr-2" />
          Экспорт в JSON
        </button>

        <button
          @click="saveProject"
          class="px-4 py-2.5 bg-gray-800 text-white text-sm font-medium rounded-xl hover:bg-gray-900 hover:shadow-md transition-all flex items-center"
        >
          <Icon icon="heroicons:check" class="w-4 h-4 mr-2" />
          Сохранить
        </button>

        <router-link
          to="/"
          class="px-4 py-2.5 bg-white text-gray-700 border border-gray-300 text-sm font-medium rounded-xl hover:bg-gray-50 hover:shadow-sm transition-all flex items-center"
        >
          <Icon icon="heroicons:arrow-left" class="w-4 h-4 mr-2" />
          Назад
        </router-link>
      </div>
    </div>

    <!-- GrapesJS Editor -->
    <div class="flex-1 w-full h-full overflow-hidden bg-white relative">
      <div ref="editorContainer" class="absolute inset-0"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import grapesjs, { Editor } from 'grapesjs'
import 'grapesjs/dist/css/grapes.min.css'
import { Icon } from '@iconify/vue'

const route = useRoute()
const projectId = route.params.id as string

const editorContainer = ref<HTMLElement | null>(null)
let editor: Editor | null = null

const projectName = ref<string>('')

// Загрузка проекта из localStorage
const loadProject = () => {
  try {
    const savedProjects = localStorage.getItem('landing-projects')
    if (!savedProjects) return null
    const projects = JSON.parse(savedProjects)
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    return projects.find((p: any) => p.id === projectId) || null
  } catch (e) {
    console.error('Ошибка загрузки проекта:', e)
    return null
  }
}

// Сохранение JSON в localStorage + обновление метаданных
const saveProject = () => {
  if (!editor) return
  if (!projectId) return

  const jsonModel = editor.getProjectData() // Это и есть сериализованный JSON GrapesJS

  try {
    const savedProjects = localStorage.getItem('landing-projects')
    const projects = savedProjects ? JSON.parse(savedProjects) : []

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const index = projects.findIndex((p: any) => p.id === projectId)
    if (index !== -1) {
      projects[index] = {
        ...projects[index],
        name: projectName.value || projects[index].name,
        updatedAt: new Date().toISOString(),
        jsonModel, // ← сохраняем JSON-структуру
        framesCount: 1, // упрощаем до 1 фрейма
      }
      localStorage.setItem('landing-projects', JSON.stringify(projects))
      alert('✅ Проект сохранён!')
    }
  } catch (e) {
    console.error('Ошибка сохранения:', e)
    alert('❌ Ошибка при сохранении')
  }
}

// Экспорт JSON в файл (скачивание)
const exportToJson = () => {
  if (!editor) return

  const data = editor.getProjectData() // ← Это и есть JSON! Можете `JSON.stringify(data, null, 2)`
  const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = `${projectName.value || 'landing'}.json`
  a.click()
  URL.revokeObjectURL(url)
}

// Инициализация GrapesJS
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const initGrapesEditor = (initialData?: any) => {
  if (editor) editor.destroy()

  editor = grapesjs.init({
    container: editorContainer.value!,
    fromElement: false,
    height: '100%',
    width: '100%',
    storageManager: false, // отключаем встроенный storage (используем свой)
    styleManager: {
      sectors: [
        {
          name: 'Общие',
          buildProps: ['background-color', 'padding', 'margin'],
          open: false,
        },
        {
          name: 'Текст',
          buildProps: ['font-family', 'font-size', 'color', 'text-align'],
          open: false,
        },
      ],
    },
  })

  const bm = editor.BlockManager
  bm.add('text', {
    label: 'Текст',
    content: '<div class="p-4 text-gray-800">Редактируемый текст</div>',
  })
  bm.add('image', {
    label: 'Изображение',
    content:
      '<img src="https://via.placeholder.com/400x200?text=Фото" class="w-full h-auto rounded-lg" />',
  })
  bm.add('button', {
    label: 'Кнопка',
    content:
      '<button class="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700">Кнопка</button>',
  })

  // Если есть сохранённые данные — загружаем
  if (initialData?.jsonModel) {
    editor.loadProjectData(initialData.jsonModel)
    projectName.value = initialData.name
  } else {
    // Шаблон начальной страницы
    editor.setComponents(`
      <div style="text-align:center; padding:50px;">
        <h1 style="font-size:2.5rem; color:#333;">Добро пожаловать!</h1>
        <p style="font-size:1.2rem; color:#666; margin-top:1rem;">Перетащите элементы из панели слева</p>
      </div>
    `)
    editor.setStyle(`
      body { font-family: -apple-system, BlinkMacSystemFont, sans-serif; background:#f9fafb; }
    `)
  }
}

// Жизненный цикл
onMounted(() => {
  const project = loadProject()
  initGrapesEditor(project)
})

onUnmounted(() => {
  if (editor) editor.destroy()
})
</script>

<style scoped>
/* Единственное, что может понадобиться — убрать padding GrapesJS */
.gjs-cv-canvas {
  padding: 0 !important;
}
</style>
 