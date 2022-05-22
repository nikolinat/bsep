<template>
  <div>
    <Table>
      <TableHead
        :columnNames="['Name', 'Last name', 'Username', 'Email', '']"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(user, i) in users"
          :key="i"
          :values="[user.name, user.lastName, user.username, user.email]"
        >
          <div class="pull-right text-gray">
            <DropDownMenu>
                <DropDownItem @click="onAdd(user)">Add</DropDownItem>
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
import { mapGetters, mapActions } from "vuex";
import toastr from "toastr";

export default {
  props: {
      users: {},
      role: {},
      realEstate: {},
  },
  data: () => {
    return {
      selectedUser: null,
       searchFilterDto: {
                name: "",
                lastName: "",
                username: "",
                email: "",
                roles: [1, 2, 3]

      },
    };
  },

  mounted() {},

  components: {
    Table,
    TableHead,
    TableBody,
    TableRow,
    DropDownItem,
    DropDownMenu
  },

  watch: {
    result({ message, ok, label }) {
      if (label === "update") {
          if (ok) {
            toastr.success("Successfuly added owner/tenant!");
            this.fetchRealEstates();
            this.fetchUsers(this.searchFilterDto);
          } else {
            toastr.error(message);
        }
      }
    },
  },

computed: {
    ...mapGetters({
      result: "realestate/getResult",
    }),
  },
  methods: {
    ...mapActions({
      updateRealEstate: "realestate/updateRealEstate",
      fetchRealEstates: "realestate/fetchRealEstates",
      fetchUsers: "users/fetchUsers",
    }),

    onAdd(user) {
      this.selectedUser = user;
      var updateRealEstateDto = {
            id: this.realEstate.id,
            role: this.role,
            username: this.selectedUser.username
        }
      this.updateRealEstate(updateRealEstateDto);
      document.getElementById("addOwnerOrTenantCloser").click();

    },
  },
};
</script>
