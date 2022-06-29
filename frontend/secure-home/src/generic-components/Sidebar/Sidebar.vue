<template>
  <div class="sidebar" data-color="purple" data-background-color="white">
    <sidebar-logo></sidebar-logo>
    <div class="sidebar-wrapper">
      <ul class="nav">
        <sidebar-item name="Home" icon="dashboard" to="/"></sidebar-item>
        <sidebar-item v-if="role.includes('ROLE_ADMIN')" name="Registration user" icon="dashboard" to="/new-user"></sidebar-item>
        <sidebar-item v-if="role.includes('ROLE_ADMIN')" name="Users" icon="assignment" to="/users"></sidebar-item>
        <sidebar-item  v-if="role.includes('ROLE_ADMIN')" name="Real estates" icon="assignment" to="/real-estates"></sidebar-item>
         <sidebar-item  v-if="role.includes('ROLE_ADMIN')" name="Rules" icon="assignment" to="/rule"></sidebar-item>

        <sidebar-item  v-if="role.includes('ROLE_TENANT') || role.includes('ROLE_HOUSE_OWNER')" name="Real estates owner" icon="assignment" to="/owner-real-estates"></sidebar-item>
        <sidebar-item  v-if="role.includes('ROLE_TENANT') || role.includes('ROLE_HOUSE_OWNER')" name="Real estates tenant" icon="assignment" to="/tenant-real-estates"></sidebar-item>
      </ul>
    </div>
  </div>
</template>

<script>
import SidebarItem from "./SidebarItem.vue";
import SidebarLogo from "./SidebarLogo.vue";
import {getRoleFromToken} from '../../utils/token.js';

export default {
  data: () => {
    return {
      role: "",
    };
  },
  mounted() {
    this.role = getRoleFromToken();
  },
  components: {
    SidebarLogo,
    SidebarItem,
  },
  methods: {},
};
</script>

<style></style>
