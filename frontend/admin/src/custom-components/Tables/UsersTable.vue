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
          :values="[user.name, user.lastName, user.username, user.email]"
        >
          <div class="pull-right text-gray">
            <DropDownMenu>
              <DropDownItem @click="onUpdate">Delete</DropDownItem>
              <DropDownItem @click="onUpdate(user)">Update</DropDownItem>
            </DropDownMenu>
          </div>
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
import DropDownMenu from "../../generic-components/DropdownMenu/DropdownMenu.vue";
import DropDownItem from "../../generic-components/DropdownMenu/DropdownItem.vue";
import { mapGetters } from "vuex";

export default {
  props: {},
  data: () => {
    return {
      users: [],
      selectedUser: null,
    };
  },
  mounted() {},
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow,
    DropDownItem,
    DropDownMenu,
  },

  watch: {
    searchedUsers(users) {
      this.users = users;
    },
  },
  computed: {
    ...mapGetters({
      searchedUsers: "users/getUsers",
    }),
  },
  methods: {
    onUpdate(user) {
      this.selectedUser = user;

      this.$router.push({ name: "UpdateUserPage", params: { data: user } });
    },
  },
};
</script>
