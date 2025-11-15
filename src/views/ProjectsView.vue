<template>
  <div class="min-h-screen bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] py-16 px-4 overflow-hidden relative">
    <!-- Фоновые декоративные элементы -->
    <div class="absolute top-1/5 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"></div>
    <div class="absolute bottom-1/4 right-1/5 w-48 h-48 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"></div>
    <div class="absolute top-1/3 right-1/4 w-20 h-20 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"></div>

    <div class="max-w-6xl mx-auto relative z-10">
      <!-- Заголовок -->
      <div class="text-center mb-12 px-4 animate-fade-in">
        <h1 class="text-3xl md:text-4xl font-bold bg-gradient-to-r from-[#6a5af9] via-[#a66bff] to-[#d966ff] bg-clip-text text-transparent mb-3">
          Мои лендинги
        </h1>
        <p class="text-gray-600 max-w-2xl mx-auto leading-relaxed text-sm md:text-base animate-fade-in-delay-1">
          Создавайте, редактируйте и публикуйте одностраничные сайты без кода — быстро и красиво
        </p>
      </div>

      <!-- Загрузка -->
      <div v-if="loading" class="text-center py-16 animate-fade-in-delay-2">
        <div class="inline-block w-8 h-8 border-4 border-[#6a5af9] border-t-transparent rounded-full animate-spin"></div>
        <p class="mt-4 text-gray-600">Загрузка проектов...</p>
      </div>

      <!-- Нет проектов -->
      <div v-else-if="projects.length === 0" class="max-w-2xl mx-auto animate-fade-in-delay-2">
        <div class="bg-white/70 backdrop-blur-sm rounded-2xl border-2 border-dashed border-[#6a5af9]/30 p-12 text-center shadow-lg">
          <div class="mx-auto w-16 h-16 bg-[#e6dfff] rounded-2xl flex items-center justify-center mb-5">
            <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8 text-[#6a5af9]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
          </div>
          <h3 class="text-xl font-semibold text-gray-800 mb-3">У вас пока нет лендингов</h3>
          <p class="text-gray-600 mb-6 max-w-md mx-auto">
            Начните с создания первого проекта — всего за пару минут вы получите готовый сайт
          </p>
          <button
            @click="createNewProject"
            class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white font-medium rounded-2xl shadow-md hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
            </svg>
            Создать лендинг
          </button>
        </div>
      </div>

      <!-- Список проектов -->
      <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 animate-fade-in-delay-2">
        <!-- Всегда первый: карточка "Новый лендинг" -->
        <button
          type="button"
          @click="createNewProject"
          class="bg-white/60 backdrop-blur-sm rounded-2xl border-2 border-dashed border-[#6a5af9]/40 py-8 px-4 shadow-md hover:shadow-lg transition-all duration-300 hover:-translate-y-1 hover:bg-white/80 flex flex-col items-center justify-center"
        >
          <div class="w-12 h-12 bg-[#e6dfff] rounded-2xl flex items-center justify-center mb-3">
            <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 text-[#6a5af9]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
            </svg>
          </div>
          <p class="font-semibold text-gray-800 mb-1">Новый лендинг</p>
          <p class="text-xs text-gray-500 text-center">
            Создать новый проект с нуля
          </p>
        </button>

        <!-- Остальные проекты -->
        <div
          v-for="project in projects"
          :key="project.id"
          class="bg-white/70 backdrop-blur-sm rounded-2xl border border-white/40 overflow-hidden shadow-lg hover:shadow-xl transition-all duration-400 group hover:-translate-y-1 hover:scale-[1.01] relative"
        >
          <!-- Удаление -->
          <div class="absolute top-4 right-4 flex gap-1 z-10">
            <button
              @click.stop="deleteProjectById(project.id)"
              class="p-1.5 bg-white/80 rounded-full shadow-sm text-gray-500 hover:text-red-500 hover:bg-red-50/80 transition-all backdrop-blur-sm"
              title="Удалить"
            >
              <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd" />
              </svg>
            </button>
          </div>

          <div class="p-6" @click="openEditor(project.id)">
            <h3 class="font-bold text-lg text-gray-800 group-hover:text-[#6a5af9] truncate">
              {{ project.name }}
            </h3>
            <p class="text-xs text-gray-500 mb-4">
              Обновлено: {{ formatDate(project.updatedAt) }}
            </p>

            <!-- Превью -->
            <div class="aspect-video rounded-xl bg-gradient-to-br from-purple-50/70 to-pink-50/70 border border-white/40 flex items-center justify-center mb-5">
              <div class="text-center px-2">
                <div class="w-10 h-10 bg-[#fde8ff] rounded-xl flex items-center justify-center mx-auto mb-2">
                  <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-[#d966ff]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                  </svg>
                </div>
                <p class="text-xs text-gray-500">Превью лендинга</p>
              </div>
            </div>

            <div class="flex justify-between items-center text-xs text-gray-500">

              <span class="text-sm font-medium text-[#6a5af9] group-hover:text-[#d966ff]">
                → Открыть
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { fetchProjects, deleteProject } from '@/api/client'

const router = useRouter()

interface ProjectView {
  id: string
  name: string
  updatedAt: string | null
  pagesCount: number
  framesCount: number
}

const projects = ref<ProjectView[]>([])
const loading = ref(true)

const formatDate = (iso: string | null) => {
  if (!iso) return 'неизвестно'
  return new Date(iso).toLocaleDateString('ru-RU', {
    day: 'numeric',
    month: 'long',
  })
}

// Правильное окончание для фреймов
const plural = (n: number) => {
  if (n % 10 === 1 && n % 100 !== 11) return ''
  if ([2, 3, 4].includes(n % 10) && ![12, 13, 14].includes(n % 100)) return 'а'
  return 'ов'
}

const loadProjects = async () => {
  loading.value = true
  try {
    const list = await fetchProjects()

    projects.value = list.map((p: any) => {
      let json: any = {}
      try {
        json = JSON.parse(p.jsonData || '{}')
      } catch (e) {
        console.error('Ошибка парсинга jsonData', e)
      }

      return {
        id: p.id,
        name: json.name || 'Без названия',
        updatedAt: json.updatedAt || null,
        pagesCount: json.pages ? json.pages.length : 1,
        framesCount: json.frames ? json.frames.length : 0,
      }
    })
  } catch (e) {
    console.error(e)
    alert('Ошибка загрузки проектов')
  } finally {
    loading.value = false
  }
}

// 🔹 Теперь создание нового проекта — это просто переход в редактор "new"
const createNewProject = () => {
  router.push('/editor/new')
}

const openEditor = (id: string) => router.push(`/editor/${id}`)

const deleteProjectById = async (id: string) => {
  if (!confirm('Удалить проект?')) return
  try {
    await deleteProject(id)
    projects.value = projects.value.filter((p) => p.id !== id)
  } catch (e) {
    console.error(e)
    alert('Ошибка удаления')
  }
}

onMounted(loadProjects)
</script>
