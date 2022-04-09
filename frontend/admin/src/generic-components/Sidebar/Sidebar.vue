<template>
  <div class="sidebar" data-color="purple" data-background-color="white">
      <sidebar-logo></sidebar-logo>
      <div class="sidebar-wrapper">
        <ul class="nav">
          <sidebar-item name="Home" icon="dashboard" to="/"></sidebar-item>
          <sidebar-item name="My patients" icon="contacts" to="/patients_list"></sidebar-item>
          <sidebar-item name="Consultation report" icon="assignment" to="/report"></sidebar-item>
          <sidebar-item name="Work calendar" icon="event" to="/work-calendar"></sidebar-item>
          <sidebar-item name="Vacation request" icon="holiday_village" to="/vacation-request"></sidebar-item>
          <sidebar-item name="Issue medicine" icon="medication" to="/issue-reserved"></sidebar-item>
        </ul>
      </div>
    </div>
</template>

<script>
import SidebarItem from './SidebarItem.vue'
import SidebarLogo from './SidebarLogo.vue'

export default {
  data: () => {
    return {
      
    }
  },
  mounted() {
    
  },
  components: {
    SidebarLogo,
    SidebarItem
  },
  methods: {
    
  }
}
</script>

<style>

</style>