<template>
    <div class="content">
        <Card title='Revoked certificates'>
            <RevokedCertificatesTable  :certificates="revokedCertificates" />
        </Card>
    </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'
import Card from '../generic-components/Card/Card.vue'
import RevokedCertificatesTable from '../custom-components/Tables/RevokedCertificatesTable.vue'

export default {
    components: {
        Card,
        RevokedCertificatesTable
    },
    name: 'RevokedCertificates',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
            revokedCertificates: 'certificates/getRevokedCertificates',
        })
    },
    methods: {
        ...mapActions({
            fetchRevokedCertificates: 'certificates/fetchRevokedCertificates',
        }),
    },
    mounted() {
        this.fetchRevokedCertificates();
    }
}
</script>
