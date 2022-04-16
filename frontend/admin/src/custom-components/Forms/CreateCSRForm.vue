<template>
    <Form>
        <form-row>
            <div class="col-12">
                <text-input 
                    label="Email"
                    v-model="csr.email"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Common name"
                    v-model="csr.commonName"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Organization"
                    v-model="csr.organization"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Organization unit"
                    v-model="csr.organizationUnit"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Given name"
                    v-model="csr.givenName"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Surname"
                    v-model="csr.surname"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Country"
                    v-model="csr.country"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="User id"
                    v-model="csr.userId"
                    type="text"
                />
            </div>
        </form-row>

        <Button @click="handleCreateCsr">Create</Button>
    </Form>
</template>

<script>

import Button from '../../generic-components/Form/Button.vue'
import Form from '../../generic-components/Form/Form.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'
import { mapActions } from 'vuex'

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
          }
        }
    },

    computed: {

    },

    watch: {

    },

    methods: {
        ...mapActions({ 
            createCsr: 'csr/createCsr',
        }),

        handleCreateCsr() {
            this.createCsr(this.csr)
        }
    }
}

</script>
