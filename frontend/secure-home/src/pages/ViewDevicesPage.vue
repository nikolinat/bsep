<template>
    <div class="content">
        <Card title='Devices'>
            <DevicesTable v-if="devices !== null" :devices="devices" />
        </Card>
    </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'
import Card from '../generic-components/Card/Card.vue'
import DevicesTable from '../custom-components/Tables/DevicesTable.vue'

export default {
    components: {
        Card,
        DevicesTable
    },
    name: 'RealEstates',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
            devices: 'devices/getDevices',
        })
    },
    methods: {
        ...mapActions({
            fetchDevicesForRealEstate: 'devices/fetchDevicesForRealEstate',
        }),
    },
    mounted() {
        this.fetchDevicesForRealEstate(this.$route.params.id);
    }
}
</script>
