<template>
  <div>
    <Modal title="Add Subtrees" :modalBoxId="modalBoxId">
      <div slot="body">
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
              <div>
                <AddGeneralNameModal :modalBoxId="'addBase'" />

                <ModalOpener :modalBoxId="'addBase'">
                  <Button type="button">Add</Button>
                </ModalOpener>
              </div>
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
      </div>
    </Modal>
  </div>
</template>

<script>
import Card from "../../generic-components/Card/Card.vue";
import Button from "../../generic-components/Form/Button.vue";
import Form from "../../generic-components/Form/Form.vue";
import FormRow from "../../generic-components/Form/FormRow.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";
import Modal from "../../generic-components/Modal/Modal";
import AddGeneralNameModal from "../Modals/AddGeneralNameModal.vue";
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue";
export default {
  components: {
    Form,
    FormRow,
    TextInput,
    Button,
    Card,
    Modal,
    AddGeneralNameModal,
    ModalOpener,
  },
  props: {
    addedOptions: {
      type: Array,
      default: () => [],
    },
    modalBoxId: {
      type: String,
    },
  },
  data: function () {
    return {
      sub: {
        minimum: null,
        maximum: null,
      },

      showErrorMessage: false,
    };
  },
  watch: {
    addedOptions(options) {
      this.addedOptions = options;
    },
  },
  methods: {
    remove(index) {
      this.addedOptions.splice(index, 1);
    },

    handleAddGeneralNameToAuthorityKeyIdentifier(arg) {
      this.addedOptions.push({
        label: arg.label,
        value: arg.value,
        enteredValue: arg.enteredValue,
      });
    },
  },
};
</script>
