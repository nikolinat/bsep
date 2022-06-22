<template>
  <Form>
    <form-row>
      <div class="col-12">
                 <SelectOptionInput label="Select device type" :showLabel="false " v-model="rule.type" 
                 :options="options"></SelectOptionInput>
      </div>
    </form-row>
  <form-row>
    <div class="col-12">
         <SelectOptionInput label="Select device type" :showLabel="false " v-model="rule.sign" 
            :options="signs"></SelectOptionInput>
    </div>
    </form-row>
    <form-row v-if="this.rule.type !== null && this.rule.type == 'AIR_CONDITIONING'">
    <div class="col-12">
         <SelectOptionInput label="Select mode" :showLabel="false " v-model="mode" 
            :options="modes"></SelectOptionInput>
    </div>
    </form-row>
    <form-row>
    <div class="col-12">
         <text-input label="Temperature value" v-model="rule.number" type="number"
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
  props: {
    realEstateId: null,
  },

  data: function () {
    return {
      rule: {
        sign: "",
        number: 0,
        message: "",
        type: "",
      },
      options: [],
      sign: "",
      signs: [],
      mode: "",
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
      createRule: "rules/createRule",
    }),

    handleClick() {
        if(this.rule.type == 'HEATING'){
            this.rule.message = 'Grejanje je ukljuceno';
        }else{
            if(this.mode == 'HOT'){
                  this.rule.message = 'Klima je ukljucena u rezimu grejanja';
            }else{
                  this.rule.message = 'Klima je ukljucena u rezimu hladjenja';
            }
        }
      this.createRule(this.rule);      
    },
  },

  mounted() {
    this.options = [  {
                        label:"AIR CONDITIONING",
                        value: "AIR_CONDITIONING"
                        }, 
                      {
                        label: "HEATING",
                        value: "HEATING"
                    }];
    this.signs = [  {
                        label:"GREATER",
                        value: ">="
                        }, 
                      {
                        label: "LESS",
                        value: "<="
                    }];
    this.modes = [
         {
                        label:"HOT",
                        value: "HOT"
                        }, 
                      {
                        label: "COOL",
                        value: "COOL"
                    }];
    
  },
};
</script>
