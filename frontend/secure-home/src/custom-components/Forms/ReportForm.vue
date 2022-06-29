<template>
  <Form>
    <form-row>
      <div class="col-6">
        <text-input label="Type" v-model="reportFilterDto.type" type="text" />
      </div>
    </form-row>

    <form-row>
      <div class="col-6">
        <text-input
          label="Start date"
          v-model="reportFilterDto.startDateTime"
          type="date"
        />
      </div>
      <div class="col-6">
        <text-input
          label="End date"
          v-model="reportFilterDto.endDateTime"
          type="date"
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
import TextInput from "../../generic-components/Form/TextInput.vue";
import { mapGetters, mapActions } from "vuex";

export default {
  props: {
    realEstateId: String,
  },
  components: {
    Form,
    FormRow,
    TextInput,
    Button,
  },

  data: function () {
    return {
      reportFilterDto: {
        realEstateId: "0",
        type: "",
        startDateTime: "",
        endDateTime: "",
      },
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
      this.fetchReportForDevices(this.reportFilterDto);
    },
  },

  mounted() {
    this.reportFilterDto.realEstateId = this.realEstateId;
  },
};
</script>
