<template>
  <div class="content">
    <div class="container-fluid">
      <Card title="Valid certificates">
        <CertificateSigningRequests
          :certificateSigningRequests="certificateSigningRequests"
        ></CertificateSigningRequests>
      </Card>
    </div>
  </div>
</template>
<script>
import CertificateSigningRequests from "../custom-components/Tables/certificateSigningRequestsTable.vue";
import { mapGetters, mapActions } from "vuex";
import Card from "../generic-components/Card/Card.vue";
export default {
  certificateSigningRequests: null,
  computed: {
    ...mapGetters({
      certificateSigningRequests: "csr/getAllCsr",
    }),
  },
  methods: {
    ...mapActions({
      fetchCsr: "csr/fetchCsr",
    }),
  },
  mounted() {
    this.fetchCsr();
  },
  components: { CertificateSigningRequests, Card },
  name: "CertificateSigningRequestsPage",
};
</script>
