<template>
    <MultiSelectOptionInput class="col-12"
        label="Select extended key usages"
        :options="options"
        v-model="checkedOptions"
    />
</template>

<script>

import { mapActions } from 'vuex'
import MultiSelectOptionInput from '../../generic-components/Form/MultiSelectOptionInput.vue'

const $ = window.$;

export default {
   components: {
        MultiSelectOptionInput
    },
    props: {
        defaultChecked: {
            type: Array
        }
    },

    data: function() {
        return {
          options: [
              {
                  label: "TLS Web Server Authentication",
                  value: "1.3.6.1.5.5.7.3.1"
              },
              {
                  label: "TLS Web Client Authentication",
                  value: "1.3.6.1.5.5.7.3.2"
              },
              {
                  label: "Code Signing",
                  value: "1.3.6.1.5.5.7.3.3"
              },
              {
                  label: "Document Signing",
                  value: "1.3.6.1.4.1.311.10.3.12"
              },
              {
                  label: "Adobe PDF Signing",
                  value: "1.2.840.113583.1.1.5"
              },
              {
                  label: "TSL Signing",
                  value: "0.4.0.2231.3.0"
              },
              {
                  label: "E-mail Protection",
                  value: "1.3.6.1.5.5.7.3.4"
              },
              {
                  label: "Encrypted File System",
                  value: "1.3.6.1.4.1.311.10.3.4"
              },
              {
                  label: "IP Security End System",
                  value: "1.3.6.1.5.5.7.3.5"
              },
              {
                  label: "IP Security Tunnel Termination",
                  value: "1.3.6.1.5.5.7.3.6"
              },
              {
                  label: "IP Security User",
                  value: "1.3.6.1.5.5.7.3.7"
              },
              {
                  label: "Time Stamping",
                  value: "1.3.6.1.5.5.7.3.8"
              },
              {
                  label: "OCSP Signing",
                  value: "1.3.6.1.5.5.7.3.9"
              },
              {
                  label: "Smartcard Logon",
                  value: "1.3.6.1.4.1.311.20.2.2"
              },
              {
                  label: "Any Extended Key Usage",
                  value: "2.5.29.37.0"
              },
          ],
        checkedOptions: this.defaultChecked
        }
    },

    computed: {

    },

    watch: {
        checkedOptions(checked) {
            this.checkedOptions = checked;
            this.$emit("addedExtendedKey", this.checkedOptions)
        },
        defaultChecked(checked) {
            this.defaultChecked = checked
            this.checkedOptions = checked;
        }
    },

    methods: {
        ...mapActions({ 

        }),
    },
     
    mounted() {
        setTimeout(() => {
                $('.selectpicker').selectpicker('refresh');
        }, 100);
    }
}

</script>
