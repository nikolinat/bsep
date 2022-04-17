<template>
  <div class="content">
    <div class="container-fluid">
      <Card title="Name Constraint Extension">
        <Card title="Permitted Subtrees">
          <NameConstraintForm
            :subtrees="permittedSubtrees"
          ></NameConstraintForm>
        </Card>
        <Card title="Excluded Subtrees">
          <NameConstraintForm :subtrees="excludedSubtrees"></NameConstraintForm>
        </Card>
      </Card>
    </div>
  </div>
</template>
<script>
import NameConstraintForm from "../custom-components/Forms/NameConstraintSubtreesForm.vue";
import { mapGetters, mapActions } from "vuex";
import Card from "../generic-components/Card/Card.vue";
export default {
  certificateSigningRequests: null,
  computed: {
    ...mapGetters({
      //certificateSigningRequests: "csr/getCsr",
    }),
  },
  methods: {
    ...mapActions({
      //fetchCsr: "csr/fetchCsr",
    }),
  },
  mounted() {
    this.fetchCsr();
  },
  components: { NameConstraintForm, Card },
  name: "CertificateSigningRequestsPage",
};
</script>
