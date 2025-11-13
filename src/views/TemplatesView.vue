<!-- src/views/TemplatesView.vue -->
<template>
  <div
    class="min-h-screen bg-gradient-to-br from-[#f0f4ff] via-[#fdf6ff] to-[#fff0f8] py-16 px-4 overflow-hidden relative"
  >
    <!-- Фоновые декоративные элементы -->
    <div
      class="absolute top-1/5 left-1/6 w-32 h-32 rounded-full bg-[#e0d6ff]/30 blur-3xl animate-pulse-slow"
    ></div>
    <div
      class="absolute bottom-1/4 right-1/5 w-48 h-48 rounded-full bg-[#ffd6f0]/30 blur-3xl animate-pulse-slow animation-delay-2000"
    ></div>
    <div
      class="absolute top-1/3 right-1/4 w-20 h-20 rounded-full bg-[#c8e6ff]/40 blur-2xl animate-float"
    ></div>

    <div class="max-w-6xl mx-auto relative z-10">
      <!-- Заголовок -->
      <div class="text-center mb-12 px-4">
        <h1
          class="text-3xl font-bold bg-gradient-to-r from-[#6a5af9] via-[#a66bff] to-[#d966ff] bg-clip-text text-transparent mb-2 animate-fade-in"
        >
          Шаблоны лендингов
        </h1>
        <p
          class="text-gray-600 max-w-2xl mx-auto leading-relaxed animate-fade-in-delay-1 text-sm md:text-base"
        >
          Выберите готовый шаблон — или создайте что-то новое с помощью волшебства ИИ.
        </p>
      </div>

      <!-- Вкладки -->
      <div class="flex justify-center mb-12 animate-fade-in-delay-2">
        <div
          class="inline-flex bg-white/60 backdrop-blur-sm rounded-2xl p-2 border border-white/30 shadow-sm"
        >
          <button
            @click="activeTab = 'built-in'"
            class="px-6 py-3 rounded-xl font-medium transition-all duration-300 relative"
            :class="
              activeTab === 'built-in'
                ? 'bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white shadow-md'
                : 'text-gray-700 hover:text-[#6a5af9] hover:bg-white/50'
            "
          >
            Встроенные
          </button>
          <button
            @click="activeTab = 'my'"
            class="px-6 py-3 rounded-xl font-medium transition-all duration-300 relative"
            :class="
              activeTab === 'my'
                ? 'bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white shadow-md'
                : 'text-gray-700 hover:text-[#6a5af9] hover:bg-white/50'
            "
          >
            Мои шаблоны
            <span
              v-if="userTemplates.length > 0"
              class="absolute -top-2 -right-2 w-5 h-5 bg-[#d966ff] text-white text-xs rounded-full flex items-center justify-center animate-pulse-slow"
            >
              {{ userTemplates.length }}
            </span>
          </button>
          <button
            @click="activeTab = 'ai'"
            class="px-6 py-3 rounded-xl font-medium transition-all duration-300 relative"
            :class="
              activeTab === 'ai'
                ? 'bg-gradient-to-r from-[#a66bff] to-[#d966ff] text-white shadow-md'
                : 'text-gray-700 hover:text-[#d966ff] hover:bg-white/50'
            "
          >
            С ИИ
          </button>
        </div>
      </div>

      <!-- Built-in Templates -->
      <div
        v-if="activeTab === 'built-in'"
        class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 animate-fade-in-delay-3"
      >
        <div
          v-for="tpl in builtInTemplates"
          :key="tpl.id"
          @click="useTemplate(tpl)"
          class="bg-white/70 backdrop-blur-sm rounded-2xl border border-white/40 overflow-hidden shadow-lg hover:shadow-xl transition-all duration-400 cursor-pointer group hover:-translate-y-1 hover:scale-[1.01]"
        >
          <div class="p-6">
            <div class="flex justify-between items-start mb-4">
              <h3 class="font-bold text-lg text-gray-800 group-hover:text-[#6a5af9] truncate">
                {{ tpl.name }}
              </h3>
              <span
                class="px-2.5 py-1 bg-[#e6dfff] text-[#6a5af9] text-xs font-medium rounded-full"
              >
                Готов
              </span>
            </div>
            <p class="text-sm text-gray-600 mb-4">
              {{ tpl.description }}
            </p>

            <!-- Цветовая палитра -->
            <div class="flex gap-2 mb-5">
              <div
                v-for="(color, idx) in tpl.colorPalette"
                :key="idx"
                :style="{ backgroundColor: color }"
                class="w-6 h-6 rounded-full border border-white/50 shadow-sm"
              ></div>
            </div>

            <!-- Preview -->
            <div
              class="aspect-video bg-gradient-to-br from-indigo-50/70 to-purple-50/70 rounded-xl border border-white/40 flex items-center justify-center mb-4"
            >
              <div class="text-center px-2">
                <div
                  class="w-10 h-10 bg-[#e6dfff] rounded-xl flex items-center justify-center mx-auto mb-2"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-5 h-5 text-[#6a5af9]"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
                    />
                  </svg>
                </div>
                <p class="text-xs text-gray-500">Встроенный шаблон</p>
              </div>
            </div>

            <div class="flex justify-end">
              <span
                class="text-sm font-medium text-[#6a5af9] group-hover:text-[#d966ff] transition-colors"
              >
                → Использовать
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- User Templates -->
      <div v-else-if="activeTab === 'my'" class="animate-fade-in-delay-3">
        <div v-if="loadingUserTemplates" class="text-center py-16">
          <div
            class="inline-block w-8 h-8 border-4 border-[#6a5af9] border-t-transparent rounded-full animate-spin"
          ></div>
          <p class="mt-4 text-gray-600">Загрузка ваших шаблонов...</p>
        </div>

        <div v-else-if="userTemplates.length === 0" class="max-w-2xl mx-auto">
          <div
            class="bg-white/70 backdrop-blur-sm rounded-2xl border-2 border-dashed border-[#6a5af9]/30 p-12 text-center shadow-lg"
          >
            <div
              class="mx-auto w-16 h-16 bg-[#e6dfff] rounded-2xl flex items-center justify-center mb-5"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="w-8 h-8 text-[#6a5af9]"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                />
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-800 mb-3">У вас пока нет шаблонов</h3>
            <p class="text-gray-600 mb-6 max-w-md mx-auto">
              Откройте проект → нажмите «Сохранить как шаблон» → и он появится здесь!
            </p>
            <router-link
              to="/"
              class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-[#6a5af9] to-[#a66bff] text-white font-medium rounded-2xl shadow-md hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="w-4 h-4 mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M15 19l-7-7 7-7"
                />
              </svg>
              К проектам
            </router-link>
          </div>
        </div>

        <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          <div
            v-for="tpl in userTemplates"
            :key="tpl.id"
            @click="useTemplate(tpl)"
            class="bg-white/70 backdrop-blur-sm rounded-2xl border border-white/40 overflow-hidden shadow-lg hover:shadow-xl transition-all duration-400 cursor-pointer group hover:-translate-y-1 hover:scale-[1.01]"
          >
            <div class="p-6">
              <div class="flex justify-between items-start mb-4">
                <h3 class="font-bold text-lg text-gray-800 group-hover:text-[#6a5af9] truncate">
                  {{ tpl.name }}
                </h3>
                <span
                  class="px-2.5 py-1 bg-[#e0f2ff] text-[#0ea5e9] text-xs font-medium rounded-full"
                >
                  Мой
                </span>
              </div>
              <p class="text-sm text-gray-600 mb-4">
                {{ tpl.description || 'Создано вами' }}
              </p>

              <div class="flex gap-2 mb-5">
                <div
                  v-for="(color, idx) in tpl.colorPalette || ['#6b7280', '#4b5563', '#374151']"
                  :key="idx"
                  :style="{ backgroundColor: color }"
                  class="w-6 h-6 rounded-full border border-white/50 shadow-sm"
                ></div>
              </div>

              <div
                class="aspect-video bg-gradient-to-br from-slate-50/70 to-blue-50/70 rounded-xl border border-white/40 flex items-center justify-center mb-4"
              >
                <div class="text-center px-2">
                  <div
                    class="w-10 h-10 bg-[#e0f2ff] rounded-xl flex items-center justify-center mx-auto mb-2"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="w-5 h-5 text-[#0ea5e9]"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                      />
                    </svg>
                  </div>
                  <p class="text-xs text-gray-500">Ваш шаблон</p>
                </div>
              </div>

              <div class="flex justify-between items-center text-xs text-gray-500">
                <span>{{ formatDate(tpl.updatedAt) }}</span>
                <span class="text-sm font-medium text-[#6a5af9] group-hover:text-[#d966ff]">
                  → Использовать
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- AI Templates -->
      <div v-else-if="activeTab === 'ai'" class="max-w-3xl mx-auto animate-fade-in-delay-3">
        <div
          class="bg-white/70 backdrop-blur-sm rounded-2xl border border-white/40 p-8 text-center shadow-lg"
        >
          <div
            class="w-16 h-16 bg-[#fde8ff] rounded-2xl flex items-center justify-center mx-auto mb-5"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="w-8 h-8 text-[#d966ff]"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"
              />
            </svg>
          </div>
          <h2 class="text-2xl font-bold text-gray-800 mb-3">Создайте шаблон с помощью ИИ</h2>
          <p class="text-gray-600 mb-6 max-w-md mx-auto">
            Опишите идею — и ИИ за секунды сгенерирует лендинг под ваш запрос.
          </p>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8 text-left">
            <div class="p-4 bg-[#fde8ff]/60 rounded-xl border border-[#d966ff]/20">
              <div
                class="w-8 h-8 bg-[#d966ff] rounded-lg flex items-center justify-center text-white text-sm font-bold mb-2"
              >
                1
              </div>
              <h4 class="font-medium text-gray-800 mb-1">Откройте конструктор</h4>
              <p class="text-sm text-gray-600">Начните с чистого листа</p>
            </div>
            <div class="p-4 bg-[#e6dfff]/60 rounded-xl border border-[#6a5af9]/20">
              <div
                class="w-8 h-8 bg-[#6a5af9] rounded-lg flex items-center justify-center text-white text-sm font-bold mb-2"
              >
                2
              </div>
              <h4 class="font-medium text-gray-800 mb-1">Нажмите «AI Конструктор»</h4>
              <p class="text-sm text-gray-600">Опишите, что хотите видеть</p>
            </div>
          </div>

          <div
            class="mb-8 p-4 bg-gradient-to-r from-[#fde8ff]/50 to-[#e6dfff]/50 rounded-xl border border-white/40 text-left"
          >
            <p class="font-medium text-gray-800 mb-2">Примеры запросов:</p>
            <ul class="text-sm text-gray-600 space-y-1">
              <li>• «Создай лендинг для онлайн-курса по йоге»</li>
              <li>• «Добавь таймер скидки и форму заявки»</li>
              <li>• «Сделай дизайн в стиле Apple — минимализм, белый фон»</li>
            </ul>
          </div>

          <router-link
            to="/ai-builder"
            class="inline-flex items-center px-6 py-3 bg-gradient-to-r from-[#a66bff] to-[#d966ff] text-white font-medium rounded-2xl shadow-md hover:shadow-lg transition-all duration-300 transform hover:-translate-y-0.5"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="w-4 h-4 mr-2"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z"
              />
            </svg>
            Начать с ИИ
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(16px) scale(0.98);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(-12px) rotate(2deg);
  }
}

