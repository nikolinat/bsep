<template>
  <div class="content">
    <Card title="Valid certificates">
      <CertificateSigningRequests
        :certificateSigningRequests="certificateSigningRequests"
      ></CertificateSigningRequests>
    </Card>
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
      certificateSigningRequests: "csr/getCsr",
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
};
</script>
