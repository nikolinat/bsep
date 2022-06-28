<template>
  <div>
    <Table>
      <TableHead :columnNames="['Log type','Message','Date', 'Application']"></TableHead>
      <TableBody>
        <TableRow
          v-for="(log, i) in allLogs"
          :key="i"
          :values="[log.logType, log.message, formatDate(log.dateTime), log.applicationName]">
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
import { mapGetters } from "vuex";
import moment from "moment";

export default {
  data: () => {
    return {
        allLogs: []
    };
  },
  mounted() {},
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow
  },

  computed: {
    ...mapGetters({
      result: "log/getResult",
      logs: "log/getLogs"
    }),
  },

    watch: {
        logs(allLogs) {
            this.allLogs = allLogs;
        }
    },

  methods: {
    formatDate(d) {
        const format = "YYYY-MM-DD HH:mm:ss"
        return moment(d).format(format);
    },


  },
};
</script>