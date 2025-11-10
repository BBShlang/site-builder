<template>
  <div class="default-layout">
    <Header />
    <div class="flex">
      <Sidebar />
      <main class="flex-1 min-h-screen">
        <RouterView />
      </main>
    </div>
    <Footer />
  </div>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import Header from '@/components/layout/Header.vue'
import Sidebar from '@/components/layout/Sidebar.vue'
import Footer from '@/components/layout/Footer.vue'
</script>
