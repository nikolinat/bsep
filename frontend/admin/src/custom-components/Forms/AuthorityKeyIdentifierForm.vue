<template>
    <div class="col-12" style="padding-left: 0px">
        <SelectOptionInput
            class="col-6"
            :label="'Authority Cert Issuer (General Names)'"
            :showLabel="false"
            :options="options"
            v-model="currentOption"
        />

        <Form @submit="onSubmit($event, currentOption)" v-if="currentOption==0">
            <form-row>
                <text-input 
                    label="DNS Name"
                    v-model="addedOptions.dnsName"
                    :isValid="!!addedOptions.dnsName"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="DNS must be provided."
                    type="text"
                />
            </form-row>
            <form-row>
                <Button @click="showErrorMessage = true" type="submit">Add</Button>
            </form-row>
        </Form>
    </div>
</template>

<script>

import { mapActions } from 'vuex'
import SelectOptionInput from '../../generic-components/Form/SelectOptionInput.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import Button from '../../generic-components/Form/Button.vue'

export default {
   components: {
       SelectOptionInput,
       TextInput,
       FormRow,
       Button
    },
    props: {
      
    },

    data: function() {
        return {
          options: [
              {
                  label: "DNS Name",
                  value: 0
              },
              {
                  label: "IP Address",
                  value: 1
              },
              {
                  label: "Register ID",
                  value: 2
              },
              {
                  label: "RFC 822 Name",
                  value: 3
              },
              {
                  label: "URI",
                  value: 4
              },
              {
                  label: "UPN",
                  value: 5
              }
          ],
        addedOptions: {
            dnsName: '',
            ipAddress: '',
            registerId: '',
            rfc822Name: '',
            uri: '',
            upn: ''
        },
        currentOption: null,
        showErrorMessage: false
        }
    },

    computed: {

    },

    watch: {
        currentOption(option) {
            this.currentOption = option
        }
    },

    methods: {
        ...mapActions({ 

        }),

        onSubmit(e, option) {
            e.preventDefault()
            console.log(option)
        }
    }
}

</script>