@keyframes pulse-slow {
  0%,
  100% {
    opacity: 0.3;
    transform: scale(1);
  }
  50% {
    opacity: 0.5;
    transform: scale(1.05);
  }
}

.animate-fade-in {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
}

.animate-fade-in-delay-1 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.15s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-2 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.3s forwards;
  animation-fill-mode: both;
}

.animate-fade-in-delay-3 {
  animation: fade-in 0.7s cubic-bezier(0.22, 0.61, 0.36, 1) 0.45s forwards;
  animation-fill-mode: both;
}

.animate-float {
  animation: float 8s ease-in-out infinite;
}

.animate-pulse-slow {
  animation: pulse-slow 6s ease-in-out infinite;
}

.animation-delay-2000 {
  animation-delay: 2s;
}
</style>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/api/client'

const router = useRouter()
const activeTab = ref<'built-in' | 'my' | 'ai'>('built-in')
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const userTemplates = ref<any[]>([])
const loadingUserTemplates = ref(true)

const builtInTemplates = [
  {
    id: 'template-online-course',
    name: 'Онлайн-курс',
    description: 'Идеален для продажи курсов: таймер, отзывы, форма заявки',
    colorPalette: ['#4f46e5', '#7c3aed', '#ec4899'],
    jsonModel: {
      components: [
        {
          type: 'wrapper',
          components: [
            // 🔝 ШАПКА
            {
              type: 'header',
              tagName: 'header',
              attributes: { class: 'header' },
              components: [
                {
                  type: 'text',
                  tagName: 'div',
                  content: '🎓 Мой Онлайн-Курс',
                  attributes: { class: 'logo' },
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'О курсе',
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'Отзывы',
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'Контакты',
                },
              ],
              style: {
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                padding: '20px 40px',
                backgroundColor: '#ffffff',
                boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
                fontFamily: 'Arial, sans-serif',
              },
            },

            // 🎯 ГЕРОЙ-СЕКЦИЯ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'hero' },
              components: [
                {
                  type: 'text',
                  tagName: 'h1',
                  content: '🔥 Учись на 100% — без стресса и перегрузок!',
                  style: {
                    fontSize: '3rem',
                    fontWeight: 'bold',
                    textAlign: 'center',
                    color: '#4f46e5',
                    marginBottom: '20px',
                  },
                },
                {
                  type: 'text',
                  tagName: 'p',
                  content:
                    'Пройди курс за 30 дней и получи сертификат, который ценят работодатели.',
                  style: {
                    fontSize: '1.2rem',
                    textAlign: 'center',
                    color: '#333',
                    marginBottom: '30px',
                  },
                },
                {
                  type: 'button',
                  tagName: 'button',
                  attributes: { class: 'cta-button' },
                  content: 'Начать сейчас →',
                  style: {
                    padding: '15px 30px',
                    backgroundColor: '#ec4899',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer',
                    fontSize: '1.1rem',
                    fontWeight: 'bold',
                    transition: 'background-color 0.3s',
                  },
                  events: {
                    click: 'alert("Форма заявки откроется!")',
                  },
                },
                {
                  type: 'component',
                  tagName: 'div',
                  attributes: { class: 'timer' },
                  content: '<strong>До конца акции:</strong> <span id="countdown">02:59:59</span>',
                  style: {
                    marginTop: '20px',
                    textAlign: 'center',
                    fontSize: '1.1rem',
                    color: '#7c3aed',
                    fontWeight: 'bold',
                  },
                },
              ],
              style: {
                padding: '80px 40px',
                textAlign: 'center',
                backgroundColor: '#f9fafb',
                backgroundImage: 'linear-gradient(to bottom, #f9fafb, #f3f4f6)',
              },
            },

            // 💬 ОТЗЫВЫ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'testimonials' },
              components: [
                {
                  type: 'text',
                  tagName: 'h2',
                  content: 'Что говорят наши студенты',
                  style: {
                    fontSize: '2.5rem',
                    textAlign: 'center',
                    color: '#4f46e5',
                    marginBottom: '40px',
                  },
                },
                {
                  type: 'grid',
                  tagName: 'div',
                  attributes: { class: 'testimonial-grid' },
                  components: [
                    {
                      type: 'card',
                      tagName: 'div',
                      attributes: { class: 'testimonial-card' },
                      components: [
                        {
                          type: 'image',
                          tagName: 'img',
                          attributes: {
                            src: 'https://via.placeholder.com/80',
                            alt: 'Анна',
                            class: 'avatar',
                          },
                          style: {
                            width: '80px',
                            height: '80px',
                            borderRadius: '50%',
                            marginBottom: '10px',
                          },
                        },
                        {
                          type: 'text',
                          tagName: 'p',
                          content:
                            '«Курс изменил мою жизнь! Я нашла работу мечты через 2 недели после окончания»',
                          style: {
                            fontSize: '1rem',
                            textAlign: 'center',
                            color: '#333',
                          },
                        },
                        {
                          type: 'text',
                          tagName: 'small',
                          content: '— Анна, 28 лет',
                          style: {
                            display: 'block',
                            textAlign: 'center',
                            color: '#7c3aed',
                            fontWeight: 'bold',
                          },
                        },
                      ],
                      style: {
                        padding: '20px',
                        backgroundColor: 'white',
                        borderRadius: '12px',
                        boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
                        textAlign: 'center',
                      },
                    },
                    // Добавьте еще 2 отзыва по аналогии...
                  ],
                  style: {
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
                    gap: '20px',
                    margin: '0 auto',
                    maxWidth: '1200px',
                  },
                },
              ],
              style: {
                padding: '60px 40px',
                textAlign: 'center',
                backgroundColor: '#ffffff',
              },
            },

            // 📝 ФОРМА ЗАЯВКИ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'form-section' },
              components: [
                {
                  type: 'text',
                  tagName: 'h2',
                  content: 'Оставьте заявку — мы свяжемся с вами!',
                  style: {
                    fontSize: '2.5rem',
                    textAlign: 'center',
                    color: '#4f46e5',
                    marginBottom: '20px',
                  },
                },
                {
                  type: 'form',
                  tagName: 'form',
                  attributes: { class: 'contact-form' },
                  components: [
                    {
                      type: 'input',
                      tagName: 'input',
                      attributes: {
                        type: 'text',
                        placeholder: 'Ваше имя',
                        required: true,
                        class: 'form-input',
                      },
                      style: {
                        width: '100%',
                        padding: '12px',
                        marginBottom: '15px',
                        border: '1px solid #ddd',
                        borderRadius: '8px',
                        fontSize: '1rem',
                      },
                    },
                    {
                      type: 'input',
                      tagName: 'input',
                      attributes: {
                        type: 'email',
                        placeholder: 'Email',
                        required: true,
                        class: 'form-input',
                      },
                      style: {
                        width: '100%',
                        padding: '12px',
                        marginBottom: '15px',
                        border: '1px solid #ddd',
                        borderRadius: '8px',
                        fontSize: '1rem',
                      },
                    },
                    {
                      type: 'button',
                      tagName: 'button',
                      attributes: { type: 'submit', class: 'submit-btn' },
                      content: 'Отправить заявку',
                      style: {
                        width: '100%',
                        padding: '15px',
                        backgroundColor: '#ec4899',
                        color: 'white',
                        border: 'none',
                        borderRadius: '8px',
                        cursor: 'pointer',
                        fontSize: '1.1rem',
                        fontWeight: 'bold',
                      },
                    },
                  ],
                  style: {
                    maxWidth: '500px',
                    margin: '0 auto',
                    padding: '30px',
                    backgroundColor: '#f9fafb',
                    borderRadius: '12px',
                    boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
                  },
                },
              ],
              style: {
                padding: '60px 40px',
                textAlign: 'center',
                backgroundColor: '#f3f4f6',
              },
            },

            // 📌 ПОДВАЛ
            {
              type: 'footer',
              tagName: 'footer',
              attributes: { class: 'footer' },
              components: [
                {
                  type: 'text',
                  tagName: 'p',
                  content: '© 2025 Мой Онлайн-Курс. Все права защищены.',
                  style: {
                    textAlign: 'center',
                    color: '#64748b',
                    fontSize: '0.9rem',
                  },
                },
              ],
              style: {
                padding: '20px',
                textAlign: 'center',
                backgroundColor: '#f8fafc',
                borderTop: '1px solid #e2e8f0',
                fontSize: '0.9rem',
              },
            },
          ],
        },
      ],
      styles: [
        // Стили для шапки
        '.header { background: white; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }',
        '.header .logo { font-size: 1.5rem; font-weight: bold; color: #4f46e5; }',
        '.header .nav-link { margin: 0 15px; text-decoration: none; color: #333; }',
        '.header .nav-link:hover { color: #4f46e5; }',

        // Стили героя
        '.hero h1 { font-size: 3rem; color: #4f46e5; }',
        '.hero p { font-size: 1.2rem; color: #333; }',
        '.cta-button { background: #ec4899; color: white; border: none; padding: 15px 30px; border-radius: 8px; cursor: pointer; }',
        '.cta-button:hover { background: #e11d48; }',
        '.timer { font-size: 1.1rem; color: #7c3aed; font-weight: bold; }',

        // Стили отзывов
        '.testimonials h2 { color: #4f46e5; }',
        '.testimonial-card { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }',
        '.testimonial-card .avatar { width: 80px; height: 80px; border-radius: 50%; }',
        '.testimonial-card p { font-size: 1rem; color: #333; }',
        '.testimonial-card small { display: block; color: #7c3aed; font-weight: bold; }',

        // Стили формы
        '.form-section h2 { color: #4f46e5; }',
        '.contact-form { max-width: 500px; margin: 0 auto; padding: 30px; background: #f9fafb; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }',
        '.form-input { width: 100%; padding: 12px; margin-bottom: 15px; border: 1px solid #ddd; border-radius: 8px; font-size: 1rem; }',
        '.submit-btn { width: 100%; padding: 15px; background: #ec4899; color: white; border: none; border-radius: 8px; cursor: pointer; font-size: 1.1rem; font-weight: bold; }',
        '.submit-btn:hover { background: #e11d48; }',

        // Стили подвала
        '.footer { background: #f8fafc; border-top: 1px solid #e2e8f0; padding: 20px; text-align: center; font-size: 0.9rem; color: #64748b; }',
      ],
      pages: [
        {
          id: 'main',
          name: 'Главная',
          component: { type: 'wrapper', components: [] },
          styles: [],
        },
      ],
    },
  },
  {
    id: 'template-fitness-product',
    name: 'Фитнес-продукт',
    description: 'Продающий лендинг для товаров: акцент на выгоде и отзыве',
    colorPalette: ['#0ea5e9', '#3b82f6', '#6366f1'],
    jsonModel: {
      components: [
        {
          type: 'wrapper',
          components: [
            // 🔝 ШАПКА
            {
              type: 'header',
              tagName: 'header',
              attributes: { class: 'header' },
              components: [
                {
                  type: 'text',
                  tagName: 'div',
                  content: '💪 FitPro',
                  attributes: { class: 'logo' },
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'Продукт',
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'Отзывы',
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'Контакты',
                },
              ],
              style: {
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                padding: '20px 40px',
                backgroundColor: '#ffffff',
                boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
                fontFamily: 'Arial, sans-serif',
              },
            },

            // 🎯 ГЕРОЙ-СЕКЦИЯ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'hero' },
              components: [
                {
                  type: 'text',
                  tagName: 'h1',
                  content: '🔥 Превратите свое тело за 30 дней!',
                  style: {
                    fontSize: '3rem',
                    fontWeight: 'bold',
                    textAlign: 'center',
                    color: '#0ea5e9',
                    marginBottom: '20px',
                  },
                },
                {
                  type: 'text',
                  tagName: 'p',
                  content:
                    'Научитесь правильно питаться и тренироваться с нашим продуктом. Результат гарантирован!',
                  style: {
                    fontSize: '1.2rem',
                    textAlign: 'center',
                    color: '#333',
                    marginBottom: '30px',
                  },
                },
                {
                  type: 'button',
                  tagName: 'button',
                  attributes: { class: 'cta-button' },
                  content: 'Купить сейчас →',
                  style: {
                    padding: '15px 30px',
                    backgroundColor: '#3b82f6',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer',
                    fontSize: '1.1rem',
                    fontWeight: 'bold',
                    transition: 'background-color 0.3s',
                  },
                },
              ],
              style: {
                padding: '80px 40px',
                textAlign: 'center',
                backgroundColor: '#f9fafb',
                backgroundImage: 'linear-gradient(to bottom, #f9fafb, #f3f4f6)',
              },
            },

            // 🖼️ ИЗОБРАЖЕНИЕ ПРОДУКТА + ВЫГОДЫ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'product-benefits' },
              components: [
                {
                  type: 'grid',
                  tagName: 'div',
                  attributes: { class: 'product-grid' },
                  components: [
                    {
                      type: 'image',
                      tagName: 'img',
                      attributes: {
                        src: 'https://via.placeholder.com/500x300?text=FitPro+Product',
                        alt: 'FitPro Product',
                        class: 'product-image',
                      },
                      style: {
                        width: '100%',
                        height: 'auto',
                        borderRadius: '12px',
                        boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
                      },
                    },
                    {
                      type: 'div',
                      tagName: 'div',
                      attributes: { class: 'benefits-list' },
                      components: [
                        {
                          type: 'text',
                          tagName: 'h2',
                          content: 'Почему выбирают FitPro?',
                          style: {
                            fontSize: '2rem',
                            color: '#0ea5e9',
                            marginBottom: '20px',
                          },
                        },
                        {
                          type: 'list',
                          tagName: 'ul',
                          components: [
                            {
                              type: 'li',
                              tagName: 'li',
                              content: '✅ Научитесь есть правильно',
                              style: {
                                fontSize: '1.1rem',
                                color: '#333',
                                marginBottom: '10px',
                              },
                            },
                            {
                              type: 'li',
                              tagName: 'li',
                              content: '✅ Уникальная методика тренировок',
                              style: {
                                fontSize: '1.1rem',
                                color: '#333',
                                marginBottom: '10px',
                              },
                            },
                            {
                              type: 'li',
                              tagName: 'li',
                              content: '✅ Поддержка тренера 24/7',
                              style: {
                                fontSize: '1.1rem',
                                color: '#333',
                                marginBottom: '10px',
                              },
                            },
                          ],
                          style: {
                            paddingLeft: '20px',
                            fontSize: '1.1rem',
                            color: '#333',
                          },
                        },
                        {
                          type: 'button',
                          tagName: 'button',
                          attributes: { class: 'buy-now' },
                          content: 'Купить за $49',
                          style: {
                            marginTop: '20px',
                            padding: '12px 24px',
                            backgroundColor: '#6366f1',
                            color: 'white',
                            border: 'none',
                            borderRadius: '8px',
                            cursor: 'pointer',
                            fontSize: '1.1rem',
                            fontWeight: 'bold',
                          },
                        },
                      ],
                      style: {
                        padding: '20px',
                        backgroundColor: 'white',
                        borderRadius: '12px',
                        boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
                      },
                    },
                  ],
                  style: {
                    display: 'grid',
                    gridTemplateColumns: '1fr 1fr',
                    gap: '30px',
                    margin: '0 auto',
                    maxWidth: '1200px',
                    alignItems: 'center',
                  },
                },
              ],
              style: {
                padding: '60px 40px',
                textAlign: 'center',
                backgroundColor: '#ffffff',
              },
            },

            // 💬 ОТЗЫВЫ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'testimonials' },
              components: [
                {
                  type: 'text',
                  tagName: 'h2',
                  content: 'Результаты наших клиентов',
                  style: {
                    fontSize: '2.5rem',
                    textAlign: 'center',
                    color: '#0ea5e9',
                    marginBottom: '40px',
                  },
                },
                {
                  type: 'grid',
                  tagName: 'div',
                  attributes: { class: 'testimonial-grid' },
                  components: [
                    {
                      type: 'card',
                      tagName: 'div',
                      attributes: { class: 'testimonial-card' },
                      components: [
                        {
                          type: 'image',
                          tagName: 'img',
                          attributes: {
                            src: 'https://via.placeholder.com/80',
                            alt: 'Иван',
                            class: 'avatar',
                          },
                          style: {
                            width: '80px',
                            height: '80px',
                            borderRadius: '50%',
                            marginBottom: '10px',
                          },
                        },
                        {
                          type: 'text',
                          tagName: 'p',
                          content:
                            '«Сбросил 15 кг за месяц! Теперь я чувствую себя уверенно и здоровым»',
                          style: {
                            fontSize: '1rem',
                            textAlign: 'center',
                            color: '#333',
                          },
                        },
                        {
                          type: 'text',
                          tagName: 'small',
                          content: '— Иван, 32 года',
                          style: {
                            display: 'block',
                            textAlign: 'center',
                            color: '#0ea5e9',
                            fontWeight: 'bold',
                          },
                        },
                      ],
                      style: {
                        padding: '20px',
                        backgroundColor: 'white',
                        borderRadius: '12px',
                        boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
                        textAlign: 'center',
                      },
                    },
                    // Добавьте еще 2 отзыва...
                  ],
                  style: {
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
                    gap: '20px',
                    margin: '0 auto',
                    maxWidth: '1200px',
                  },
                },
              ],
              style: {
                padding: '60px 40px',
                textAlign: 'center',
                backgroundColor: '#f3f4f6',
              },
            },

            // 📌 ПОДВАЛ
            {
              type: 'footer',
              tagName: 'footer',
              attributes: { class: 'footer' },
              components: [
                {
                  type: 'text',
                  tagName: 'p',
                  content: '© 2025 FitPro. Все права защищены.',
                  style: {
                    textAlign: 'center',
                    color: '#64748b',
                    fontSize: '0.9rem',
                  },
                },
              ],
              style: {
                padding: '20px',
                textAlign: 'center',
                backgroundColor: '#f8fafc',
                borderTop: '1px solid #e2e8f0',
                fontSize: '0.9rem',
              },
            },
          ],
        },
      ],
      styles: [
        '.header { background: white; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }',
        '.header .logo { font-size: 1.5rem; font-weight: bold; color: #0ea5e9; }',
        '.header .nav-link { margin: 0 15px; text-decoration: none; color: #333; }',
        '.header .nav-link:hover { color: #0ea5e9; }',

        '.hero h1 { font-size: 3rem; color: #0ea5e9; }',
        '.hero p { font-size: 1.2rem; color: #333; }',
        '.cta-button { background: #3b82f6; color: white; border: none; padding: 15px 30px; border-radius: 8px; cursor: pointer; }',
        '.cta-button:hover { background: #2563eb; }',

        '.product-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; max-width: 1200px; margin: 0 auto; align-items: center; }',
        '.product-image { width: 100%; height: auto; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }',
        '.benefits-list h2 { font-size: 2rem; color: #0ea5e9; margin-bottom: 20px; }',
        '.benefits-list ul { padding-left: 20px; font-size: 1.1rem; color: #333; }',
        '.benefits-list li { margin-bottom: 10px; }',
        '.buy-now { margin-top: 20px; padding: 12px 24px; background: #6366f1; color: white; border: none; border-radius: 8px; cursor: pointer; font-size: 1.1rem; font-weight: bold; }',
        '.buy-now:hover { background: #4f46e5; }',

        '.testimonials h2 { color: #0ea5e9; }',
        '.testimonial-card { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }',
        '.testimonial-card .avatar { width: 80px; height: 80px; border-radius: 50%; }',
        '.testimonial-card p { font-size: 1rem; color: #333; }',
        '.testimonial-card small { display: block; color: #0ea5e9; font-weight: bold; }',

        '.footer { background: #f8fafc; border-top: 1px solid #e2e8f0; padding: 20px; text-align: center; font-size: 0.9rem; color: #64748b; }',
      ],
      pages: [
        {
          id: 'main',
          name: 'Главная',
          component: { type: 'wrapper', components: [] },
          styles: [],
        },
      ],
    },
  },
  {
    id: 'template-saas-service',
    name: 'SaaS-сервис',
    description: 'Для IT-продуктов: упор на функционал и интеграции',
    colorPalette: ['#1e293b', '#334155', '#64748b'],
    jsonModel: {
      components: [
        {
          type: 'wrapper',
          components: [
            // 🔝 ШАПКА
            {
              type: 'header',
              tagName: 'header',
              attributes: { class: 'header' },
              components: [
                {
                  type: 'text',
                  tagName: 'div',
                  content: '🚀 SaaSFlow',
                  attributes: { class: 'logo' },
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'Функции',
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'Цены',
                },
                {
                  type: 'link',
                  tagName: 'a',
                  attributes: { href: '#', class: 'nav-link' },
                  content: 'API',
                },
                {
                  type: 'button',
                  tagName: 'button',
                  attributes: { class: 'btn-login' },
                  content: 'Войти',
                  style: {
                    padding: '8px 16px',
                    backgroundColor: '#334155',
                    color: 'white',
                    border: 'none',
                    borderRadius: '6px',
                    cursor: 'pointer',
                    fontSize: '0.9rem',
                  },
                },
              ],
              style: {
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                padding: '20px 40px',
                backgroundColor: '#ffffff',
                boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
                fontFamily: 'Arial, sans-serif',
              },
            },

            // 🎯 ГЕРОЙ-СЕКЦИЯ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'hero' },
              components: [
                {
                  type: 'text',
                  tagName: 'h1',
                  content: 'Автоматизируйте бизнес с SaaSFlow',
                  style: {
                    fontSize: '3rem',
                    fontWeight: 'bold',
                    textAlign: 'center',
                    color: '#1e293b',
                    marginBottom: '20px',
                  },
                },
                {
                  type: 'text',
                  tagName: 'p',
                  content:
                    'Интегрируйте CRM, почту, платежи и аналитику в один интерфейс. Быстро, надежно, масштабируемо.',
                  style: {
                    fontSize: '1.2rem',
                    textAlign: 'center',
                    color: '#334155',
                    marginBottom: '30px',
                  },
                },
                {
                  type: 'button',
                  tagName: 'button',
                  attributes: { class: 'cta-button' },
                  content: 'Попробовать бесплатно →',
                  style: {
                    padding: '15px 30px',
                    backgroundColor: '#64748b',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer',
                    fontSize: '1.1rem',
                    fontWeight: 'bold',
                    transition: 'background-color 0.3s',
                  },
                },
              ],
              style: {
                padding: '80px 40px',
                textAlign: 'center',
                backgroundColor: '#f9fafb',
                backgroundImage: 'linear-gradient(to bottom, #f9fafb, #f3f4f6)',
              },
            },

            // ⚙️ ФУНКЦИИ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'features' },
              components: [
                {
                  type: 'text',
                  tagName: 'h2',
                  content: 'Все, что вам нужно',
                  style: {
                    fontSize: '2.5rem',
                    textAlign: 'center',
                    color: '#1e293b',
                    marginBottom: '40px',
                  },
                },
                {
                  type: 'grid',
                  tagName: 'div',
                  attributes: { class: 'features-grid' },
                  components: [
                    {
                      type: 'card',
                      tagName: 'div',
                      attributes: { class: 'feature-card' },
                      components: [
                        {
                          type: 'text',
                          tagName: 'h3',
                          content: 'CRM-интеграция',
                          style: {
                            fontSize: '1.3rem',
                            color: '#1e293b',
                            marginBottom: '10px',
                          },
                        },
                        {
                          type: 'text',
                          tagName: 'p',
                          content:
                            'Подключите вашу CRM-систему и управляйте клиентами из одного места.',
                          style: {
                            fontSize: '1rem',
                            color: '#334155',
                          },
                        },
                      ],
                      style: {
                        padding: '20px',
                        backgroundColor: 'white',
                        borderRadius: '12px',
                        boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
                      },
                    },
                    {
                      type: 'card',
                      tagName: 'div',
                      attributes: { class: 'feature-card' },
                      components: [
                        {
                          type: 'text',
                          tagName: 'h3',
                          content: 'Платежи',
                          style: {
                            fontSize: '1.3rem',
                            color: '#1e293b',
                            marginBottom: '10px',
                          },
                        },
                        {
                          type: 'text',
                          tagName: 'p',
                          content: 'Принимайте платежи через Stripe, PayPal и другие системы.',
                          style: {
                            fontSize: '1rem',
                            color: '#334155',
                          },
                        },
                      ],
                      style: {
                        padding: '20px',
                        backgroundColor: 'white',
                        borderRadius: '12px',
                        boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
                      },
                    },
                    {
                      type: 'card',
                      tagName: 'div',
                      attributes: { class: 'feature-card' },
                      components: [
                        {
                          type: 'text',
                          tagName: 'h3',
                          content: 'Аналитика',
                          style: {
                            fontSize: '1.3rem',
                            color: '#1e293b',
                            marginBottom: '10px',
                          },
                        },
                        {
                          type: 'text',
                          tagName: 'p',
                          content:
                            'Визуализируйте данные с помощью графиков и дашбордов в реальном времени.',
                          style: {
                            fontSize: '1rem',
                            color: '#334155',
                          },
                        },
                      ],
                      style: {
                        padding: '20px',
                        backgroundColor: 'white',
                        borderRadius: '12px',
                        boxShadow: '0 4px 12px rgba(0,0,0,0.05)',
                      },
                    },
                  ],
                  style: {
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
                    gap: '20px',
                    margin: '0 auto',
                    maxWidth: '1200px',
                  },
                },
              ],
              style: {
                padding: '60px 40px',
                textAlign: 'center',
                backgroundColor: '#ffffff',
              },
            },

            // 📊 ИНТЕГРАЦИИ
            {
              type: 'section',
              tagName: 'section',
              attributes: { class: 'integrations' },
              components: [
                {
                  type: 'text',
                  tagName: 'h2',
                  content: 'Работает с вашими инструментами',
                  style: {
                    fontSize: '2.5rem',
                    textAlign: 'center',
                    color: '#1e293b',
                    marginBottom: '20px',
                  },
                },
                {
                  type: 'div',
                  tagName: 'div',
                  attributes: { class: 'integration-logos' },
                  components: [
                    {
                      type: 'image',
                      tagName: 'img',
                      attributes: {
                        src: 'https://via.placeholder.com/100x50?text=Stripe',
                        alt: 'Stripe',
                        class: 'logo',
                      },
                      style: {
                        width: '100px',
                        height: '50px',
                        margin: '0 15px',
                      },
                    },
                    {
                      type: 'image',
                      tagName: 'img',
                      attributes: {
                        src: 'https://via.placeholder.com/100x50?text=Slack',
                        alt: 'Slack',
                        class: 'logo',
                      },
                      style: {
                        width: '100px',
                        height: '50px',
                        margin: '0 15px',
                      },
                    },
                    {
                      type: 'image',
                      tagName: 'img',
                      attributes: {
                        src: 'https://via.placeholder.com/100x50?text=Google+Analytics',
                        alt: 'Google Analytics',
                        class: 'logo',
                      },
                      style: {
                        width: '100px',
                        height: '50px',
                        margin: '0 15px',
                      },
                    },
                    {
                      type: 'image',
                      tagName: 'img',
                      attributes: {
                        src: 'https://via.placeholder.com/100x50?text=Zapier',
                        alt: 'Zapier',
                        class: 'logo',
                      },
                      style: {
                        width: '100px',
                        height: '50px',
                        margin: '0 15px',
                      },
                    },
                  ],
                  style: {
                    display: 'flex',
                    justifyContent: 'center',
                    flexWrap: 'wrap',
                    gap: '20px',
                    margin: '20px 0',
                  },
                },
              ],
              style: {
                padding: '60px 40px',
                textAlign: 'center',
                backgroundColor: '#f3f4f6',
              },
            },

            // 📌 ПОДВАЛ
            {
              type: 'footer',
              tagName: 'footer',
              attributes: { class: 'footer' },
              components: [
                {
                  type: 'text',
                  tagName: 'p',
                  content: '© 2025 SaaSFlow. Все права защищены.',
                  style: {
                    textAlign: 'center',
                    color: '#64748b',
                    fontSize: '0.9rem',
                  },
                },
              ],
              style: {
                padding: '20px',
                textAlign: 'center',
                backgroundColor: '#f8fafc',
                borderTop: '1px solid #e2e8f0',
                fontSize: '0.9rem',
              },
            },
          ],
        },
      ],
      styles: [
        '.header { background: white; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }',
        '.header .logo { font-size: 1.5rem; font-weight: bold; color: #1e293b; }',
        '.header .nav-link { margin: 0 15px; text-decoration: none; color: #334155; }',
        '.header .nav-link:hover { color: #1e293b; }',
        '.btn-login { padding: 8px 16px; background: #334155; color: white; border: none; border-radius: 6px; cursor: pointer; font-size: 0.9rem; }',
        '.btn-login:hover { background: #1e293b; }',

        '.hero h1 { font-size: 3rem; color: #1e293b; }',
        '.hero p { font-size: 1.2rem; color: #334155; }',
        '.cta-button { background: #64748b; color: white; border: none; padding: 15px 30px; border-radius: 8px; cursor: pointer; }',
        '.cta-button:hover { background: #475569; }',

        '.features h2 { color: #1e293b; }',
        '.features-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; max-width: 1200px; margin: 0 auto; }',
        '.feature-card { padding: 20px; background: white; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }',
        '.feature-card h3 { font-size: 1.3rem; color: #1e293b; margin-bottom: 10px; }',
        '.feature-card p { font-size: 1rem; color: #334155; }',

        '.integrations h2 { color: #1e293b; }',
        '.integration-logos { display: flex; justify-content: center; flex-wrap: wrap; gap: 20px; margin: 20px 0; }',
        '.integration-logos .logo { width: 100px; height: 50px; margin: 0 15px; }',

        '.footer { background: #f8fafc; border-top: 1px solid #e2e8f0; padding: 20px; text-align: center; font-size: 0.9rem; color: #64748b; }',
      ],
      pages: [
        {
          id: 'main',
          name: 'Главная',
          component: { type: 'wrapper', components: [] },
          styles: [],
        },
      ],
    },
  },
]

