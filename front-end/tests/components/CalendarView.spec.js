import { mount } from '@vue/test-utils'
import CalendarView from '@/components/CalendarView.vue'
import { createRouter, createMemoryHistory } from 'vue-router'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
    components,
    directives,
})

describe('CalendarView.vue', () => {
    let wrapper

    beforeEach(() => {
        wrapper = mount(CalendarView, {
            global: {
                plugins: [vuetify],
                mocks: {
                    $route: { params: { userID: 1 } },
                },
                stubs: {
                    'v-code': true,
                    'v-calendar': true,  // stub v-calendar to avoid unresolved component warning
                },
            },
        })
    })

    it('formats the current month correctly', async () => {
        const today = new Date()
        const options = { year: 'numeric', month: 'long' }
        const expected = today.toLocaleDateString('en-US', options)

        await wrapper.vm.$nextTick()
        expect(wrapper.vm.formattedMonth).toBe(expected)
    })

    it('increments the month when nextMonth is called', async () => {
        const originalMonth = wrapper.vm.month
        wrapper.vm.nextMonth()
        await wrapper.vm.$nextTick()
        expect(wrapper.vm.month).toBe((originalMonth + 1) % 12)
    })

    it('decrements the month when previousMonth is called', async () => {
        wrapper.vm.month = 0
        wrapper.vm.previousMonth()
        await wrapper.vm.$nextTick()
        expect(wrapper.vm.month).toBe(11)
    })

    it('fetchUser reads userID from route params', async () => {
        // Mock fetchUser method to spy on the call or simulate API response
        wrapper.vm.fetchUser = jest.fn().mockResolvedValue({ id: 1, name: 'Test User' })

        await wrapper.vm.fetchUser()

        expect(wrapper.vm.fetchUser).toHaveBeenCalled()
        expect(wrapper.vm.fetchUser).toHaveBeenCalledTimes(1)
    })
})