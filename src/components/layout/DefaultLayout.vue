<template>
    <div class="flex">
      <Sidebar />
      <main class="flex-1 min-h-screen">
        <RouterView />
      </main>
    </div>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import Sidebar from '@/components/layout/Sidebar.vue'
</script>

<style scoped>
</style>