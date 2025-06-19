import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      'vuetify/lib/components/VCode/VCode.css': fileURLToPath(new URL('./empty.css', import.meta.url)),
    },
  },
  test: {
    globals: true,
    environment: 'jsdom',
    transformMode: {
      web: [/\.vue$/],
    },
    deps: {
      inline: ['vuetify'],
    }
  }
})
