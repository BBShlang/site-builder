import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [tailwindcss(), vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
    },
  },
  server: {
    port: 5173, // опционально, для явного указания порта
    proxy: {
      '/api': {
        target: 'http://26.86.179.119:8080',
        changeOrigin: true,
        secure: false, // если бэкенд по HTTP (не HTTPS)
      },
    },
  },
})
