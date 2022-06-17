<template>
  <Form>
    <form-row>
      <div class="col-12">
        <text-input label="Name" v-model="device.name" type="text" 
                :isValid="validateStringWithLettersOnly(device.name)"
                :showErrorMessage="showErrorMessage"
                errorMessage="Name must have only letters" />
      </div>
    </form-row>

    <form-row>
      <div class="col-12">
        <text-input label="Period (seconds)" v-model="device.period" type="number"
                :showErrorMessage="showErrorMessage"/>
      </div>
    </form-row>
    <form-row>
      <div class="col-12">
        <text-input label="Filter" v-model="device.filter" type="text"
                :showErrorMessage="showErrorMessage"/>
      </div>
    </form-row>
  <form-row>
    <div class="col-12">
         <SelectOptionInput v-if="realEstateId !== null"
                       label="Select device type" :showLabel="false " v-model="device.type" :options="types"
        ></SelectOptionInput>
    </div>
    </form-row>

    <Button @click="handleClick">Add</Button>
  </Form>

</template>

<script>
import Button from "../../generic-components/Form/Button.vue";
import Form from "../../generic-components/Form/Form.vue";
import FormRow from "../../generic-components/Form/FormRow.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";
import SelectOptionInput from '../../generic-components/Form/SelectOptionInput.vue'
import { mapActions, mapGetters} from "vuex";
import toastr from "toastr";
import {validateStringWithLettersOnly } from '../../utils/validation'
import {types} from '../../constants.js'

export default {
  components: {
    Form,
    FormRow,
    TextInput,
    Button,
    SelectOptionInput
  },
  props: {
    realEstateId: null,
  },

  data: function () {
    return {
      device: {
        realEstateId: "",
        name: "",
        type: "",
        filter: "",
      },
      types: [],
      type: "",
      showErrorMessage: false
    };
  },

  computed: {
    ...mapGetters({
      result: "devices/getResult",
    }),
  },

  watch: {
    result({ message, ok, label }) {
      if (label === "create") {
          if (ok) {
            toastr.success("Device added");
          } else {
            toastr.error(message);
        }
        document.getElementById("addDeviceModalOpener").click();
      }
    },
  },

  methods: {
    ...mapActions({
      createDevice: "devices/createDevice",
    }),

    handleClick() {
      this.device.realEstateId = this.realEstateId;
      this.createDevice(this.device);      
    },
    validateStringWithLettersOnly(name) {
      return validateStringWithLettersOnly(name);
    }
  },

  mounted() {
    this.types = types;
  },
};
</script>
