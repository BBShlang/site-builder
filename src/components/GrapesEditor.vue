<!-- src/components/GrapesEditor.vue -->
<template>
  <div ref="container" class="grapes-editor w-full h-full"></div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue'
import grapesjs, { Editor } from 'grapesjs'
import 'grapesjs/dist/css/grapes.min.css'

import webpagePreset from 'grapesjs-preset-webpage'
import tabsPlugin from 'grapesjs-tabs'

const container = ref<HTMLDivElement | null>(null)
let editor: Editor | null = null

// Утилита: загрузка файла и конвертация в base64
function fileToBase64(file: File): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = () => resolve(reader.result as string)
    reader.onerror = reject
    reader.readAsDataURL(file)
  })
}

// Плагин для обработки медиа (фото и видео)
const mediaUploadPlugin = (editor: Editor) => {
  // Команда: загрузить фото
  editor.Commands.add('upload-image', {
    run(editor) {
      const modal = editor.Modal
      const input = document.createElement('input')
      input.type = 'file'
      input.accept = 'image/*'
      input.onchange = async (e) => {
        const file = (e.target as HTMLInputElement).files?.[0]
        if (!file) return
        if (file.size > 15 * 1024 * 1024) {
          alert('Файл слишком большой (макс. 15 МБ)')
          return
        }
        try {
          const dataUrl = await fileToBase64(file)
          const selected = editor.getSelected()
          if (selected && selected.get('type') === 'image') {
            selected.set('src', dataUrl)
          }
          modal.close()
        } catch (err) {
          console.error(err)
          alert('Ошибка загрузки изображения')
        }
      }
      input.click()
    },
  })

  // Команда: загрузить видео
  editor.Commands.add('upload-video', {
    run(editor) {
      const modal = editor.Modal
      const input = document.createElement('input')
      input.type = 'file'
      input.accept = 'video/*'
      input.onchange = async (e) => {
        const file = (e.target as HTMLInputElement).files?.[0]
        if (!file) return
        if (file.size > 30 * 1024 * 1024) {
          alert('Видео слишком большое (макс. 30 МБ)')
          return
        }
        try {
          const dataUrl = await fileToBase64(file)
          const selected = editor.getSelected()
          if (selected && selected.get('type') === 'video') {
            selected.set('src', dataUrl)
            selected.set('attributes', { ...selected.get('attributes'), src: dataUrl })
          }
          modal.close()
        } catch (err) {
          console.error(err)
          alert('Ошибка загрузки видео')
        }
      }
      input.click()
    },
  })

  // Перехватываем двойной клик на медиа
  editor.on('component:doubleclick', (component) => {
    if (component.get('type') === 'image') {
      editor.runCommand('upload-image')
    } else if (component.get('type') === 'video') {
      editor.runCommand('upload-video')
    }
  })
}

onMounted(() => {
  if (!container.value) return

  editor = grapesjs.init({
    container: container.value,
    height: '100%',
    width: '100%',
    storageManager: false,
    plugins: [webpagePreset, tabsPlugin, mediaUploadPlugin],
    pluginsOpts: {
      [webpagePreset]: {},
      [tabsPlugin]: {},
    },
    blockManager: { blocks: [] },
    deviceManager: {
      devices: [
        { name: 'Mobile', width: '375px' },
        { name: 'Tablet', width: '768px' },
        { name: 'Desktop', width: '1200px' },
      ],
    },
  })

  const bm = editor.BlockManager
  bm.getAll().reset()

  const colors = {
    primary: '#3b82f6',
    text: '#1e293b',
    secondary: '#64748b',
    bg: '#f8fafc',
    light: '#f1f5f9',
  }

  // === Шапка ===
  bm.add('header', {
    label: 'Шапка',
    category: 'Структура',
    content: `<header style="display:flex; justify-content:space-between; padding:20px; background:white; box-shadow:0 2px 8px rgba(0,0,0,0.05);">Логотип<div>Меню</div></header>`,
  })

  // === Подвал ===
  bm.add('footer', {
    label: 'Подвал',
    category: 'Структура',
    content: `<footer style="padding:30px; text-align:center; background:${colors.light}; color:${colors.secondary};">© 2025</footer>`,
  })

  // === Текст ===
  bm.add('text', {
    label: 'Текст',
    category: 'Текст',
    content: '<p style="font-size:1.1rem; line-height:1.6;">Редактируемый текст</p>',
  })

  // === Изображение (с поддержкой загрузки) ===
  bm.add('image', {
    label: 'Изображение',
    category: 'Медиа',
    content: {
      type: 'image',
      attributes: { 
        src: 'image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" width="400" height="250" viewBox="0 0 400 250"%3E%3Crect width="400" height="250" fill="%23f1f5f9"/%3E%3Ctext x="50%25" y="50%25" text-anchor="middle" dominant-baseline="middle" font-family="Arial" font-size="16" fill="%2394a3b8"%3EДважды кликните, чтобы загрузить фото%3C/text%3E%3C/svg%3E',
        alt: 'Изображение'
      },
      style: { width: '100%', height: 'auto', display: 'block', cursor: 'pointer' },
    },
    select: true,
    activate: true,
  })

  // === Видео (с поддержкой загрузки) ===
  bm.add('video', {
    label: 'Видео',
    category: 'Медиа',
    content: {
      type: 'video',
      tagName: 'video',
      attributes: {
        controls: true,
        src: '', // будет заменено
      },
      components: [
        {
          tagName: 'source',
          attributes: {
            src: 'video/mp4;base64,', // placeholder
            type: 'video/mp4',
          },
        },
        'Ваш браузер не поддерживает видео.',
      ],
      style: {
        width: '100%',
        height: 'auto',
        backgroundColor: '#000',
        cursor: 'pointer',
      },
    },
    select: true,
    activate: true,
  })

  // === Кнопка ===
  bm.add('button', {
    label: 'Кнопка',
    category: 'Элементы',
    content: `<button style="padding:12px 28px; background:${colors.primary}; color:white; border:none; border-radius:8px; cursor:pointer;">Кнопка</button>`,
  })
})

onUnmounted(() => {
  if (editor) {
    editor.destroy()
    editor = null
  }
})
</script>

<style scoped>
.grapes-editor :deep(.gjs-cv-canvas) {
  padding: 0;
}
</style>