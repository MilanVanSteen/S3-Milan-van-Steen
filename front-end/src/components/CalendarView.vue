<script setup>
import {ref, onMounted, computed} from 'vue'
import { Client } from '../API.ts'
import {useRoute} from "vue-router";
import { useDate } from 'vuetify'

const route = useRoute()
const client = new Client()
const events = ref([])
const isLoading = ref(true)
const error = ref(null)
const currentUser = ref(null)

const calendarRef = ref()
const today = ref(new Date())
const adapter = useDate()

const fetchUser = async () => {
  try {
    currentUser.value = await client.getUserById(route.params.userID)
  } catch (err) {
    console.error('Error fetching user:', err)
    throw new Error('User fetch failed')
  }
}

const fetchEvents = async () => {
  try {
    const calendars = await client.getCalendarsByUserID(currentUser.value.userID)
    const allEvents = calendars
        .filter(calendar => calendar && Array.isArray(calendar.events))
        .flatMap(calendar => calendar.events)

    events.value = allEvents.flatMap(event => {
      const expanded = []
      const start = new Date(event.startDate)
      const end = new Date(event.endDate)
      let d = new Date(start)

      while (d <= end) {
        const part =
            d.toDateString() === start.toDateString()
                ? 'start'
                : d.toDateString() === end.toDateString()
                    ? 'end'
                    : 'middle'

        expanded.push({
          name: event.name,
          start: new Date(d),
          end: new Date(d),
          part,
        })
        d.setDate(d.getDate() + 1)
      }
      return expanded
    })
  } catch (err) {
    throw new Error('Failed to fetch events')
  }
}

const prevMonth = () => {
  today.value = adapter.addMonths(today.value, -1)
}
const nextMonth = () => {
  today.value = adapter.addMonths(today.value, 1)
}

const formattedMonth = computed(() => {
  const options = { year: 'numeric', month: 'long' }
  return today.value.toLocaleDateString('en-US', options)
})

onMounted(async () => {
  isLoading.value = true
  error.value = null
  try {
    await fetchUser()
    if (!currentUser.value) throw new Error('User not found')
    await fetchEvents()
    console.log(events.value)
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

    <div v-else>
      <div class="calendar-header">
        <v-btn outlined class="today-button" @click="today = new Date()">Today</v-btn>

        <div class="month-controls">
          <v-btn icon @click="prevMonth">&lt;</v-btn>
          <div class="month-label">{{ formattedMonth }}</div>
          <v-btn icon @click="nextMonth">&gt;</v-btn>
        </div>
      </div>

      <v-row class="fill-height">
        <v-col>
          <v-sheet height="600">
            <v-calendar
                ref="calendarRef"
                v-model="today"
                :events="events"
                color="primary"
                type="month"
                hide-header
            >
              <template #event="{ event }">
                <div class="custom-event" :title="event.name">
                  {{ event.name }}
                </div>
              </template>
            </v-calendar>
          </v-sheet>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<style scoped>
.custom-event {
  display: block;
  width: 100%;
  height: 100%;
  background-color: #1976d2;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 500;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.15);
  border: 1px solid #1565c0;
  cursor: pointer;
  box-sizing: border-box;
}

.calendar-header {
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  margin-bottom: 1rem;
  column-gap: 12px;
}

.today-button {
  background-color: white !important;
  color: black !important;
  border-color: black !important;
  font-weight: 600;
  text-transform: none;
  min-width: 90px;
}

.month-controls {
  justify-self: center;
  display: flex;
  align-items: center;
  gap: 12px;
}

.month-label {
  min-width: 140px;
  text-align: center;
  font-weight: 600;
  font-size: 1.25rem;
}
</style>