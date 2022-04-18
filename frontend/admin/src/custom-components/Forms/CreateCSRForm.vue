<template>
    <Form @submit="onSubmit($event)">
        <form-row>
            <div class="col-12">
                <text-input 
                    label="Email"
                    v-model="csr.email"
                    type="text"
                    :isValid="!!csr.email"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="Email must be provided."
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Common name"
                    v-model="csr.commonName"
                    :isValid="!!csr.commonName"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="Common name must be provided."
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Organization"
                    v-model="csr.organization"
                    :isValid="!!csr.organization"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="Organization must be provided."
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Organization unit"
                    v-model="csr.organizationUnit"
                    :isValid="!!csr.organizationUnit"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="Organization unit must be provided."
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Given name"
                    v-model="csr.givenName"
                    :isValid="!!csr.givenName"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="Given name must be provided."
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Surname"
                    v-model="csr.surname"
                    :isValid="!!csr.surname"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="Surname must be provided."
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Country"
                    v-model="csr.country"
                    :isValid="csr.country.length == 2"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="Country must have only 2 characters."
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="User id"
                    v-model="csr.userId"
                    :isValid="!!csr.userId"
                    :showErrorMessage="showErrorMessage"
                    errorMessage="User id must be provided."
                    type="text"
                />
            </div>
        </form-row>

        <Button @click="showErrorMessage = true" type="submit">Create</Button>
    </Form>
</template>

<script>

import Button from '../../generic-components/Form/Button.vue'
import Form from '../../generic-components/Form/Form.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'
import { mapActions, mapGetters } from 'vuex'
import toastr from 'toastr'

export default {
   components: {
       Form,
       FormRow,
       TextInput,
       Button
    },

    data: function() {
        return {
          csr: {
            email: '',
            commonName: '',
            organization: '',
            organizationUnit: '',
            givenName: '',
            surname: '',
            country: '',
            userId: ''
          },

          showErrorMessage: false
        }
    },

    computed: {
        ...mapGetters({
            result: "csr/getResult",
            }),
    },

    watch: {
        result({ message, ok, label }) {
            if (label === "create") {
                if (ok) {
                toastr.success(message);
                this.fetchValidCertificates();
                } else {
                toastr.error(message);
                }
            }
            },
    },

    methods: {
        ...mapActions({ 
            createCsr: 'csr/createCsr',
        }),

        onSubmit(e) {
            e.preventDefault();
            this.createCsr(this.csr)
        }
    }
}

</script>
