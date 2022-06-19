<template>
  <div class="sidebar" data-color="purple" data-background-color="white">
    <sidebar-logo></sidebar-logo>
    <div class="sidebar-wrapper">
      <ul class="nav">
        <sidebar-item name="Home" icon="dashboard" to="/"></sidebar-item>
        <sidebar-item
          name="Valid certificates"
          icon="assignment"
          to="/valid-certificates"
        ></sidebar-item>
        <sidebar-item
          name="Revoked certificates"
          icon="assignment"
          to="/revoked-certificates"
        ></sidebar-item>
        <sidebar-item
          name="CSR"
          icon="assignment"
          to="/csr-preview"
        ></sidebar-item>
        <sidebar-item
          name="Create CSR"
          icon="assignment"
          to="/csr"
        ></sidebar-item>
        <sidebar-item
          name="Logs"
          icon="assignment"
          to="/logs"
        ></sidebar-item>
      </ul>
    </div>
  </div>
</template>

<script>
import SidebarItem from "./SidebarItem.vue";
import SidebarLogo from "./SidebarLogo.vue";
import {getRoleFromToken} from "../../utils/token.js";

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
