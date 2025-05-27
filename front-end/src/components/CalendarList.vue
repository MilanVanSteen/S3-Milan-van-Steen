<script setup>
import { ref, onMounted } from 'vue'
import { Client } from '../API.ts' // adjust path as needed

const client = new Client()
const calendars = ref([])
const isLoading = ref(true)
const error = ref(null)

const fetchCalendars = async () => {
  try {
    calendars.value = await client.getAllCalendars()
  } catch (err) {
    error.value = err
  } finally {
    isLoading.value = false
  }
}

onMounted(() => {
  fetchCalendars()
})
</script>

<template>
  <header>
    <h1>Calendars</h1>

    <p v-if="isLoading">Loading calendars...</p>
    <p v-else-if="error">{{ error }}</p>

    <ul v-else>
      <li v-for="calendar in calendars" :key="calendar.calendarID">
        (ID: {{ calendar.calendarID }})
      </li>
    </ul>
  </header>

  <main></main>
</template>

