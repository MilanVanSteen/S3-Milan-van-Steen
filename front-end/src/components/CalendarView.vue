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
  if (import.meta.env.VITE_ENV === 'ci') {
    // In CI/mock environment, just set a fake user
    currentUser.value = {userID: 2, name: 'Mock User'}
  } else {
    try {
      currentUser.value = await client.getUserById(route.params.userID)
    } catch (err) {
      console.error('Error fetching user:', err)
      throw new Error('User fetch failed')
    }
  }
}

const fetchEvents = async () => {
  try {
    let allEvents = [];

    if (import.meta.env.VITE_ENV === 'ci') {
      console.log("VITE_ENV:", import.meta.env.VITE_ENV);
      // CI mode: load mock events from public/mock/events.json
      try {
        const response = await fetch('/mock/events.json');
        const mockCalendars = await response.json();
        allEvents = mockCalendars
            .filter(calendar => calendar && Array.isArray(calendar.events))
            .flatMap(calendar => calendar.events);
      } catch (error) {
        console.error('Failed to load mock events:', error);
      }
    } else {
      // Normal mode: fetch real data from backend
      const calendars = await client.getCalendarsByUserID(currentUser.value.userID);
      allEvents = calendars
          .filter(calendar => calendar && Array.isArray(calendar.events))
          .flatMap(calendar => calendar.events);
    }

    // Sort events by duration (longest first)
    allEvents.sort((a, b) => {
      const aDuration = (new Date(a.endDate) - new Date(a.startDate)) || 0
      const bDuration = (new Date(b.endDate) - new Date(b.startDate)) || 0
      return bDuration - aDuration
    })

    events.value = allEvents.flatMap(event => {
      const expanded = []
      const start = new Date(event.startDate)
      const end = new Date(event.endDate)
      let d = new Date(start)

      while (d <= end) {
        // Determine which part of the event this day is
        let part = 'middle'
        if (d.toDateString() === start.toDateString()) {
          part = 'start'
        } else if (d.toDateString() === end.toDateString()) {
          part = 'end'
        }

        expanded.push({
          id: event.id,
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
                <div
                    class="custom-event"
                    :title="event.name"
                    :class="`event-part-${event.part}`"
                >
                  <template v-if="event.part === 'start'">
                    <strong>▶</strong> {{ event.name }}
                  </template>
                  <template v-else-if="event.part === 'middle'">
                    <em>•</em>
                  </template>
                  <template v-else-if="event.part === 'end'">
                    <strong>■</strong>
                  </template>
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
  display: flex;
  align-items: center;
  height: 100%;
  width: 100%;
  padding: 4px 8px;
  font-size: 0.75rem;
  font-weight: 500;
  color: white;
  box-sizing: border-box;
  border: 1px solid #1565c0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.15);
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
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

/* start: rounded left, solid border left */
.event-part-start {
  background-color: #1e88e5;
  border-left: 4px solid #0d47a1;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}

/* middle: no left/right border to join seamlessly */
.event-part-middle {
  background-color: #42a5f5;
  opacity: 0.8;
  border-left: none;
  border-right: none;
  margin-left: -1px;
}

/* end: rounded right, solid border right */
.event-part-end {
  background-color: #1565c0;
  border-right: 4px solid #0d47a1;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  margin-left: -1px; /* overlap left border with prev */
}
</style>