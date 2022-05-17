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
        <text-input label="Email" v-model="user.email" type="text" />
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
    };
  },

  computed: {
    ...mapGetters({
      result: "users/getUsers",
    }),
  },

  props: {
    user: {
      type: Object,
    },
  },

  created() {
    console.log(this.user);
  },

  watch: {},

  methods: {
    ...mapActions({
      updateUser: "users/updateUser",
    }),

    handleUpdateBtn() {
      this.updateUser(this.user);
      toastr.success("User updated");
    },
  },
};
</script>
