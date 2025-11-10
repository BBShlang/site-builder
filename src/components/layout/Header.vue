<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <!-- Main Header -->
    <div class="bg-white py-4 border-b border-gray-200">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex items-center justify-between">
          <!-- Logo -->
          <router-link to="/" class="flex items-center gap-3 group">
            <div class="relative">
              <div
                class="w-12 h-12 rounded-xl bg-gradient-to-br from-blue-600 to-indigo-600 flex items-center justify-center text-white font-bold text-xl shadow-md group-hover:shadow-lg transition-all duration-300"
              >
                LB
              </div>
              <div
                class="absolute -top-1 -right-1 w-4 h-4 bg-blue-400 rounded-full animate-pulse"
              ></div>
            </div>
            <div>
              <h1
                class="text-2xl font-bold bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent"
              >
                Landing Builder
              </h1>
              <p class="text-sm text-gray-500 -mt-1">Создавайте лендинги без кода</p>
            </div>
          </router-link>

          <!-- Desktop Navigation -->
          <nav class="hidden lg:flex items-center space-x-8">
            <router-link
              v-for="item in navItems"
              :key="item.to"
              :to="item.to"
              class="relative text-gray-700 hover:text-blue-600 font-medium transition-colors duration-300 group"
              :class="{ 'text-blue-600': $route.path === item.to }"
            >
              {{ item.label }}
              <span
                class="absolute -bottom-1 left-0 w-0 h-0.5 bg-gradient-to-r from-blue-600 to-indigo-600 group-hover:w-full transition-all duration-300"
                :class="{ 'w-full': $route.path === item.to }"
              ></span>
            </router-link>
          </nav>

          <!-- Actions -->
          <div class="flex items-center gap-4">
            <!-- Mobile Menu Button -->
            <button
              @click="toggleMobileMenu"
              class="lg:hidden p-3 bg-gray-50 hover:bg-blue-50 rounded-xl transition-all duration-300 group"
            >
              <Icon
                :icon="isMobileMenuOpen ? 'heroicons:x-mark' : 'heroicons:bars-3'"
                class="w-6 h-6 text-gray-700 group-hover:text-blue-600"
              />
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile Menu -->
    <div
      v-if="isMobileMenuOpen"
      class="lg:hidden bg-white border-t border-gray-100 shadow-lg"
    >
      <div class="max-w-7xl mx-auto px-4 py-6">
        <nav class="space-y-4 mb-6">
          <router-link
            v-for="item in navItems"
            :key="item.to"
            :to="item.to"
            class="block py-3 px-4 text-gray-700 hover:text-blue-600 hover:bg-blue-50 rounded-xl font-medium transition-all duration-300"
            :class="{ 'text-blue-600 bg-blue-50': $route.path === item.to }"
            @click="isMobileMenuOpen = false"
          >
            {{ item.label }}
          </router-link>
        </nav>
      </div>
    </div>

</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { Icon } from '@iconify/vue'

const route = useRoute()

const isMobileMenuOpen = ref(false)


const navItems = [
  { label: 'Проекты', to: '/' },
  { label: 'Шаблоны', to: '/templates' },
  { label: 'Профиль', to: '/profile' },
]



const toggleMobileMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
}

// Закрывать меню при навигации
watch(() => route.path, () => {
  isMobileMenuOpen.value = false
})
</script>