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
  },
  computed: {
    ...mapGetters({
      searchedUsers: "users/getUsers",
    }),
  },
  methods: {
    ...mapActions({
      deleteUser: "users/deleteUser",
    }),
    onUpdate(user) {
      this.selectedUser = user;

      this.$router.push({ name: "UpdateUserPage", params: { data: user } });
    },
    onDelete() {
      this.deleteUser(this.selectedUser.username);
      toastr.success("User updated");
    },
  },
};
</script>
