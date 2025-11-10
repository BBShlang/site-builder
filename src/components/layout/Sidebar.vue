<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <aside 
    class="bg-white border-r border-gray-100 flex flex-col transition-all duration-300"
    :class="isCollapsed ? 'w-16' : 'w-64'"
  >
    <!-- Заголовок + кнопка сворачивания -->
    <div class="p-4 border-b border-gray-100 flex items-center justify-between">
      <!-- Логотип в стиле Header -->
      <div v-if="!isCollapsed" class="flex items-center gap-3 group">
        <div class="relative">
          <div
            class="w-10 h-10 rounded-xl bg-gradient-to-br from-blue-600 to-indigo-600 flex items-center justify-center text-white font-bold text-lg shadow-sm group-hover:shadow-md transition-all duration-300"
          >
            LB
          </div>
          <div
            class="absolute -top-1 -right-1 w-3 h-3 bg-blue-400 rounded-full animate-pulse"
          ></div>
        </div>
        <h2
          class="font-bold text-sm bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent"
        >
          Landing Builder
        </h2>
      </div>

      <!-- Кнопка сворачивания (в стиле PuzzleArt) -->
      <button 
        @click="toggleCollapse"
        class="p-2 text-gray-500 hover:text-blue-600 rounded-xl hover:bg-blue-50 transition-all duration-300 group"
      >
        <svg 
          xmlns="http://www.w3.org/2000/svg" 
          class="w-5 h-5" 
          viewBox="0 0 20 20" 
          fill="currentColor"
        >
          <path 
            v-if="!isCollapsed"
            fill-rule="evenodd" 
            d="M14.707 12.707a1 1 0 01-1.414 0L10 9.414l-3.293 3.293a1 1 0 01-1.414-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 010 1.414z" 
            clip-rule="evenodd" 
          />
          <path 
            v-else
            fill-rule="evenodd" 
            d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" 
            clip-rule="evenodd" 
          />
        </svg>
      </button>
    </div>

    <!-- Навигация -->
    <nav class="flex-1 px-2 py-4 space-y-1">
      <router-link
        v-for="item in menuItems"
        :key="item.to"
        :to="item.to"
        class="flex items-center gap-3 px-3 py-2.5 text-sm rounded-xl font-medium transition-all duration-200 group"
        :class="[
          $route.path === item.to 
            ? 'bg-gradient-to-r from-blue-50 to-indigo-50 text-blue-700 border border-blue-100 shadow-sm' 
            : 'text-gray-700 hover:bg-blue-50 hover:text-blue-600'
        ]"
      >
        <!-- Иконка: цветная при активности, градиент-ховер -->
        <span 
          class="w-5 h-5 flex-shrink-0 flex items-center justify-center rounded"
          :class="[
            $route.path === item.to 
              ? 'text-blue-600 bg-blue-100' 
              : 'text-gray-500 group-hover:text-blue-600 group-hover:bg-blue-100'
          ]"
          v-html="item.svg"
        ></span>
        <span v-if="!isCollapsed" class="whitespace-nowrap">{{ item.label }}</span>
      </router-link>
    </nav>

    <!-- Выйти (в стиле PuzzleArt) -->
    <div class="p-3 border-t border-gray-100 mt-auto">
      <button 
        @click="logout"
        class="flex items-center gap-3 w-full px-3 py-2.5 text-sm text-gray-600 hover:bg-blue-50 hover:text-blue-700 rounded-xl font-medium transition-all duration-200 group"
      >
        <span 
          class="w-5 h-5 flex items-center justify-center rounded text-gray-500 group-hover:text-blue-600 group-hover:bg-blue-100"
          v-html="logoutIcon"
        ></span>
        <span v-if="!isCollapsed" class="whitespace-nowrap">Выйти</span>
      </button>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isCollapsed = ref(false)

// SVG-иконки как строки (без template → безопасно для runtime-only Vue)
const homeIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 00-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" /></svg>`
const folderIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z" /></svg>`
const cogIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" /><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" /></svg>`

const logoutIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" /></svg>`

const menuItems = computed(() => [
  { to: '/', label: 'Проекты', svg: homeIcon },
  { to: '/templates', label: 'Шаблоны', svg: folderIcon },
  { to: '/profile', label: 'Профиль', svg: cogIcon }
])

const toggleCollapse = () => {
  isCollapsed.value = !isCollapsed.value
}

const logout = () => {
  if (confirm('Выйти из аккаунта?')) {
    // Здесь будет логика выхода
    router.push('/')
  }
}
</script>
<style scoped>
/* Добавляем плавность при сворачивании */
aside {
  will-change: width;
}
</style>