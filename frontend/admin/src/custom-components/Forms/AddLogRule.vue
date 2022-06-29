<template>
  <Form>
    <form-row>
      <div class="col-12">
                 <SelectOptionInput label="Select log type" :showLabel="false " v-model="log.type" 
                 :options="options"></SelectOptionInput>
      </div>
    </form-row>
    <form-row>
      <div class="col-12">
         <text-input label="Message" v-model="log.message" type="text"
                :showErrorMessage="showErrorMessage"/>
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

export default {
  components: {
    Form,
    FormRow,
    TextInput,
    Button,
    SelectOptionInput
  },
  props: { },

  data: function () {
    return {
      log: {
        message: "",
        type: "",
      },
      options: [],
      showErrorMessage: false
    };
  },

  computed: {
    ...mapGetters({
      result: "rules/getResult",
    }),
  },

  watch: {
    result({ message, ok, label }) {
      if (label === "create") {
          if (ok) {
            toastr.success("Rule added");
          } else {
            toastr.error(message);
        }
      }
    },
  },

  methods: {
    ...mapActions({
      createRule: "rules/createLogRule",
    }),

    handleClick() {
      this.createRule(this.log);      
    },
  },

  mounted() {
    this.options = [
        {
            "label": "SUCCESS",
            "value": "SUCCESS"
        }, 
        {
            "label": "ERROR",
            "value": "ERROR"
        },
        {
            "label": "WARNING",
            "value": "WARNING"
        }
    ]
  },
};
</script>
