<template>
    <div class="content">
        <Card title='Alarms'>
            <AlarmsTable v-if="alarms !== null" :alarms="alarms" />
        </Card>
    </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'
import Card from '../generic-components/Card/Card.vue'
import AlarmsTable from '../custom-components/Tables/AlarmsTable.vue'

export default {
    components: {
        Card,
        AlarmsTable
    },
    name: 'RealEstates',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
            alarms: 'alarms/getAlarms',
        })
    },
    methods: {
        ...mapActions({
            fetchAlarmsForRealEstate: 'alarms/fetchAlarmsForRealEstate',
        }),
    },
    mounted() {
        this.fetchAlarmsForRealEstate(this.$route.params.id);
    }
}
</script>
