<template>
    <div class="content">
        <Card title='Real estates'>
            <RealEstateTable  :realEstates="realEstates" />
        </Card>
    </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'
import Card from '../generic-components/Card/Card.vue'
import RealEstateTable from '../custom-components/Tables/RealEstateTable.vue'

export default {
    components: {
        Card,
        RealEstateTable
    },
    name: 'RealEstates',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
            realEstates: 'realestate/getRealEstates',
        })
    },
    methods: {
        ...mapActions({
            fetchRealEstates: 'realestate/fetchRealEstates',
        }),
    },
    mounted() {
        this.fetchRealEstates();
    }
}
</script>
