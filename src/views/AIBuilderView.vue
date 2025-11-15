<template>
  <div class="min-h-screen bg-gradient-to-br from-[#eef1ff] to-[#fde8ff] py-16 px-6">
    <div class="max-w-3xl mx-auto bg-white/80 backdrop-blur-xl p-8 rounded-2xl shadow-xl">
      <h1
        class="text-3xl font-bold text-center bg-gradient-to-r from-[#a66bff] to-[#d966ff] bg-clip-text text-transparent mb-6"
      >
        AI Конструктор
      </h1>

      <p class="text-gray-600 text-center mb-8">
        Опиши свой лендинг — AI создаст структуру автоматически.
      </p>

      <textarea
        v-model="prompt"
        rows="4"
        placeholder="Например: Лендинг для мобильного приложения с блоками: герой, фичи, отзывы, контакты."
        class="w-full p-4 rounded-xl border border-gray-300 focus:ring-2 focus:ring-[#a66bff]"
      ></textarea>

      <button
        @click="generate"
        :disabled="loading"
        class="w-full mt-6 py-3 rounded-xl bg-gradient-to-r from-[#a66bff] to-[#d966ff] text-white font-bold shadow hover:shadow-lg disabled:opacity-50"
      >
        {{ loading ? 'Генерация...' : 'Сгенерировать лендинг' }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { api, createProject } from '@/api/client'

const prompt = ref('')
const loading = ref(false)
const router = useRouter()

const generate = async () => {
  if (!prompt.value.trim()) {
    alert('Введите описание')
    return
  }

  loading.value = true
  try {
    // Пример генерации структуры через AI (mock)
    const { data } = await api.post('/ai/generate', { prompt: prompt.value })

    const model = data.model

    const created = await createProject(JSON.stringify(model))

    router.push(`/editor/${created.id}`)
  } catch (e) {
    console.error(e)
    alert('Ошибка генерации')
  } finally {
    loading.value = false
  }
}
</script>
