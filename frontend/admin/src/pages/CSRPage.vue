<template>
  <div class="content">
    <Card title="Certificate Signing Requests">
      <CertificateSigningRequests :certificateSigningRequests="certificateSigningRequests"></CertificateSigningRequests>
    </Card>
  </div>
</template>
<script>
import CertificateSigningRequests from '../custom-components/Tables/CertificateSigningRequestsTable.vue'
import { mapGetters, mapActions } from "vuex";
import Card from "../generic-components/Card/Card.vue";

export default {
    components: {
        Card,
        CertificateSigningRequests
    },
    name: 'CSRPage',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
            certificateSigningRequests: "csr/getAllCsr",
        })
    },
     methods: {
        ...mapActions({
          fetchCsr: "csr/fetchCsr",
    }),
  },
  mounted() {
    this.fetchCsr();
  },
}
</script>
