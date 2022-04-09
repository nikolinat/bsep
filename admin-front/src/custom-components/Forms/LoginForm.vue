<template>
    <Form>
        <form-row>
            <div class="col-12">
                <text-input 
                    label="Username"
                    v-model="username"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-12">
                <text-input 
                    label="Password"
                    v-model="password"
                    type="password"
                />
            </div>
        </form-row>

        <Button @click="handleLoginClick">Sign In</Button>

        <div class="row mt-4">
            <p class="text-left col-6">
                <router-link to="/patient-registration">Registration</router-link>
            </p>
        </div>
    </Form>
</template>

<script>

import Button from '../../generic-components/Form/Button.vue'
import Form from '../../generic-components/Form/Form.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'

export default {
   components: {
       Form,
       FormRow,
       TextInput,
       Button
    },

    data: function() {
        return {
            username: '',
            password: ''
        }
    },

    computed: {

    },

    watch: {

    },

    methods: {
        handleLoginClick() {

        }
    }
}

</script>
