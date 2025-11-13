<!-- src/views/TemplatesView.vue -->
<template>
  <div class="min-h-screen bg-gradient-to-b from-gray-50 to-indigo-50">
    <!-- Header -->
    <div class="max-w-7xl mx-auto px-4 py-8 md:px-6">
      <div class="mb-10 text-center md:text-left">
        <h1 class="text-3xl md:text-4xl font-bold mb-3 bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent">
          Шаблоны лендингов
        </h1>
        <p class="text-gray-600 max-w-2xl mx-auto md:mx-0">
          Выберите готовый шаблон — и начните редактировать за 1 клик
        </p>
      </div>

      <!-- Templates Grid -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- Built-in Templates -->
        <div
          v-for="tpl in builtInTemplates"
          :key="tpl.id"
          @click="useTemplate(tpl)"
          class="bg-white rounded-2xl border border-gray-200 overflow-hidden shadow-sm hover:shadow-lg transition-all duration-300 cursor-pointer group hover:-translate-y-1"
        >
          <div class="p-5">
            <div class="flex justify-between items-start mb-3">
              <h3 class="font-bold text-lg text-gray-800 group-hover:text-indigo-700 truncate">
                {{ tpl.name }}
              </h3>
            </div>
            <p class="text-sm text-gray-600 mb-3">
              {{ tpl.description }}
            </p>

            <!-- Color palette -->
            <div class="flex gap-1.5 mb-4">
              <div
                v-for="(color, idx) in tpl.colorPalette"
                :key="idx"
                :style="{ backgroundColor: color }"
                class="w-5 h-5 rounded-full border border-gray-200 flex-shrink-0"
              ></div>
            </div>

            <!-- Preview placeholder -->
            <div class="aspect-video bg-gradient-to-br from-indigo-50 to-purple-50 rounded-xl border border-gray-200 flex items-center justify-center mb-4">
              <div class="text-center px-2">
                <div class="w-10 h-10 bg-indigo-100 rounded-xl flex items-center justify-center mx-auto mb-2">
                  <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-indigo-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                  </svg>
                </div>
                <p class="text-xs text-gray-500">Превью шаблона</p>
              </div>
            </div>

            <div class="flex justify-between items-center text-xs text-gray-500">
              <span class="px-2 py-0.5 bg-purple-100 text-purple-700 rounded-full text-xs">Готовый</span>
              <span class="text-indigo-600 font-medium group-hover:text-purple-600">
                → Использовать
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
 </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
