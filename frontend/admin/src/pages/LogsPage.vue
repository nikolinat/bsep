<template>
    <div class="content">
        <Card title='Search logs'>
            <LogsSearchForm />
        </Card>
        <Card>
            <LogsTable />
        </Card>
    </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'
import Card from '../generic-components/Card/Card.vue'
import LogsTable  from '../custom-components/Tables/LogsTable.vue'
import LogsSearchForm from '../custom-components/Forms/LogsSearchForm.vue'

export default {
    components: {
        Card,
        LogsTable,
        LogsSearchForm
    },
    name: 'Logs',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
           
        })
    },
    methods: {
        ...mapActions({
            fetchLogs: 'log/fetchLogs',
        }),
    },
    mounted() {
        this.fetchLogs();
    }
}
</script>
