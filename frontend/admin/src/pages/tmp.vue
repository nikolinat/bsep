<template>
  <div class="col-md-6 col-lg-6 col-sm-10">
    <card :title="'Sign in'">
      <NewCertificate />
    </card>
  </div>
</template>

<script>
import NewCertificate from "../generic-components/Form/NewCertificate.vue";

export default {
  components: {
    NewCertificate,
  },
};
</script>
