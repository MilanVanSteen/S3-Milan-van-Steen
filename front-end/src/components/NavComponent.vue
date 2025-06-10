<script setup>
import { ref, onMounted } from 'vue'
import { Client } from '../API.ts'

const client = new Client()
const currentUser = ref(null)
const error = ref(null)

const fetchUser = async () => {
  try {
    currentUser.value = await client.getUserById(2)
  } catch (err) {
    console.error('Error fetching user:', err)
    error.value = 'Failed to fetch user'
  }
}

onMounted(() => {
  fetchUser()
})
</script>

<template>
  <nav class="navbar">
    <a href="/" class="logo">EventFinder</a>

    <div class="nav-links">
      <router-link
          :to="`/CalendarList/${currentUser?.userID ?? ''}`"
          :class="{ disabled: !currentUser }"
      >
        UserCalendars
      </router-link>

      <router-link
          :to="`/PersonalCalendars/${currentUser?.userID ?? ''}`"
          :class="{ disabled: !currentUser }"
      >
        PersonalCalendars
      </router-link>

      <router-link
          :to="`/Calendar/${currentUser?.userID ?? ''}`"
          :class="{ disabled: !currentUser }"
      >
        TestCalendar
      </router-link>

      <router-link
          :to="`/CalendarExample`"
          :class="{ disabled: !currentUser }"
      >
        ExampleCalendar
      </router-link>
    </div>

    <span v-if="currentUser" class="user-id">User ID: {{ currentUser.userID }}</span>
  </nav>
</template>

<style scoped>
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: blanchedalmond;
  padding: 1rem 2rem;
}

.logo {
  font-weight: bold;
  color: black;
  text-decoration: none;
  font-size: 1.5rem;
}
.logo:hover {
  color: darkred;
}

.nav-links {
  display: flex;
  gap: 1.5rem;
}

.nav-links a {
  color: black;
  font-weight: 600;
  text-decoration: none;
  transition: color 0.3s ease;
}

.nav-links a:hover {
  color: darkred;
}

.nav-links a.disabled {
  color: #ccc;
  pointer-events: none;
  cursor: default;
}
</style>
