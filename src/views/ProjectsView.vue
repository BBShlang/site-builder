<!-- ProjectsView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-b from-gray-50 to-pink-50">
    <!-- Main -->
    <main class="max-w-7xl mx-auto px-4 py-10 md:px-6">
      <!-- Header -->
      <div class="mb-10 text-center md:text-left">
        <h1 class="text-3xl md:text-4xl font-bold mb-3 bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">
          Мои лендинги
        </h1>
        <p class="text-gray-600 max-w-2xl mx-auto md:mx-0">
          Создавайте, редактируйте и публикуйте одностраничные сайты без кода — быстро и красиво
        </p>
      </div>

      <!-- Projects Grid -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- Empty state -->
        <div v-if="projects.length === 0" class="lg:col-span-3">
          <div 
            class="bg-white rounded-2xl border-2 border-dashed border-purple-200 p-10 md:p-16 text-center shadow-sm hover:shadow-md transition-shadow"
          >
            <div class="mx-auto w-20 h-20 bg-purple-50 rounded-2xl flex items-center justify-center mb-6">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 text-purple-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" 
                      d="M9.75 17L9 20l-1 1h8l-1-1-.75-3M3 13h18M5 17h14a2 2 0 002-2V5a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-800 mb-3">У вас пока нет лендингов</h3>
            <p class="text-gray-600 mb-6 max-w-md mx-auto">
              Начните с создания первого проекта — всего за пару минут вы получите готовый сайт
            </p>
            <button 
              @click="createNewProject"
              class="px-6 py-3 bg-gradient-to-r from-purple-600 to-pink-600 text-white rounded-xl font-semibold shadow-md hover:shadow-lg transition-all transform hover:-translate-y-0.5"
            >
              🚀 Создать лендинг
            </button>
          </div>
        </div>

        <!-- Project cards -->
        <div 
          v-for="project in projects" 
          :key="project.id"
          @click="openEditor(project.id)"
          class="bg-white rounded-2xl border border-gray-200 overflow-hidden shadow-sm hover:shadow-lg transition-all duration-300 cursor-pointer group hover:-translate-y-1 relative"
          :class="{ 'border-dashed border-2 border-purple-200': project.isFirst }"
        >
          <!-- Статус-метка -->
          <div 
            v-if="!project.isFirst"
            class="absolute top-4 right-4 flex gap-1"
          >

            <button 
              @click.stop="deleteProject(project.id)" 
              class="p-1.5 bg-white rounded-full shadow-sm text-gray-500 hover:text-red-500 hover:bg-red-50 transition-colors"
              title="Удалить"
            >
              <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd" />
              </svg>
            </button>
          </div>

          <div class="p-5">
            <div class="flex justify-between items-start mb-3">
              <div class="min-w-0">
                <h3 class="font-bold text-lg text-gray-800 group-hover:text-purple-700 truncate">
                  {{ project.name }}
                </h3>
                <span 
                  v-if="project.isFirst" 
                  class="inline-block mt-1 px-2 py-0.5 bg-blue-100 text-blue-700 text-xs font-medium rounded-full"
                >
                  🎯 Начните здесь
                </span>
              </div>
            </div>
            <p class="text-xs text-gray-500 mb-4">
              Обновлено: {{ formatDate(project.updatedAt) }}
            </p>

            <!-- Preview thumbnail -->
            <div class="mb-4 rounded-xl bg-gradient-to-br from-purple-50 to-pink-50 border border-gray-200 w-full h-36 flex items-center justify-center">
              <div v-if="project.isFirst" class="text-center px-4">
                <div class="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center mx-auto mb-2">
                  <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 text-purple-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                  </svg>
                </div>
                <p class="text-sm text-purple-700 font-medium">Нажмите, чтобы начать</p>
              </div>
              <div v-else class="text-center px-4">
                <div class="w-12 h-12 bg-pink-100 rounded-xl flex items-center justify-center mx-auto mb-2">
                  <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 text-pink-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                  </svg>
                </div>
                <p class="text-sm text-gray-500">Превью лендинга</p>
              </div>
            </div>

            <div class="flex justify-between items-center text-xs text-gray-500">
              <span class="flex items-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="w-3.5 h-3.5 mr-1 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4" />
                </svg>
                {{ project.framesCount }} фрейм{{ plural(project.framesCount) }}
              </span>
              <span class="text-purple-600 font-medium group-hover:text-pink-600">
                → Открыть
              </span>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Заглушка для данных проектов
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const projects = ref<any[]>([])

// Загрузка проектов из localStorage
const loadProjects = () => {
  try {
    const saved = localStorage.getItem('landing-projects')
    if (saved) {
      projects.value = JSON.parse(saved)
    }
    
    // Если нет проектов, создаем первый пустой
    if (projects.value.length === 0) {
      createFirstProject()
    }
  } catch (error) {
    console.error('Ошибка загрузки проектов:', error)
    createFirstProject()
  }
}

// Создание первого проекта
const createFirstProject = () => {
  const firstProject = {
    id: 'first-project',
    name: 'Начните создавать',
    updatedAt: new Date().toISOString(),
    framesCount: 1,
    publishedUrl: null,
    isFirst: true // Флаг для первого проекта
  }
  projects.value = [firstProject]
  saveProjects()
}

// Сохранение проектов в localStorage
const saveProjects = () => {
  localStorage.setItem('landing-projects', JSON.stringify(projects.value))
}

const createNewProject = () => {
  const newProject = {
    id: 'proj-' + Date.now(),
    name: 'Новый лендинг ' + (projects.value.length + 1),
    updatedAt: new Date().toISOString(),
    framesCount: 1,
    publishedUrl: null,
    isFirst: false
  }
  projects.value.push(newProject)
  saveProjects()
  router.push(`/editor/${newProject.id}`)
}

const openEditor = (id: string) => {
  const project = projects.value.find(p => p.id === id)
  if (project?.isFirst) {
    createNewProject()
  } else {
    router.push(`/editor/${id}`)
  }
}

const deleteProject = (id: string) => {
  if (confirm('Удалить лендинг? Действие нельзя отменить.')) {
    projects.value = projects.value.filter(p => p.id !== id)
    saveProjects()
    
    // Если удалили все проекты, создаем первый снова
    if (projects.value.length === 0) {
      createFirstProject()
    }
  }
}

// Загружаем проекты при монтировании
onMounted(() => {
  loadProjects()
})

// Хелперы
const formatDate = (iso: string) => {
  return new Date(iso).toLocaleDateString('ru-RU', {
    day: 'numeric',
    month: 'long',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const plural = (n: number) => {
  return n === 1 ? 'а' : n > 1 && n < 5 ? 'ы' : ''
}
</script>