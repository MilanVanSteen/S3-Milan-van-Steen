<script setup>
import { ref, onMounted } from 'vue'
import { Client } from '../API.ts'
import {useRoute} from "vue-router";

const route = useRoute()
const client = new Client()
const events = ref([])
const isLoading = ref(true)
const error = ref(null)
const currentUser = ref(null)

// Example user ID – replace with actual dynamic value
const userId = 1

const fetchUser = async () => {
  try {
    currentUser.value = await client.getUserById(route.params.userID)
  } catch (err) {
    console.error('Error fetching user:', err)
    throw new Error('User fetch failed')
  }
}

const fetchEvents = async (userId) => {
  try {
    const calendars = await client.getCalendarsByUserID(userId)
    const allEvents = calendars
        .filter(calendar => calendar && Array.isArray(calendar.events))
        .flatMap(calendar => calendar.events)
    events.value = allEvents.map(event => ({
      name: event.name,
      start: new Date(event.startDate),
      end: new Date(event.endDate),
    }))
  } catch (err) {
    throw new Error('Failed to fetch events')
  }
}

onMounted(async () => {
  isLoading.value = true
  error.value = null
  try {
    await fetchUser()
    if (!currentUser.value) throw new Error('User not found')
    await fetchEvents(currentUser.value.userID)
  } catch (err) {
    error.value = err.message
  } finally {
    isLoading.value = false
  }
})
</script>

<template>
  <v-container>
    <div v-if="isLoading">Loading events...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <v-calendar
        v-else
        :events="events"
        color="primary"
        type="month"
    />
  </v-container>
</template>