// ===========================
// ВСТРОЕННЫЕ ШАБЛОНЫ (локально, совместимые с GrapesEditor.vue)
// ===========================
const builtInTemplates = [
  {
    id: 'template-online-course',
    name: 'Онлайн-курс',
    description: 'Идеален для продажи курсов: таймер, отзывы, форма заявки',
    colorPalette: ['#4f46e5', '#7c3aed', '#ec4899'], // [primary, accent1, accent2]
    jsonModel: {
      components: [
        {
          type: 'header',
          tagName: 'header',
          attributes: { class: 'site-header' },
          style: {
            display: 'flex',
            justifyContent: 'space-between',
            padding: '20px',
            background: '#ffffff',
            boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
          },
          components: [
            { type: 'text', content: 'LOGO' },
            { type: 'text', content: 'Меню' },
          ],
        },
        {
          type: 'section',
          style: { padding: '60px 20px', textAlign: 'center', background: '#f8fafc' },
          components: [
            {
              type: 'text',
              tagName: 'h1',
              style: { fontSize: '2.5rem', color: '#1e293b', marginBottom: '20px' },
              content: 'Освой профессию с нуля за 8 недель',
            },
            {
              type: 'text',
              tagName: 'p',
              style: { fontSize: '1.2rem', color: '#64748b', marginBottom: '30px' },
              content: 'Практический курс с поддержкой наставника',
            },
            {
              type: 'button',
              tagName: 'button',
              style: {
                padding: '14px 32px',
                background: '#4f46e5',
                color: '#ffffff',
                border: 'none',
                borderRadius: '8px',
                cursor: 'pointer',
                fontSize: '1rem',
              },
              content: 'Записаться на курс',
            },
            {
              type: 'image',
              attributes: {
                src: 'image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" width="800" height="400" viewBox="0 0 800 400"%3E%3Crect width="800" height="400" fill="%23f1f5f9"/%3E%3Ctext x="50%25" y="50%25" text-anchor="middle" dominant-baseline="middle" font-family="Arial" font-size="18" fill="%2394a3b8"%3EКурс: дважды кликните для загрузки фото%3C/text%3E%3C/svg%3E',
                alt: 'Превью курса',
              },
              style: { width: '100%', height: 'auto', cursor: 'pointer', marginTop: '40px' },
            },
          ],
        },
        {
          type: 'section',
          style: { padding: '60px 20px', background: '#ffffff', textAlign: 'center' },
          components: [
            {
              type: 'text',
              tagName: 'h2',
              style: { fontSize: '2rem', color: '#1e293b', marginBottom: '30px' },
              content: 'Отзывы учеников',
            },
            {
              type: 'text',
              content: '«Курс изменил мою карьеру!» — Анна, UX-дизайнер',
              style: { fontSize: '1.1rem', color: '#64748b', marginBottom: '15px' },
            },
            {
              type: 'text',
              content: '«Понятно даже без опыта» — Дмитрий, начинающий',
              style: { fontSize: '1.1rem', color: '#64748b' },
            },
          ],
        },
        {
          type: 'footer',
          tagName: 'footer',
          style: {
            padding: '30px',
            textAlign: 'center',
            background: '#f1f5f9',
            color: '#64748b',
          },
          content: '© 2025 Онлайн-курс',
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
          type: 'header',
          tagName: 'header',
          style: {
            display: 'flex',
            justifyContent: 'space-between',
            padding: '20px',
            background: '#ffffff',
            boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
          },
          components: [{ type: 'text', content: 'FITBRAND' }, { type: 'text', content: 'Каталог' }],
        },
        {
          type: 'section',
          style: { padding: '80px 20px', textAlign: 'center', background: '#f8fafc' },
          components: [
            {
              type: 'text',
              tagName: 'h1',
              style: { fontSize: '2.8rem', color: '#1e293b', marginBottom: '20px' },
              content: 'Твоя сила начинается здесь',
            },
            {
              type: 'text',
              tagName: 'p',
              style: { fontSize: '1.2rem', color: '#64748b', marginBottom: '30px' },
              content: 'Натуральный протеин без сахара и ГМО',
            },
            {
              type: 'button',
              tagName: 'button',
              style: {
                padding: '14px 36px',
                background: '#0ea5e9',
                color: '#ffffff',
                border: 'none',
                borderRadius: '50px',
                cursor: 'pointer',
                fontSize: '1.1rem',
                fontWeight: '600',
              },
              content: 'Купить за 1990 ₽',
            },
            {
              type: 'image',
              attributes: {
                src: 'image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" width="600" height="500" viewBox="0 0 600 500"%3E%3Crect width="600" height="500" fill="%23f1f5f9"/%3E%3Ctext x="50%25" y="50%25" text-anchor="middle" dominant-baseline="middle" font-family="Arial" font-size="18" fill="%2394a3b8"%3EПродукт: дважды кликните для загрузки фото%3C/text%3E%3C/svg%3E',
                alt: 'Протеин',
              },
              style: { width: '80%', height: 'auto', cursor: 'pointer', marginTop: '40px' },
            },
          ],
        },
        {
          type: 'footer',
          tagName: 'footer',
          style: {
            padding: '30px',
            textAlign: 'center',
            background: '#f1f5f9',
            color: '#64748b',
          },
          content: '© 2025 FITBRAND',
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
          type: 'header',
          tagName: 'header',
          style: {
            display: 'flex',
            justifyContent: 'space-between',
            padding: '20px',
            background: '#ffffff',
            boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
          },
          components: [{ type: 'text', content: 'CloudFlow' }, { type: 'text', content: 'Функции • Цены • Контакты' }],
        },
        {
          type: 'section',
          style: { padding: '100px 20px', textAlign: 'center', background: '#f8fafc' },
          components: [
            {
              type: 'text',
              tagName: 'h1',
              style: { fontSize: '2.5rem', color: '#1e293b', marginBottom: '20px' },
              content: 'Автоматизируйте ваш бизнес в облаке',
            },
            {
              type: 'text',
              tagName: 'p',
              style: { fontSize: '1.2rem', color: '#64748b', marginBottom: '40px' },
              content: 'Интеграция с 50+ сервисами. Гарантия uptime 99.9%',
            },
            {
              type: 'button',
              tagName: 'button',
              style: {
                padding: '14px 32px',
                background: '#1e293b',
                color: '#ffffff',
                border: 'none',
                borderRadius: '8px',
                cursor: 'pointer',
              },
              content: 'Начать бесплатно',
            },
          ],
        },
        {
          type: 'section',
          style: { padding: '60px 20px', background: '#ffffff', textAlign: 'center' },
          components: [
            {
              type: 'text',
              tagName: 'h2',
              style: { fontSize: '2rem', color: '#1e293b', marginBottom: '30px' },
              content: 'Как это работает',
            },
            {
              type: 'text',
              content: '1. Подключите аккаунт\n2. Настройте триггеры\n3. Получайте результаты',
              style: { whiteSpace: 'pre-line', fontSize: '1.1rem', color: '#64748b' },
            },
            {
              type: 'video',
              tagName: 'video',
              attributes: { controls: true },
              style: {
                width: '100%',
                height: 'auto',
                backgroundColor: '#000',
                cursor: 'pointer',
                marginTop: '30px',
              },
              components: [
                {
                  tagName: 'source',
                  attributes: { src: 'video/mp4;base64,', type: 'video/mp4' },
                },
                'Ваш браузер не поддерживает видео.',
              ],
            },
          ],
        },
        {
          type: 'footer',
          tagName: 'footer',
          style: {
            padding: '30px',
            textAlign: 'center',
            background: '#f1f5f9',
            color: '#64748b',
          },
          content: '© 2025 CloudFlow',
        },
      ],
    },
  },
]
// ===========================
// ЛОГИКА: ИСПОЛЬЗОВАТЬ ШАБЛОН → СОЗДАТЬ ПРОЕКТ
// ===========================
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const useTemplate = (template: any) => {
  // Генерируем новый ID проекта
  const projectId = crypto.randomUUID ? crypto.randomUUID() : 'proj-' + Date.now()

  // Формируем новый проект на основе шаблона
  const newProject = {
    id: projectId,
    name: `Копия: ${template.name}`,
    templateId: template.id,
    updatedAt: new Date().toISOString(),
    jsonModel: template.jsonModel,
    framesCount: 1,
  }

  // Загружаем существующие проекты
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  let projects: any[] = []
  try {
    const saved = localStorage.getItem('landing-projects')
    projects = saved ? JSON.parse(saved) : []
  } catch (e) {
    console.error('Ошибка загрузки проектов:', e)
    projects = []
  }

  // Добавляем новый проект
  projects.push(newProject)
  try {
    localStorage.setItem('landing-projects', JSON.stringify(projects))
  } catch (e) {
    console.error('Ошибка сохранения проекта:', e)
    alert('❌ Не удалось создать проект')
    return
  }

  // Переходим в редактор
  router.push(`/editor/${projectId}`)
}

</script>