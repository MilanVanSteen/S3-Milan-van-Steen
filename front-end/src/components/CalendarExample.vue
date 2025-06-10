<script setup>
import { ref } from 'vue'

const today = ref(new Date())

// Example multi-day events
const events = ref([
  {
    name: 'Multi-day Event',
    start: new Date('2025-06-03T00:00:00'),
    end: new Date('2025-06-08T23:59:59.999'),
  },
  {
    name: 'Single Day Event',
    start: new Date('2025-06-10T00:00:00'),
    end: new Date('2025-06-10T23:59:59.999'),
  }
])
</script>

<template>
  <v-container>
    <v-sheet height="600">
      <v-calendar
          v-model="today"
          :events="events"
          type="month"
          color="primary"
      />
    </v-sheet>
  </v-container>
</template>
