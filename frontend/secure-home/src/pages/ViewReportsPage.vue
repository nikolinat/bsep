<template>
  <div class="content">
    <div class="container-fluid">
      <div class="center align-items-center col-10" style="margin-left: 10%">
        <div>
          <card :title="'Report'">
            <Report :realEstateId="this.$route.params.id" />
          </card>
          <card>
            <DevicesTable />
          </card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Card from "../generic-components/Card/Card.vue";
import DevicesTable from "../custom-components/Tables/DevicesTable.vue";
import Report from "../custom-components/Forms/ReportForm.vue";

export default {
  components: {
    Card,
    DevicesTable,
    Report,
  },
  name: "RealEstates",

  data: () => {
    return {
      realEstate: "0",
    };
  },
  computed: {
    ...mapGetters({
      devices: "devices/getDevices",
    }),
  },
  methods: {
    ...mapActions({
      fetchDevicesForRealEstate: "devices/fetchDevicesForRealEstate",
    }),
  },
  mounted() {
    this.realEstate = this.$route.params.id;
  },
};
</script>
