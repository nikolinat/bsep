<template>
  <Form>
    <form-row>
      <div class="col-12">
        <text-input label="Alarm" v-model="alarm.alarmMessage" type="text"
                :showErrorMessage="showErrorMessage"/>
      </div>
    </form-row>
  <form-row>
    <div class="col-12">
         <SelectOptionInput v-if="realEstateId !== null"
                       label="Select device type" :showLabel="false " v-model="alarm.type" :options="types"
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
      alarm: {
        realEstateId: "",
        id: "",
        alarmMessage: "",
        type: "",
      },
      types: [],
      showErrorMessage: false
    };
  },

  computed: {
    ...mapGetters({
      result: "alarms/getResult",
    }),
  },

  watch: {
    result({ message, ok, label }) {
      if (label === "create") {
          if (ok) {
            toastr.success("Alarm added");
          } else {
            toastr.error(message);
        }
        document.getElementById("addAlarmModalOpener").click();
      }
    },
  },

  methods: {
    ...mapActions({
      createAlarm: "alarms/createAlarm",
    }),

    handleClick() {
      this.alarm.realEstateId = this.realEstateId;
      this.createAlarm(this.alarm);      
    },
  },

  mounted() {
    this.types = types;
  },
};
</script>
