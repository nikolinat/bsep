<template>
  <Form>
    <form-row>
      <div class="col-12">
                 <SelectOptionInput label="Select device type" :showLabel="false " v-model="rule.type" 
                 :options="options"></SelectOptionInput>
      </div>
    </form-row>
    <form-row v-if="this.rule.type !== null && (this.rule.type == 'AIR_CONDITIONING' || this.rule.type == 'HEATING') ">
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
    <form-row v-if="this.rule.type !== null && (this.rule.type == 'AIR_CONDITIONING' || this.rule.type == 'HEATING') ">
      <div class="col-12">
         <text-input label="Temperature value" v-model="rule.number" type="number"
                :showErrorMessage="showErrorMessage"/>
      </div>
    </form-row>
    <form-row v-if="this.rule.type !== null && (this.rule.type != 'AIR_CONDITIONING' || this.rule.type != 'HEATING') ">
      <div class="col-12">
         <text-input label="Message" v-model="rule.message" type="text"
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
            this.rule.message = 'Heating is on';
        }else if (this.rule.type == 'AIR_CONDITIONING'){
            if(this.mode == 'HOT'){
                  this.rule.message = 'Air conditioning is in heating mode';
            }else{
                  this.rule.message = 'Air conditioning is in cooling mode';
            }
        }else{
          console.log(this.rule);
        }
      this.createRule(this.rule);      
    },
  },

  mounted() {
    this.options = types;
    this.options.splice(0, 0, {
            value: -1,
            label: ''
        })
     this.options.splice(0, 0, {
            value: -1,
            label: ''
        })
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
