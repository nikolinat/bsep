<template>
  <Form @submit="onSubmit($event)">
    <form-row>
      <div class="col-6">
        <text-input label="Name" v-model="user.name" type="text"
                :isValid="validateStringWithLettersOnly(user.name)"
                :showErrorMessage="showErrorMessage"
                errorMessage="Name must have only letters" />
      </div>
      <div class="col-6">
        <text-input label="Last name" v-model="user.lastName" type="text"
                :isValid="validateStringWithLettersOnly(user.lastName)"
                :showErrorMessage="showErrorMessage"
                errorMessage="Last name must have only letters." />
      </div>
    </form-row>

    <form-row>
      <div class="col-6">
        <text-input label="Username" v-model="user.username" type="text"
                :isValid="validateStringWithLettersAndNumbersOnly(user.username)"
                :showErrorMessage="showErrorMessage"
                errorMessage="Username must have only letters an numbers."  />
      </div>
      <div class="col-6">
        <text-input label="Email" v-model="user.email" type="text" :isValid="validateEmail(user.email)"
                :showErrorMessage="showErrorMessage"
                errorMessage="Please enter valid email" />
      </div>
    </form-row>

    <form-row>
      <MultiSelectOptionInput
        class="col-6"
        label="Select user role"
        :showLabel="false"
        :options="roles"
        v-model="choosenRoles"
      />
    </form-row>

    <Button @click="showErrorMessage = true" type="submit">Update</Button>
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
import { validateEmail, validateStringWithLettersOnly, validateStringWithLettersAndNumbersOnly } from '../../utils/validation'

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
          value: "ROLE_HOUSE_OWNER",
        },
        {
          label: "ROLE_TENANT",
          value: "ROLE_TENANT",
        },
      ],
      showErrorMessage: false,
      choosenRoles: [],
      user: {}
    };
  },

  computed: {
    ...mapGetters({
      result: "users/getResult",
      getUser: "users/getUser"
    }),
  },

  created() {
  },

  watch: {
    result({ message, ok, label }) {
      if (label === "update") {
          if (ok) {
            toastr.success("User updated");
          } else {
            toastr.error(message);
        }
      }
    },
    getUser(user) {
      this.user = user;
    },
    choosenRoles(roles) {
      console.log("TU SAM")
      this.choosenRoles = roles;
    }
  },

  methods: {
    ...mapActions({
      updateUser: "users/updateUser",
      fetchUser: "users/fetchUser"
    }),

    onSubmit(e) {
      e.preventDefault();
      console.log(this.choosenRoles);
      if(this.choosenRoles.length > 0) {
        this.user.roles = this.choosenRoles;
      }
      console.log(this.user.roles)
      this.updateUser(this.user);
    },

    validateEmail(email) {
        return validateEmail(email);
    },

    validateStringWithLettersAndNumbersOnly(username) {
      return validateStringWithLettersAndNumbersOnly(username);
    },

    validateStringWithLettersOnly(name) {
      return validateStringWithLettersOnly(name);
    }
  },
  mounted() {
    this.fetchUser(this.$route.params.id);
  } 
};
</script>
