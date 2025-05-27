<script setup>
import { ref, onMounted } from 'vue'
import { Client } from '../API.ts' // adjust path if needed

const client = new Client()
const calendars = ref([])
const isLoading = ref(true)
const error = ref(null)
const currentUser = ref(null)

const fetchUser = async () => {
  try {
    currentUser.value = await client.getUserById(2)
  } catch (err) {
    error.value = 'Error fetching user.'
    console.error(err)
  }
}

const fetchCalendars = async () => {
  try {
    const allCalendars = await client.getCalendarsByUserID(currentUser.value.userID)
    calendars.value = allCalendars.filter(c => c.isPersonal === true)
  } catch (err) {
    error.value = 'Error fetching calendars.'
    console.error(err)
  }
}

onMounted(async () => {
  isLoading.value = true
  error.value = null

  await fetchUser()
  if (currentUser.value) {
    await fetchCalendars()
  } else {
    error.value = 'User not found.'
  }

  isLoading.value = false
})
</script>

<template>
  <header>
    <h1>Personal Calendars</h1>

    <p v-if="isLoading">Loading calendars...</p>
    <p v-else-if="error">{{ error }}</p>

    <ul v-else-if="calendars.length > 0">
      <li v-for="calendar in calendars" :key="calendar.calendarID">
        (ID: {{ calendar.calendarID }})
      </li>
    </ul>

    <p v-else>No personal calendars found.</p>
  </header>

  <main></main>
</template>
