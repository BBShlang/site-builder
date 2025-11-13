import { createRouter, createWebHistory } from 'vue-router'

// Layouts
import AuthLayout from '@/components/layout/AuthLayout.vue'
import DefaultLayout from '@/components/layout/DefaultLayout.vue'

// Views (auth)
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import TemplatesView from '@/views/TemplatesView.vue'
import AIBuilderView from '@/views/AIBuilderView.vue'

// Lazy-loaded views (protected)
const ProjectsView = () => import('@/views/ProjectsView.vue')
const EditorView = () => import('@/views/EditorView.vue')
const ProfileView = () => import('@/views/ProfileView.vue') // тоже lazy — для единообразия

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 🔐 Страницы аутентификации — без Header/Sidebar/Footer
    {
      path: '/register',
      component: AuthLayout, // ← обёртка
      children: [
        {
          path: '',
          name: 'register',
          component: RegisterView,
          meta: { title: 'Регистрация' }
        }
      ]
    },
    {
      path: '/login',
      component: AuthLayout, // ← обёртка
      children: [
        {
          path: '',
          name: 'login',
          component: LoginView,
          meta: { title: 'Вход' }
        }
      ]
    },

    // 🛡️ Защищённые страницы — с Header/Sidebar/Footer
    {
      path: '/',
      component: DefaultLayout, // ← обёртка
      children: [
        {
          path: '',
          name: 'projects',
          component: ProjectsView,
          meta: { title: 'Мои лендинги' }
        },
        {
          path: '/profile',
          name: 'profile',
          component: ProfileView,
          meta: { title: 'Профиль' }
        },
        {
          path: '/editor/new',
          name: 'editor-new',
          component: EditorView,
          props: { isNew: true },
          meta: { title: 'Новый лендинг' }
        },
                {
          path: '/ai-builder',
          name: 'ai-builder',
          component: AIBuilderView,
          props: { isNew: true },
          meta: { title: 'Создание с помощью ИИ' }
        },
        {
          path: '/templates',
          name: 'templates',
          component: TemplatesView,
          meta: { title: 'Шаблоны' }
        },
        {
          path: '/editor/:id',
          name: 'editor',
          component: EditorView,
          props: true,
          meta: { title: 'Редактор' }
        }
      ]
    },

    // 404 — можно показать и без layout (чаще так и делают)
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      component: () => import('@/views/NotFoundView.vue'), // без layout, как есть
      // или, если хотите — оберните в AuthLayout, если 404 должен быть «чистым»
    }
  ]
})

// Динамический title
router.afterEach((to) => {
  document.title = to.meta.title 
    ? `${to.meta.title} · Landing Builder` 
    : 'Landing Builder'
})

export default router