<template>
  <Form @submit="onSubmit($event)">
    <form-row>
      <div class="col-12">
        <text-input
          label="Require Explicit Policy Skip Certificates"
          v-model="pce.requireExplicitPolicy"
          type="number"
          :isValid="
            pce.requireExplicitPolicy != null ||
            pce.inhabitPolicyMapping != null
          "
          :showErrorMessage="showErrorMessage"
          errorMessage="A value is required for at leaste one cell."
        />
      </div>
      <div class="col-12">
        <text-input
          label="Inhabit Policy Mapping Skip Certificates"
          v-model="pce.inhabitPolicyMapping"
          type="number"
          :isValid="
            pce.requireExplicitPolicy != null ||
            pce.inhabitPolicyMapping != null
          "
          :showErrorMessage="showErrorMessage"
          errorMessage="A value is required for at leaste one cell."
        />
      </div>
    </form-row>
    <Button @click="showErrorMessage = true" type="submit"
      >Add Policy Constraint</Button
    >
  </Form>
</template>

<script>
import { mapActions } from "vuex";
import Button from "../../generic-components/Form/Button.vue";
import Form from "../../generic-components/Form/Form.vue";
import FormRow from "../../generic-components/Form/FormRow.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";

export default {
  components: { Form, FormRow, TextInput, Button },
  props: {
    defaultChecked: {
      type: Array,
      default: () => [],
    },
  },

  data: function () {
    return {
      pce: {
        requireExplicitPolicy: null,
        inhabitPolicyMapping: null,
      },

      showErrorMessage: false,
    };
  },

  computed: {},

  watch: {
    checkedOptions(checked) {
      this.checkedOptions = checked;
      this.$emit("addedKey", this.checkedOptions);
    },
    defaultChecked(checked) {
      this.checkedOptions = checked;
      this.defaultChecked = checked;
    },
  },

  methods: {
    ...mapActions({
      // createCsr: 'csr/createCsr',
    }),

    onSubmit(e) {
      e.preventDefault();
      // this.createCsr(this.csr)
    },

    onCancle() {
      this.pce.requireExplicitPolicy = null;
      this.pce.inhabitPolicyMapping = null;
    },
  },

  mounted() {},
};
</script>
