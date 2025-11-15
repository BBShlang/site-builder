<template>
  <div
    class="min-h-screen bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] py-16 px-4 overflow-hidden relative"
  >
    <!-- Декор -->
    <div class="absolute top-1/5 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl"></div>
    <div class="absolute bottom-1/4 right-1/5 w-48 h-48 rounded-full bg-[#ffd6f0]/30 blur-3xl"></div>
    <div class="absolute top-1/3 right-1/4 w-20 h-20 rounded-full bg-[#c8e6ff]/40 blur-2xl"></div>

    <div class="max-w-6xl mx-auto relative z-10">
      <!-- Заголовок -->
      <div class="text-center mb-12">
        <h1
          class="text-3xl font-bold bg-gradient-to-r from-[#6a5af9] via-[#a66bff] to-[#d966ff] bg-clip-text text-transparent"
        >
          Шаблоны лендингов
        </h1>
        <p class="text-gray-600 max-w-2xl mx-auto mt-2">
          Выберите встроенный шаблон или используйте свои.
        </p>
      </div>

      <!-- Tabs -->
      <div class="flex justify-center mb-12">
        <div class="inline-flex bg-white/60 backdrop-blur-sm rounded-2xl p-2 border border-white/30">
          <button
            @click="activeTab = 'builtin'"
            :class="activeTabClass('builtin')"
            class="px-6 py-3 rounded-xl font-medium"
          >
            Встроенные
          </button>
          <button
            @click="activeTab = 'my'"
            :class="activeTabClass('my')"
            class="px-6 py-3 rounded-xl font-medium relative"
          >
            Мои шаблоны
            <span
              v-if="userTemplates.length"
              class="absolute -top-2 -right-2 w-5 h-5 bg-[#d966ff] text-white text-xs rounded-full flex items-center justify-center"
            >
              {{ userTemplates.length }}
            </span>
          </button>
        </div>
      </div>

      <!-- BUILT-IN -->
      <div v-if="activeTab === 'builtin'" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
        <div
          v-for="tpl in builtInTemplates"
          :key="tpl.id"
          @click="useTemplate(tpl)"
          class="bg-white/70 p-6 rounded-2xl border border-white/40 shadow-lg hover:shadow-xl transition cursor-pointer hover:-translate-y-1"
        >
          <h3 class="font-bold text-lg text-gray-800">{{ tpl.name }}</h3>
          <p class="text-sm text-gray-600 mt-2">{{ tpl.description }}</p>

          <div class="flex gap-2 mt-4">
            <div
              v-for="c in tpl.colorPalette"
              :key="c"
              :style="{ backgroundColor: c }"
              class="w-6 h-6 rounded-full border border-white/50"
            ></div>
          </div>

          <p class="text-xs text-gray-400 mt-2">Встроенный шаблон</p>
        </div>
      </div>

      <!-- MY TEMPLATES -->
      <div v-else class="animate-fade-in-delay-3">
        <div v-if="loadingUserTemplates" class="text-center py-10 text-gray-500">Загрузка...</div>

        <div v-else-if="!userTemplates.length" class="text-center text-gray-500 py-12">
          У вас пока нет шаблонов
        </div>

        <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          <div
            v-for="tpl in userTemplates"
            :key="tpl.id"
            @click="useTemplate(tpl)"
            class="bg-white/70 p-6 rounded-2xl border border-white/40 shadow-lg hover:shadow-xl transition cursor-pointer hover:-translate-y-1"
          >
            <h3 class="font-bold text-lg">{{ tpl.name }}</h3>
            <p class="text-xs text-gray-400 mt-2">{{ formatDate(tpl.updatedAt) }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import builtInTemplates from '@/builtin-templates'
import { fetchTemplates, createProject } from '@/api/client'

const router = useRouter()

// TAB
const activeTab = ref<'builtin' | 'my'>('builtin')

// USER TEMPLATES
const userTemplates = ref([])
const loadingUserTemplates = ref(false)

const formatDate = (iso: string) =>
  new Date(iso).toLocaleDateString('ru-RU', { month: 'short', day: 'numeric' })

const activeTabClass = (t: string) =>
  activeTab.value === t
    ? 'bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white shadow'
    : 'text-gray-700 hover:text-[#6a5af9]'

// LOAD USER TEMPLATES
const loadUserTemplates = async () => {
  loadingUserTemplates.value = true
  try {
    userTemplates.value = await fetchTemplates()
  } catch (e) {
    console.error(e)
    alert('Ошибка загрузки шаблонов')
  } finally {
    loadingUserTemplates.value = false
  }
}

watch(activeTab, (tab) => {
  if (tab === 'my') loadUserTemplates()
})

// USE TEMPLATE
const useTemplate = async (tpl: any) => {
  try {
    const model = tpl.model || JSON.parse(tpl.jsonData || '{}')

    const created = await createProject(JSON.stringify(model))

    router.push(`/editor/${created.id}`)
  } catch (e) {
    console.error(e)
    alert('Ошибка создания проекта')
  }
}

onMounted(() => {
  if (activeTab.value === 'my') loadUserTemplates()
})
</script>
