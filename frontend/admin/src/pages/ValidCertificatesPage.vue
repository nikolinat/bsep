<template>
    <div class="content">
        <Card title='Valid certificates'>
            <ValidCertificatesTable  :certificates="validCertificates" />
        </Card>
    </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'
import Card from '../generic-components/Card/Card.vue'
import ValidCertificatesTable from '../custom-components/Tables/ValidCertificatesTable.vue'

export default {
    components: {
        Card,
        ValidCertificatesTable
    },
    name: 'ValidCertificates',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
            validCertificates: 'certificates/getValidCertificates',
        })
    },
    methods: {
        ...mapActions({
            fetchValidCertificates: 'certificates/fetchValidCertificates',
        }),
    },
    mounted() {
        this.fetchValidCertificates();
    }
}
</script>
