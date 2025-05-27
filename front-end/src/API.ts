// API.ts
import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

export class Client {
    async getAllCalendars() {
        const response = await axios.get(`${BASE_URL}/calendar/getAllCalendars`)
        return response.data
    }

    async getCalendarById(calendarId: number) {
        const response = await axios.get(`${BASE_URL}/calendar/getCalendarById`, {
            params: { calendarID: calendarId }
        })
        return response.data
    }

    async createCalendar(calendarData: any) {
        const response = await axios.post(`${BASE_URL}/calendar/createCalendar`, calendarData)
        return response.data
    }

    async deleteCalendar(calendarId: number) {
        const response = await axios.delete(`${BASE_URL}/calendar/deleteCalendar`, {
            params: { calendarID: calendarId }
        })
        return response.data
    }
}
