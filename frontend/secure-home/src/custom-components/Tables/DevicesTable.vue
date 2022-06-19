<template>
  <div>
    <Table>
      <TableHead
        :columnNames="[
          'Id',
          'Name',
          'Type',
          '',
          ''
        ]"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(device, i) in devices"
          :key="i"
          :values="[
            device.id,
            device.name,
            device.type,
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
    devices: {},
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
