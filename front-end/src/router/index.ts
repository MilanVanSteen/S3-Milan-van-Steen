import { createRouter, createWebHistory } from 'vue-router'
import CalendarList from '../components/CalendarList.vue'
import PersonalCalendars from '../components/PersonalCalendars.vue'

const routes = [
    {
        path: '/calendarList/:userID',
        name: 'CalendarList',
        component: CalendarList,
    },
    {
        path: '/personalCalendars/:userID',
        name: 'PersonalCalendars',
        component: PersonalCalendars,
    },
    {
        path: '/calendar/:userID',
        name: 'VuetifyCalendar',
        component: () => import('../components/CalendarView.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
