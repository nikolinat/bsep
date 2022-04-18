<template>
    <div>
        <Form>
            <form-row>
                <div class="col-6">
                    <text-input 
                        label="Valid from"
                        :value="formatDate(certificate.startDate)"
                        type="text"
                        disabled
                    />
                </div>
                <div class="col-6">
                    <text-input 
                        label="Valid until"
                        :value="formatDate(certificate.endDate)"
                        type="text"
                        disabled
                    />
                </div>
            </form-row>

            <form-row>
                <div class="col-12">
                    <label>Subject informations</label>
                </div>
            </form-row>

            <form-row>
                <div class="col-6">
                    <text-input 
                        label="Email"
                        v-model="certificate.subject.email" 
                        type="text"
                        disabled
                    />
                </div>
                <div class="col-6">
                    <text-input 
                        label="Common name"
                        v-model="certificate.subject.commonName"
                        type="text"
                        disabled
                    />
                </div>
            </form-row>

            <form-row>
                <div class="col-6">
                    <text-input 
                        label="Organization"
                        v-model="certificate.subject.organization"
                        type="text"
                        disabled
                    />
                </div>
                <div class="col-6">
                    <text-input 
                        label="Organization unit"
                        v-model="certificate.subject.organizationUnit"
                        type="text"
                        disabled
                     />
                </div>
            </form-row>

            <form-row>
                <div class="col-6">
                    <text-input 
                        label="Given name"
                        v-model="certificate.subject.givenName"
                        type="text"
                        disabled
                    />
                </div>
                <div class="col-6">
                    <text-input 
                        label="Surname"
                        v-model="certificate.subject.surname"
                        type="text"
                        disabled
                    />
                </div>
            </form-row>

            <form-row>
                <div class="col-6">
                    <text-input 
                        label="Country"
                        v-model="certificate.subject.country"
                        type="text"
                        disabled
                    />
                </div>

                <div class="col-6">
                    <text-input 
                        label="User id"
                        v-model="certificate.subject.userId"
                        type="text"
                        disabled
                    />
                </div>
            </form-row>
        </Form>

        <ModalOpener modalBoxId="extensionsModalOpener">
            <Button @click="emitClick()"> Extensions </Button>
        </ModalOpener>
            
    </div>
</template>

<script>
import Form from '../../generic-components/Form/Form.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'
import Button from '../../generic-components/Form/Button.vue'
import ModalOpener from '../../generic-components/Modal/ModalOpener.vue'
// import ExtensionsModal from '../../custom-components/Modals/ExtensionsModal.vue'
import moment from "moment";

export default {
    props: {
        certificate: {}
    },
    mounted() {console.log(this.certificate)},
   components: {
       Form,
       FormRow,
       TextInput,
       Button,
       ModalOpener,
    //    ExtensionsModal
    },
    methods: {
       formatDate(d) {
        return moment(d).format("ll");
        },
        emitClick() {
            this.$emit('openExtensions', this.certificate.extensions)
        }
    }
}
</script>