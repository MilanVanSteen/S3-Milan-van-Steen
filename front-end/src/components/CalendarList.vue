<script setup>
import { ref, onMounted } from 'vue'
import { Client } from '../API.ts'
import {useRoute} from "vue-router";

const route = useRoute()
const client = new Client()
const calendars = ref([])
const isLoading = ref(true)
const error = ref(null)
const currentUser = ref(null)

const fetchUser = async () => {
  try {
    currentUser.value = await client.getUserById(route.params.userID)
  } catch (err) {
    console.error('Error fetching user:', err)
    throw new Error('User fetch failed')
  }
}

const fetchCalendars = async () => {
  try {
    calendars.value = await client.getCalendarsByUserID(currentUser.value.userID)
  } catch (err) {
    console.error('Error fetching calendars:', err)
    throw new Error('Calendar fetch failed')
  }
}

onMounted(async () => {
  try {
    await fetchUser()
    if (!currentUser.value) throw new Error('User not found')
    await fetchCalendars()
  } catch (err) {
    error.value = err.message
  } finally {
    isLoading.value = false
  }
})
</script>


<template>
  <header>
    <h1>Calendars</h1>

    <p v-if="isLoading">Loading calendars...</p>
    <p v-else-if="error">{{ error }}</p>

    <ul v-else>
      <li v-for="calendar in calendars" :key="calendar.calendarID">
        Calendar met ID: {{ calendar.calendarID }}

        <p>Events:</p>
        <ul>
          <li v-for="event in calendar.events" :key="event.eventID">
            {{ event.name }} (ID: {{ event.eventID }})
          </li>
        </ul>
      </li>
    </ul>
  </header>

  <main></main>
</template>

