// vitest.setup.ts
import { config } from '@vue/test-utils'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi'

import 'vuetify/styles'

const vuetify = createVuetify({
    components,
    directives,
    icons: {
        defaultSet: 'mdi',
        aliases,
        sets: { mdi },
    },
})

// Register Vuetify globally for all tests
config.global.plugins = [vuetify]

// Optional: Stub 3rd-party components used in many tests
config.global.stubs = {
    'v-calendar': true,
    'v-code': true,
}
