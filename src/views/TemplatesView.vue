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
// ВСТРОЕННЫЕ ШАБЛОНЫ (3 штуки)
// ===========================
const builtInTemplates = [
  {
    id: 'template-hero',
    name: 'Герой + CTA',
    description: 'Яркий заголовок, подзаголовок и кнопка — идеально для MVP и стартапов',
    colorPalette: ['#4f46e5', '#7c3aed', '#ec4899'],
    jsonModel: {
      components: [
        {
          type: 'default',
          tagName: 'div',
          attributes: { class: 'hero text-center py-20 px-4 bg-gradient-to-r from-indigo-50 to-purple-50 min-h-screen flex flex-col justify-center' },
          components: [
            {
              type: 'text',
              content: '<h1 class="text-3xl md:text-5xl font-extrabold text-gray-900 leading-tight">Ваш продукт<br />решает проблему</h1>'
            },
            {
              type: 'text',
              content: '<p class="mt-4 text-lg text-gray-600 max-w-2xl mx-auto">Опишите суть за 10 секунд — и получите первых клиентов</p>'
            },
            {
              type: 'default',
              tagName: 'div',
              attributes: { class: 'mt-8 flex flex-col sm:flex-row justify-center gap-4' },
              components: [
                {
                  type: 'button',
                  tagName: 'button',
                  attributes: { class: 'px-6 py-3 bg-indigo-600 text-white font-medium rounded-xl shadow hover:bg-indigo-700 transition' },
                  content: 'Начать бесплатно'
                },
                {
                  type: 'button',
                  tagName: 'button',
                  attributes: { class: 'px-6 py-3 bg-white text-indigo-600 font-medium rounded-xl border border-indigo-300 hover:bg-indigo-50 transition' },
                  content: 'Демо'
                }
              ]
            }
          ]
        }
      ],
      styles: `
        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; line-height: 1.5; }
        * { box-sizing: border-box; }
      `
    }
  },
  {
    id: 'template-features',
    name: 'Фичи + Преимущества',
    description: 'Три колонки с иконками — покажите, почему вас стоит выбрать',
    colorPalette: ['#0ea5e9', '#3b82f6', '#6366f1'],
    jsonModel: {
      components: [
        {
          type: 'default',
          tagName: 'div',
          attributes: { class: 'features py-16 px-4 bg-white' },
          components: [
            {
              type: 'text',
              content: '<h2 class="text-3xl font-bold text-center text-gray-900">Почему нас выбирают</h2>'
            },
            {
              type: 'text',
              content: '<p class="mt-4 text-gray-600 text-center max-w-xl mx-auto">Просто, быстро и без технических знаний</p>'
            },
            {
              type: 'default',
              tagName: 'div',
              attributes: { class: 'mt-12 grid grid-cols-1 md:grid-cols-3 gap-8 max-w-5xl mx-auto' },
              components: [
                {
                  type: 'default',
                  tagName: 'div',
                  attributes: { class: 'text-center p-6' },
                  components: [
                    {
                      type: 'text',
                      content: '<div class="w-14 h-14 mx-auto bg-blue-100 rounded-xl flex items-center justify-center text-blue-600 text-xl">⚡</div>'
                    },
                    {
                      type: 'text',
                      content: '<h3 class="mt-4 text-xl font-semibold text-gray-900">Мгновенно</h3>'
                    },
                    {
                      type: 'text',
                      content: '<p class="mt-2 text-gray-600">Создайте лендинг за 5 минут</p>'
                    }
                  ]
                },
                {
                  type: 'default',
                  tagName: 'div',
                  attributes: { class: 'text-center p-6' },
                  components: [
                    {
                      type: 'text',
                      content: '<div class="w-14 h-14 mx-auto bg-blue-100 rounded-xl flex items-center justify-center text-blue-600 text-xl">🎨</div>'
                    },
                    {
                      type: 'text',
                      content: '<h3 class="mt-4 text-xl font-semibold text-gray-900">Гибко</h3>'
                    },
                    {
                      type: 'text',
                      content: '<p class="mt-2 text-gray-600">Меняйте всё под себя</p>'
                    }
                  ]
                },
                {
                  type: 'default',
                  tagName: 'div',
                  attributes: { class: 'text-center p-6' },
                  components: [
                    {
                      type: 'text',
                      content: '<div class="w-14 h-14 mx-auto bg-blue-100 rounded-xl flex items-center justify-center text-blue-600 text-xl">📱</div>'
                    },
                    {
                      type: 'text',
                      content: '<h3 class="mt-4 text-xl font-semibold text-gray-900">Адаптивно</h3>'
                    },
                    {
                      type: 'text',
                      content: '<p class="mt-2 text-gray-600">Работает на всех устройствах</p>'
                    }
                  ]
                }
              ]
            }
          ]
        }
      ],
      styles: `
        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; }
      `
    }
  },
  {
    id: 'template-footer-cta',
    name: 'Футер + Финальный CTA',
    description: 'Завершающий блок с призывом к действию и контактами',
    colorPalette: ['#1e293b', '#334155', '#64748b'],
    jsonModel: {
      components: [
        {
          type: 'default',
          tagName: 'div',
          attributes: { class: 'cta-footer py-16 px-4 bg-gray-900 text-white' },
          components: [
            {
              type: 'text',
              content: '<h2 class="text-3xl font-bold text-center">Готовы начать?</h2>'
            },
            {
              type: 'text',
              content: '<p class="mt-4 text-gray-300 text-center max-w-xl mx-auto">Присоединяйтесь к тысячам пользователей, которые уже создали свой лендинг</p>'
            },
            {
              type: 'default',
              tagName: 'div',
              attributes: { class: 'mt-8 flex justify-center' },
              components: [
                {
                  type: 'button',
                  tagName: 'button',
                  attributes: { class: 'px-8 py-3 bg-white text-gray-900 font-bold rounded-xl shadow-lg hover:shadow-xl transition' },
                  content: 'Создать сейчас'
                }
              ]
            },
            {
              type: 'text',
              content: '<div class="mt-12 border-t border-gray-800 pt-8 text-center text-gray-400 text-sm">© 2025 Ваш сервис. Все права защищены.</div>'
            }
          ]
        }
      ],
      styles: `
        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; }
      `
    }
  }
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