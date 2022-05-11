<template>
  <div>
    <Table>
      <TableHead :columnNames="['Name', 'Last name', 'Username', 'Email']"></TableHead>
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
import { mapGetters } from "vuex";

export default {
  props: {

  },
  data: () => {
    return {
      users: []
    };
  },
  mounted() {},
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow
  },

  watch: {
    searchedUsers(users) {
      this.users = users;
    }
  },
  computed: {
    ...mapGetters({
      searchedUsers: "users/getUsers",
    }),
  },
  methods: {
    
  },
};
</script>