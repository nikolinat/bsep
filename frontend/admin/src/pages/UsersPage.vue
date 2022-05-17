<template>
  <div class="content">
    <div class="container-fluid">
      <div class="center align-items-center col-10" style="margin-left: 10%">
        <card :title="'Search and filter users'">
          <SearchFilterUsersForm />
        </card>
        <card>
          <UsersTable />
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "../generic-components/Card/Card.vue";
import SearchFilterUsersForm from "../custom-components/Forms/SearchFilterUsersForm.vue";
import UsersTable from "../custom-components/Tables/UsersTable.vue";

export default {
  components: {
    Card,
    SearchFilterUsersForm,
    UsersTable,
  },
  name: "UsersPage",
};
</script>
