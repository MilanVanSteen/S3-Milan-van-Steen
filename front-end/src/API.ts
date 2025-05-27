// API.ts
import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

export class Client {
    // Calendar API
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

    async getCalendarsByUserID(userID: number) {
        const response = await axios.get(`${BASE_URL}/calendar/getCalendarsByUserID`, {
            params: { userID }
        });
        return response.data;
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

    // User API
    async getAllUsers() {
        const response = await axios.get(`${BASE_URL}/user/getAllUsers`)
        return response.data
    }

    async getUserById(userId: number) {
        const response = await axios.get(`${BASE_URL}/user/getUserById`, {
            params: { userID: userId }
        })
        return response.data
    }

    async createUser(userData: any) {
        const response = await axios.post(`${BASE_URL}/user/createUser`, userData)
        return response.data
    }

    async deleteUser(userId: number) {
        const response = await axios.delete(`${BASE_URL}/user/deleteUser`, {
            params: { userID: userId }
        })
        return response.data
    }
}
