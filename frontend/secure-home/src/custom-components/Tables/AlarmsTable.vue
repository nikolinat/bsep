<template>
  <div>
    <Table>
      <TableHead
        :columnNames="[
          'Id',
          'Type',
          'Message',
          '',
          ''
        ]"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(alarm, i) in alarms"
          :key="i"
          :values="[
            alarm.id,
            alarm.type,
            alarm.alarmMessage,
            '',
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

export default {
  props: {
    alarms: {},
  },
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
   
  },
  mounted() {
    }
};
</script>
