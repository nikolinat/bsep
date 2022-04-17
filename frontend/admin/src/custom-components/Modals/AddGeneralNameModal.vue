<template>
    <Modal title="Add new General Name" :modalBoxId="modalBoxId" >
        <div slot="body">
            <SelectOptionInput
                class="col-12"
                id="selektovan"
                :label="'Authority Cert Issuer (General Names)'"
                :showLabel="true"
                :options="options"
                v-model="currentOption"
            />

            <Form @submit="onSubmit($event, currentOption)" class="col-10" style="margin-left: 4%" v-if="currentOption !== null && currentOption !== ''">
                <form-row>
                    <text-input 
                        v-model="option.enteredValue"
                        type="text"
                    />
                </form-row>
                <form-row>
                    <Button @click="showErrorMessage = true" type="submit">Add</Button>
                </form-row>
            </Form>
        </div>
    </Modal>
</template>

<script>

import Modal from '../../generic-components/Modal/Modal'
import SelectOptionInput from '../../generic-components/Form/SelectOptionInput.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import Button from '../../generic-components/Form/Button.vue'

export default {

    props: [
        'modalBoxId',
    ],

    components: {
        Modal,
        SelectOptionInput,
       TextInput,
       FormRow,
       Button
    },
    
    data: function() {
        return {
          options: [
              {
                  label: "DNS Name",
                  value: "DNSName"
              },
              {
                  label: "IP Address",
                  value: "X400Address"
              },
              {
                  label: "Register ID",
                  value: "RegisteredID"
              },
              {
                  label: "RFC 822 Name",
                  value: "Rfc822Name"
              },
              {
                  label: "URI",
                  value: "UniformResourceIdentifier"
              },
              {
                  label: "UPN",
                  value: "OtherName"
              }
          ],
        option: {
            label: '',
            value: null,
            enteredValue: ''
        },
        currentOption: null,
        showErrorMessage: false
        }
    },

    watch: {
        currentOption(option) {
            this.currentOption = option;
        }
    },

    methods: {
        onSubmit(e, option) {
            e.preventDefault()
            this.option.value = option;
            this.options.forEach(o => {
                if(o.value === option) {
                    this.option.label = o.label;
                }
            })

            this.$emit('newGeneralName', this.option)
        }
    },

    mounted() {
    }
}
</script>
