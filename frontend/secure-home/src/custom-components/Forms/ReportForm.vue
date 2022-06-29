<template>
  <Form>
    <form-row>
      <div class="col-6">
         <SelectOptionInput v-if="realEstateId !== null"
                       label="Select device type" :showLabel="false " v-model="reportFilterDto.type" :options="types"
        ></SelectOptionInput>
      </div>
    </form-row>

    <form-row>
      <div class="col-6">
         <DateTimePicker
            v-model="reportFilterDto.startDate"
            label="Start date"
            type="datetime"
             id="start"
            />
      </div>
      <div class="col-6">
        <DateTimePicker
            v-model="reportFilterDto.endDate"
            label="End date"
            type="datetime"
            id="end"
            />
      </div>
    </form-row>

    <Button @click="handleSearchAndFilter">Search</Button>
  </Form>
</template>

<script>
import Button from "../../generic-components/Form/Button.vue";
import Form from "../../generic-components/Form/Form.vue";
import FormRow from "../../generic-components/Form/FormRow.vue";
import SelectOptionInput from "../../generic-components/Form/SelectOptionInput.vue"
import DateTimePicker from '../../generic-components/Form/DateTimePicker.vue'
import { mapGetters, mapActions } from "vuex";
import {types} from '../../constants.js'
import moment from 'moment'
export default {
  props: {
    realEstateId: null,
  },
  components: {
    Form,
    FormRow,
    Button,
    DateTimePicker,
    SelectOptionInput
  },

  data: function () {
    return {
      reportFilterDto: {
        realEstateId: 0,
        type: null,
        startDate:null,
        endDate: null,
      },
      types: []
    };
  },

  computed: {
    ...mapGetters({
      reports: "devices/fetchReportForDevices",
      result: "devices/getResult",
    }),
  },

  methods: {
    ...mapActions({
      fetchReportForDevices: "devices/fetchReportForDevices",
    }),

    handleSearchAndFilter() {
      this.reportFilterDto.startDate = moment( this.reportFilterDto.startDate).format(moment.HTML5_FMT.DATETIME_LOCAL_SECONDS);
       this.reportFilterDto.endDate = moment( this.reportFilterDto.endDate).format(moment.HTML5_FMT.DATETIME_LOCAL_SECONDS);
      this.fetchReportForDevices(this.reportFilterDto);
    },
  },

  mounted() {
    this.types = types;
    this.reportFilterDto.realEstateId = this.realEstateId;
    console.log(this.realEstateId);
  },
};
</script>
