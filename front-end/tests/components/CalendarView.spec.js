import { createRouter, createWebHistory } from 'vue-router'
import { mount } from '@vue/test-utils'
import CalendarView from '@/components/CalendarView.vue'
import { vi } from 'vitest'

// Mock the Client class used in CalendarView.vue
vi.mock('@/API.ts', () => {
    return {
        Client: vi.fn().mockImplementation(() => {
            return {
                getUserById: vi.fn(() => Promise.resolve({ userID: 1, name: 'Test User' })),
                getCalendarsByUserID: vi.fn(() =>
                    Promise.resolve([
                        {
                            id: 1,
                            events: [
                                {
                                    id: 100,
                                    name: 'Mock Event',
                                    startDate: '2025-06-10',
                                    endDate: '2025-06-12',
                                },
                            ],
                        },
                    ])
                ),
            }
        }),
    }
})

const routes = [
    {
        path: '/calendar/:userID',
        name: 'CalendarView',
        component: CalendarView,
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

describe('CalendarView.vue', () => {
    let wrapper

    beforeEach(async () => {
        await router.push('/calendar/1')
        await router.isReady()

        wrapper = mount(CalendarView, {
            global: { plugins: [router] },
        })

        // Wait for async setup & rendering
        await wrapper.vm.$nextTick()
        await wrapper.vm.$nextTick()
    })

    afterEach(() => {
        vi.clearAllMocks()
    })

    it('formats the current month correctly', () => {
        const today = new Date()
        const options = { year: 'numeric', month: 'long' }
        const expected = today.toLocaleDateString('en-US', options)
        expect(wrapper.vm.formattedMonth).toBe(expected)
    })

    it('increments the month', async () => {
        const originalMonth = wrapper.vm.today.getMonth()
        wrapper.vm.nextMonth()
        await wrapper.vm.$nextTick()
        const expectedMonth = (originalMonth + 1) % 12
        expect(wrapper.vm.today.getMonth()).toBe(expectedMonth)
    })

    it('decrements the month', async () => {
        wrapper.vm.today = new Date(2025, 0, 15)
        wrapper.vm.prevMonth()
        await wrapper.vm.$nextTick()
        expect(wrapper.vm.today.getMonth()).toBe(11)
    })

    it('fetches user and sets currentUser correctly', async () => {
        expect(wrapper.vm.currentUser).toEqual({ userID: 1, name: 'Test User' })
    })

    it('fetches and processes events', async () => {
        expect(wrapper.vm.events.length).toBeGreaterThan(0)
        expect(wrapper.vm.events[0]).toHaveProperty('name', 'Mock Event')
    })

    it('shows loading message initially', async () => {
        // Mount a fresh wrapper to catch loading state
        const loadingWrapper = mount(CalendarView, {
            global: { plugins: [router] },
        })
        expect(loadingWrapper.text()).toContain('Loading events...')
    })

    it('resets to current date when clicking "Today" button', async () => {
        // Set today to a fixed date far in the past
        wrapper.vm.today = new Date(2020, 0, 1)
        await wrapper.vm.$nextTick()

        const todayButton = wrapper.find('button.today-button')
        await todayButton.trigger('click')
        await wrapper.vm.$nextTick()

        const now = new Date()
        expect(wrapper.vm.today.getFullYear()).toBe(now.getFullYear())
        expect(wrapper.vm.today.getMonth()).toBe(now.getMonth())
    })
})
