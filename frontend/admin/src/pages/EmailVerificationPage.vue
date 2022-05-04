<template>
  <div class="content">
    <h3>Uspešno ste verifikovali mejl.</h3>
    <h4>Vaš zahtev sada može biti prihvaćen.</h4>
  </div>
</template>
<script>
import { mapGetters, mapActions } from "vuex";

export default {
    components: {
    },
    name: 'EmailVerificationPage',

     data: () => {
        return {
        }
    },
    computed: {
        ...mapGetters({
        })
    },
     methods: {
         ...mapActions({
            verifyEmail: "csr/verifyEmail",
    }),
  },
  mounted() {
      this.verifyEmail(this.$route.params.token)
  },
}
</script>