const fetchUserTemplates = async () => {
  loadingUserTemplates.value = true
  try {
    const { data } = await api.get('/api/templates') // ✅ Путь исправлен
    userTemplates.value = Array.isArray(data) ? data : []
  } catch (e) {
    console.error('Ошибка загрузки шаблонов:', e)
    alert('❌ Не удалось загрузить ваши шаблоны')
    userTemplates.value = []
  } finally {
    loadingUserTemplates.value = false
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const useTemplate = async (template: any) => {
  const userId = localStorage.getItem('userId')
  if (!userId) {
    alert('❌ Вы не авторизованы')
    router.push('/login')
    return
  }

   try {
    // Восстанавливаем модель из jsonData
    let jsonModel = {}
    try {
      jsonModel = JSON.parse(template.jsonData || '{}')
    } catch (e) {
      console.warn('Не удалось распарсить jsonData шаблона')
    }
        const payload = {
      jsonData: JSON.stringify({
        name: `Копия: ${template.name}`,
        model: jsonModel,
      }),
      user: { id: userId },
    }
        const { data } = await api.post('/api/projects', payload) // ✅ /api/projects
    router.push(`/editor/${data.id}`)
  } catch (error) {
    console.error('Ошибка при создании проекта:', error)
    alert('❌ Не удалось создать проект на основе шаблона')
  }
}

const templateName = ref('')
const templateDescription = ref('')
const savingTemplate = ref(false)
const showTemplateModal = ref(false)

const openSaveAsTemplateModal = () => {
}
const saveAsTemplate = async () => {
  const userId = localStorage.getItem('userId')
  if (!userId) {
    alert('❌ Вы не авторизованы')
    router.push('/login')
    showTemplateModal.value = false
    return
  }
    alert('❗ Сохранение шаблона должно вызываться из редактора')
  showTemplateModal.value = false
  return
}
const formatDate = (iso: string) => {
  return new Date(iso).toLocaleDateString('ru-RU', {
    day: 'numeric',
    month: 'short',
  })
}

watch(activeTab, (newTab) => {
  if (newTab === 'my' && userTemplates.value.length === 0) {
    fetchUserTemplates()
  }
})

onMounted(() => {
  if (activeTab.value === 'my') {
    fetchUserTemplates()
  }
})
</script>