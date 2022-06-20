<template>
  <div>
    <Table>
      <TableHead
        :columnNames="[
          'Device id',
          'Device name',
          'Device type',
          'Message',
          'Date',
          'Is alarm'
        ]"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(deviceMessage, i) in devicesMessages"
          :key="i"
          :values="[
            deviceMessage.deviceId,
            deviceMessage.deviceName,
            deviceMessage.type,
            deviceMessage.message,
            formatDate(deviceMessage.dateTime),
            deviceMessage.alarm
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
import moment from 'moment'

export default {
  props: ["devicesMessages"],
  data: () => {
    return {
    };
  },
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow
  },

  computed: {
  },
  watch: {
     
  },
  methods: {
    formatDate(date) {
        const format = "YYYY-MM-DD HH:mm:ss"
        return moment(date).format(format);
    }
  },
  mounted() {
    }
};
</script>
