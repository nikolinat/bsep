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
          <div class="pull-right text-gray">
            <DropDownMenu>
              <DropDownItem @click="deleteUser(user)"> Remove tenant/owner </DropDownItem>
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
import DropDownMenu from "../../generic-components/DropdownMenu/DropdownMenu.vue"
import DropDownItem from "../../generic-components/DropdownMenu/DropdownItem.vue"
import { mapActions, mapGetters } from "vuex"
export default {
  props: {
      users: {},
      realEstate: null
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
    TableRow,
    DropDownMenu,
    DropDownItem
  },
   computed: {
    ...mapGetters({
      result: "realestate/getResult"
    }),
  },
  watch: {
  },
  methods: {
    ...mapActions({
      removeTenant: "realestate/removeTenant",
    }),

    deleteUser(user){
      this.removeTenant( {id: user.id, realEstateId: this.realEstate.id});
      document.getElementById("viewTenantsModalOpener").click();
      document.getElementById("viewOwnersModalOpener").click();
    }

  },
  
};
</script>