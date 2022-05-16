<template>
  <div class="content">
    <div class="container-fluid">
      <div class="center align-items-center col-10" style="margin-left: 10%">
        <card :title="'Add new user'">
          <AddNewUser />
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "../generic-components/Card/Card.vue";
import AddNewUser from "../custom-components/Forms/AddNewUser.vue";

export default {
  components: {
    Card,
    AddNewUser,
  },
  name: "CreateUserPage",
};
</script>
