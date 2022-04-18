<template>
  <Table>
    <TableBody>
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
    </TableBody>
    <Button @click="onSubmit" type="submit">Add Policy Constraint</Button>
  </Table>
</template>

<script>
import Button from "../../generic-components/Form/Button.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";
import Table from "../../generic-components/Table/Table.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";

export default {
  components: { TextInput, Button, Table, TableBody },
  props: {
    addedOptions: {
      type: Array,
      default: () => [],
    },
  },

  data: function () {
    return {
      pce: {
        requireExplicitPolicy: 0,
        inhabitPolicyMapping: 0,
      },

      showErrorMessage: false,
    };
  },

  computed: {},

  watch: {
    addedOptions(options) {
      this.addedOptions = options;
    },
  },

  methods: {
    onSubmit(e) {
      e.preventDefault();

      this.option = [
        this.pce.requireExplicitPolicy,
        this.pce.inhabitPolicyMapping,
      ];
      this.$emit("addPolicyConstraint", this.option);
    },
  },

  mounted() {},
};
</script>
