<template>
  <div>
    <Table>
      <TableHead
        :columnNames="['Name', 'Last name', 'Username', 'Email']"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(user, i) in users"
          :key="i"
          :values="[user.name, user.lastName, user.username, user.email]">
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
      users: {}
  },
  data: () => {
    return {
    };
  },
  mounted() {},
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow
  },
  methods: {},
};
</script>