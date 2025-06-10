import { createRouter, createWebHistory } from 'vue-router'
import 'core-js/features/promise'
import CalendarList from '../components/CalendarList.vue'
import PersonalCalendars from '../components/PersonalCalendars.vue'
import CalendarExample from "@/components/CalendarExample.vue";

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
    },
    {
        path: '/calendarExample/',
        name: 'CalendarExample',
        component: CalendarExample,
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
