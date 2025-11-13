<!-- eslint-disable vue/multi-word-component-names -->
<!-- src/components/Sidebar.vue -->
<template>
  <aside 
    class="bg-gradient-to-b from-[#f9f7ff]/90 to-[#fdf9ff]/90 border-r border-[#eae6ff] backdrop-blur-xl flex flex-col transition-all duration-400 ease-out"
    :class="isCollapsed ? 'w-16' : 'w-64'"
  >
    <!-- Заголовок -->
    <div class="p-4 border-b border-[#eae6ff] flex items-center justify-between">
      <!-- Логотип -->
      <div v-if="!isCollapsed" class="flex items-center gap-3">
        <div class="relative">
          <div class="w-10 h-10 rounded-2xl bg-gradient-to-br from-[#8a6bff] via-[#a66bff] to-[#c977e0] flex items-center justify-center text-white font-bold text-sm shadow-[0_4px_16px_rgba(138,107,255,0.25)]">
            LB
          </div>
          <!-- Пульсирующая звезда -->
          <div class="absolute -top-0.5 -right-0.5 w-2 h-2 rounded-full bg-[#d966ff] animate-ping opacity-70"></div>
          <div class="absolute -top-0.5 -right-0.5 w-2 h-2 rounded-full bg-[#d966ff]"></div>
        </div>
        <h2 class="text-gray-800 font-medium tracking-tight text-sm">Landing Builder</h2>
      </div>

      <!-- Кнопка сворачивания -->
      <button 
        @click="toggleCollapse"
        class="p-2 text-gray-500 hover:text-[#8a6bff] rounded-xl hover:bg-[#f0ebff] transition-all duration-300"
        :aria-label="isCollapsed ? 'Развернуть меню' : 'Свернуть меню'"
      >
        <svg 
          xmlns="http://www.w3.org/2000/svg" 
          class="w-5 h-5 transform transition-transform duration-300"
          :class="isCollapsed ? 'rotate-180' : ''"
          viewBox="0 0 20 20" 
          fill="currentColor"
        >
          <path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
        </svg>
      </button>
    </div>

    <!-- Навигация -->
    <nav class="flex-1 px-3 py-5 space-y-2">
      <router-link
        v-for="item in menuItems"
        :key="item.to"
        :to="item.to"
        class="flex items-center gap-3 px-3 py-2.5 text-sm font-medium rounded-2xl transition-all duration-300 group relative"
        :class="{
          'text-[#7a5bff] bg-[#f0ebff] shadow-[0_2px_10px_rgba(138,107,255,0.08)] border border-[#e8dfff]': $route.path === item.to,
          'text-gray-700 hover:bg-[#f0ebff]/70 hover:text-[#8a6bff]': $route.path !== item.to
        }"
      >
        <span 
          class="w-8 h-8 flex items-center justify-center rounded-xl transition-colors duration-300"
          :class="{
            'text-[#8a6bff] bg-[#e8dfff]': $route.path === item.to,
            'text-gray-500 bg-white/50 group-hover:text-[#8a6bff] group-hover:bg-[#e8dfff]/60': $route.path !== item.to
          }"
          v-html="item.svg"
        ></span>
        <span v-if="!isCollapsed" class="whitespace-nowrap tracking-tight">{{ item.label }}</span>
      </router-link>
    </nav>

    <!-- Кнопка выхода -->
    <div class="px-3 pb-4 border-t border-[#eae6ff] mt-auto">
      <button 
        @click="logout"
        class="w-full flex items-center gap-3 px-3 py-2.5 text-sm text-gray-600 font-medium rounded-2xl transition-all duration-300 hover:bg-[#fde8ff]/70 hover:text-[#d966ff] group"
      >
        <span 
          class="w-8 h-8 flex items-center justify-center rounded-xl text-gray-500 bg-white/50 group-hover:bg-[#ffdde8]/50 group-hover:text-[#d966ff] transition-colors"
          v-html="logoutIcon"
        ></span>
        <span v-if="!isCollapsed" class="whitespace-nowrap tracking-tight">Выйти</span>
      </button>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isCollapsed = ref(false)

// Иконки с тонкими линиями и согласованным stroke
const homeIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.7"><path stroke-linecap="round" stroke-linejoin="round" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 00-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" /></svg>`
const folderIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.7"><path stroke-linecap="round" stroke-linejoin="round" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z" /></svg>`
const cogIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.7"><path stroke-linecap="round" stroke-linejoin="round" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" /><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" /></svg>`
const logoutIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.7"><path stroke-linecap="round" stroke-linejoin="round" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" /></svg>`

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
    // Здесь будет логика аутентификации
    router.push('/')
  }
}
</script>

<style scoped>
aside {
  /* Гарантирует плавность при анимации ширины */
  will-change: width;
  /* Мягкая внутренняя граница */
  box-shadow: inset -1px 0 0 0 rgba(138, 107, 255, 0.06);
}

/* Пульсация — точная копия из 404 для согласованности */
@keyframes ping {
  75%, 100% {
    transform: scale(2);
    opacity: 0;
  }
}
.animate-ping {
  animation: ping 2s cubic-bezier(0, 0, 0.2, 1) infinite;
}
</style>