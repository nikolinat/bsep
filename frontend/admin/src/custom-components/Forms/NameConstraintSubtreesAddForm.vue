<template>
  <Card title="Subtrees">
    <Form @submit="onSubmit($event)">
      <form-row>
        <div class="col-12">
          <text-input
            label="Base"
            v-model="sub.minimum"
            type="text"
            :disabled="true"
          />
        </div>
        <Button>Edit</Button>
        <Button>Clear</Button>
      </form-row>

      <form-row>
        <div class="col-12">
          <text-input
            label="Minimum"
            v-model="sub.minimum"
            type="number"
            :isValid="sub.minimum != null"
            :showErrorMessage="showErrorMessage"
            errorMessage="A value is required for minimum."
          />
        </div>
      </form-row>

      <form-row>
        <div class="col-12">
          <text-input
            label="Maximum"
            v-model="sub.maximum"
            type="number"
            :showErrorMessage="showErrorMessage"
            errorMessage="A value is required for at leaste one cell."
          />
        </div>
      </form-row>
      <Button>Ok</Button>
      <Button>Cancel</Button>
    </Form>
  </Card>
</template>

<script>
import Card from "../../generic-components/Card/Card.vue";
import Button from "../../generic-components/Form/Button.vue";
import Form from "../../generic-components/Form/Form.vue";
import FormRow from "../../generic-components/Form/FormRow.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";

export default {
  components: { Form, FormRow, TextInput, Button, Card },
  data: function () {
    return {
      sub: {
        minimum: null,
        maximum: null,
      },

      showErrorMessage: false,
    };
  },
};
</script>
