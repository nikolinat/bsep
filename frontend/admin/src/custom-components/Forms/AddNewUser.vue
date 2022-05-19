<template>
  <Form>
    <form-row>
      <div class="col-6">
        <text-input label="Name" v-model="newUser.name" type="text" />
      </div>
      <div class="col-6">
        <text-input label="Last name" v-model="newUser.lastName" type="text" />
      </div>
    </form-row>

    <form-row>
      <div class="col-6">
        <text-input label="Username" v-model="newUser.username" type="text" />
      </div>
      <div class="col-6">
        <text-input label="Email" v-model="newUser.email" type="text" :isValid="validateEmail(newUser.email)"
                :showErrorMessage="showErrorMessage"
                errorMessage="Please enter valid email" />
      </div>
    </form-row>

    <form-row>
      <div class="col-6">
        <text-input
          label="Password"
          v-model="newUser.password"
          type="password"
          :isValid="validatePassword(newUser.password)"
                :showErrorMessage="showErrorMessage"
                errorMessage="Password must have at least 13 characters, special character and a number."
        />
      </div>
      <div class="col-6">
        <text-input
          label="Repeat password"
          v-model="password2"
          type="password"
        />
      </div>
    </form-row>

    <form-row>
      <MultiSelectOptionInput
        class="col-6"
        label="Select user role"
        :showLabel="false"
        :options="roles"
        v-model="newUser.roles"
      />
    </form-row>

    <Button @click="handleCreateBtn">Create</Button>
  </Form>
</template>

<script>
import Button from "../../generic-components/Form/Button.vue";
import Form from "../../generic-components/Form/Form.vue";
import FormRow from "../../generic-components/Form/FormRow.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";
import MultiSelectOptionInput from "../../generic-components/Form/MultiSelectOptionInput.vue";
import { mapGetters, mapActions } from "vuex";
import toastr from "toastr";
import { validateEmail, validatePassword,  } from '../../utils/validation'


export default {
  components: {
    Form,
    FormRow,
    TextInput,
    Button,
    MultiSelectOptionInput,
  },

  data: function () {
    return {
      roles: [
        {
          label: "ROLE_HOUSE_OWNER",
          value: 2,
        },
        {
          label: "ROLE_TENANT",
          value: 3,
        },
      ],
      newUser: {
        name: "",
        lastName: "",
        username: "",
        email: "",
        password: "",
        roles: [1, 2, 3],
      },
      password2: "",
    };
  },

  computed: {
    ...mapGetters({
      result: "users/getUsers",
    }),
  },

  watch: {},

  methods: {
    ...mapActions({
      createUser: "users/createUser",
    }),

    handleCreateBtn() {
      if (this.newUser.password == this.password2) {
        this.createUser(this.newUser);
        toastr.success("User created");
      } else {
        toastr.error("Invalid password");
      }
      
    },
     validateEmail(email) {
            return validateEmail(email);
        },
        validatePassword(password) {
            return validatePassword(password);
        },
  },

  mounted() {},
};
</script>
