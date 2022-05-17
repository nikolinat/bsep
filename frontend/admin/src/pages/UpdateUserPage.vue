<template>
  <div class="content">
    <div class="container-fluid">
      <div class="center align-items-center col-10" style="margin-left: 10%">
        <card :title="'Update user'">
          <UpdateUser :user="this.UserData" />
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "../generic-components/Card/Card.vue";
import UpdateUser from "../custom-components/Forms/UpdateUser.vue";

export default {
  components: {
    Card,
    UpdateUser,
  },
  name: "UpdateUserPage",
  data() {
    return {
      UserData: [],
    };
  },
  created() {
    this.UserData = this.$route.params.data;
  },
};
</script>
