<template>
  <div class="content">
    <Table>
      <TableHead
        :columnNames="['Id',  'Type', 'Number of alarms']"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(device, i) in devices"
          :key="i"
          :values="[
            device.id,
            device.type,
            device.number,
          ]"
        >
        </TableRow>
      </TableBody>
    </Table>
  </div>
</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import { mapActions, mapGetters } from "vuex";

export default {
  data: () => {
    return {
      devices: [],
    };
  },
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow,
  },

  watch: {
    searchedDevices(devices) {
      this.devices = devices;
    },
  },
  computed: {
    ...mapGetters({
      searchedDevices: "devices/getFilteredDevices",
      result: "devices/getResult",
    }),
  },
  methods: {
    ...mapActions({
      fetchDevices: "devices/fetchReportForDevices",
    }),
  },
};
</script>
