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
    const calendars = await client.getCalendarsByUserID(currentUser.value.userID)
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
        :events="events"
        color="primary"
        type="month"
    >
      <template #event="{ event }">
        <div class="custom-event" :title="event.name">
          {{ event.name }}
        </div>
      </template>
    </v-calendar>
  </v-container>
</template>

<style scoped>
.custom-event {
  background-color: dimgray;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 500;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}
</style>

