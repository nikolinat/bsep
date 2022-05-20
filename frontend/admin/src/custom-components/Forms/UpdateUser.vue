<template>
  <Form>
    <form-row>
      <div class="col-6">
        <text-input label="Name" v-model="user.name" type="text" />
      </div>
      <div class="col-6">
        <text-input label="Last name" v-model="user.email" type="text" />
      </div>
    </form-row>

    <form-row>
      <div class="col-6">
        <text-input label="Username" v-model="user.email" type="text" />
      </div>
      <div class="col-6">
        <text-input label="Email" v-model="user.email" type="text" :isValid="validateEmail(newUser.email)"
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
        :v-model="user.roles"
      />
    </form-row>

    <Button @click="handleUpdateBtn">Update</Button>
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
import { validateEmail } from '../../utils/validation'

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
      showErrorMessage: false
    };
  },

  computed: {
    ...mapGetters({
      result: "users/getResult",
    }),
  },

  props: {
    user: {
      type: Object,
    },
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
  },

  methods: {
    ...mapActions({
      updateUser: "users/updateUser",
    }),

    handleUpdateBtn() {
      this.updateUser(this.user);
    },

    validateEmail(email) {
        return validateEmail(email);
    },
  },
};
</script>
