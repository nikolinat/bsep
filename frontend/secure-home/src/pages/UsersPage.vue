<template>
  <div class="content">
    <div class="container-fluid">
      <div class="center align-items-center col-10" style="margin-left: 10%">
        <card :title="'Search and filter users'">
          <SearchFilterUsersForm />
        </card>
        <card>
          <UsersTable @deletedUser="deletedUser" :users="users" />
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "../generic-components/Card/Card.vue";
import SearchFilterUsersForm from "../custom-components/Forms/SearchFilterUsersForm.vue";
import UsersTable from "../custom-components/Tables/UsersTable.vue";
import { mapActions, mapGetters } from "vuex";

export default {
  components: {
    Card,
    SearchFilterUsersForm,
    UsersTable,
  },
  name: "UsersPage",
  data: () => {
    return {
      users: [],
    };
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
      fetchUsers: "users/fetchUsers",
    }),

    deletedUser() {
      this.fetchUsers({
        name: "",
        lastName: "",
        username: "",
        email: "",
        roles: [1, 2, 3],
      });
    },
  },
};
</script>
