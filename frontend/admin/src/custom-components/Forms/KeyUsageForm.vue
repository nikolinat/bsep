<template>
    <MultiSelectOptionInput class="col-12"
        label="Select key usages"
        :options="options"
        v-model="checkedOptions"
    />
</template>

<script>

import { mapActions } from 'vuex'
import MultiSelectOptionInput from '../../generic-components/Form/MultiSelectOptionInput.vue'

// const $ = window.$;

export default {
   components: {
        MultiSelectOptionInput
    },
    props: {
        defaultChecked: {
            type: Array,
            default: () => []
        }
    },

    data: function() {
        return {
          options: [
              {
                  label: "Digital Signature",
                  value: 128
              },
              {
                  label: "Non Repudiation",
                  value: 64
              },
              {
                  label: "Key Encipherment",
                  value: 32
              },
              {
                  label: "Data Encipherment",
                  value: 16
              },
              {
                  label: "Key Agreement",
                  value: 8
              },
              {
                  label: "CRL Sign",
                  value: 2
              },
              {
                  label: "Encipher Only",
                  value: 1
              },
              {
                  label: "Decipher Only",
                  value: 32768
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
            this.$emit("addedKey", this.checkedOptions)
        },
        defaultChecked(checked) {
            this.checkedOptions = checked;
            this.defaultChecked = checked;
        }
    },

    methods: {
        ...mapActions({ 

        }),
    },

    mounted() {
        const checked = []
        this.checkedOptions.forEach(option => checked.push(option));
        this.checkedOptions = checked;
    }
}

</script>
