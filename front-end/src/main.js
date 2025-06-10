import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { VCalendar } from 'vuetify/labs/VCalendar'

const vuetify = createVuetify({
    components: {
        ...components,
        VCalendar,
    },
    directives,
})

const app = createApp(App)
app.use(router)
app.use(vuetify)

app.mount('#app')
