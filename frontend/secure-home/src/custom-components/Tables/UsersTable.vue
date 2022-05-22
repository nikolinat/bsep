<template>
  <div>
    <Table>
      <TableHead
        :columnNames="['Name', 'Last name', 'Username', 'Email', 'Roles']"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(user, i) in users"
          :key="i"
          :values="[user.name, user.lastName, user.username, user.email, formatRoles(user)]"
        >
          <div class="pull-right text-gray">
            <DropDownMenu v-if="user.roles[0] !== 'ROLE_ADMIN'">
              <ModalOpener :modalBoxId="'deleteUser'">
                <DropDownItem @click="selectedUser = user">Delete</DropDownItem>
              </ModalOpener>
              <DropDownItem @click="onUpdate(user)">Update</DropDownItem>
            </DropDownMenu>
          </div>
        </TableRow>
      </TableBody>
    </Table>

    <Modal modalBoxId="deleteUser" title="Delete user">
      <div slot="body">Are you sure?</div>
      <div slot="buttons">
        <Button @click="onDelete()"> Delete </Button>
      </div>
      <div slot="buttons">
        <ModalCloser id="deleteUser">
          <Button> Close </Button>
        </ModalCloser>
      </div>
    </Modal>
  </div>
</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import DropDownMenu from "../../generic-components/DropdownMenu/DropdownMenu.vue";
import DropDownItem from "../../generic-components/DropdownMenu/DropdownItem.vue";
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue";
import ModalCloser from "../../generic-components/Modal/ModalCloser.vue";
import Modal from "../../generic-components/Modal/Modal.vue";
import Button from "../../generic-components/Form/Button.vue";
import { mapActions, mapGetters } from "vuex";
import toastr from "toastr";

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
    ModalOpener,
    Modal,
    Button,
    ModalCloser,
  },

  watch: {
    searchedUsers(users) {
      this.users = users;
    },
    result({label, ok, message}) {
      if (label === "delete") {
          if (ok) {
            toastr.success("User deleted");
            this.$emit("deletedUser")
          } else {
            toastr.error(message);
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      searchedUsers: "users/getUsers",
      result: "users/getResult"
    }),
  },
  methods: {
    ...mapActions({
      deleteUser: "users/deleteUser",
      fetchUsers: "users/fetchUsers"
    }),
    onUpdate(user) {
      this.selectedUser = user;

      this.$router.push("/update-user/" + user.id);
    },
    onDelete() {
      this.deleteUser(this.selectedUser.username);
      document.getElementById("deleteUser").click();
    },
    formatRoles(user) {
      let roles = ""
      user.roles.forEach(role => roles += role + ", ")
      return roles.slice(0, -2)
    }
  },
};
</script>
