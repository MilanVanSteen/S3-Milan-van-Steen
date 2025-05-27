import { createRouter, createWebHistory } from 'vue-router'
import CalendarList from '../components/CalendarList.vue'

const routes = [
    {
        path: '/',
        name: 'CalendarList',
        component: CalendarList,
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
