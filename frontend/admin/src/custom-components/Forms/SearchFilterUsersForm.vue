<template>
    <Form>
        <form-row>
            <div class="col-6">
                <text-input 
                    label="Name"
                    v-model="searchFilterDto.name"
                    type="text"
                />
            </div>
            <div class="col-6">
                <text-input 
                    label="Last name"
                    v-model="searchFilterDto.lastName"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <div class="col-6">
                <text-input 
                    label="Username"
                    v-model="searchFilterDto.username"
                    type="text"
                />
            </div>
            <div class="col-6">
                <text-input 
                    label="Email"
                    v-model="searchFilterDto.email"
                    type="text"
                />
            </div>
        </form-row>

        <form-row>
            <MultiSelectOptionInput
                class="col-6"
                label="Select user role"
                :showLabel="false"
                :options="roles"
                v-model="searchFilterDto.roles"
            />
        </form-row>

        <Button @click="handleSearchAndFilter">Search</Button>
    </Form>
</template>

<script>

import Button from '../../generic-components/Form/Button.vue'
import Form from '../../generic-components/Form/Form.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'
import MultiSelectOptionInput from '../../generic-components/Form/MultiSelectOptionInput.vue'
import { mapGetters, mapActions } from "vuex";
import toastr from 'toastr';

export default {
   components: {
       Form,
       FormRow,
       TextInput,
       Button,
       MultiSelectOptionInput
    },

    data: function() {
        return {
            roles: [
                {
                label: "ROLE_ADMIN",
                value: 1,
                },
                {
                label: "ROLE_HOUSE_OWNER",
                value: 2,
                },
                {
                label: "ROLE_TENANT",
                value: 3,
                },
            ],
            searchFilterDto: {
                name: "",
                lastName: "",
                username: "",
                email: "",
                roles: [1, 2, 3]

            }
        }
    },

    computed: {
        ...mapGetters({
            users: "users/getUsers",
            result: "users/getResult"
            }),
    },

    watch: {
        result({ message, ok, label }) {
        if (label === "delete") {
            if (ok) {
                toastr.success("User deleted");
            } else {
                toastr.error(message);
            }
        }
        }
    },

    methods: {
        ...mapActions({
            fetchUsers: "users/fetchUsers",
        }),

        handleSearchAndFilter() {
            this.fetchUsers(this.searchFilterDto);
        }
    },

    mounted() {
        this.fetchUsers(this.searchFilterDto);
    }
}

</script>
