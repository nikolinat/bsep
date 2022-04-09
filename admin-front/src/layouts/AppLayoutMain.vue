<template>
  <div>
    <header/>
    <sidebar></sidebar>
    <main-wrapper>
      <navbar></navbar>
      <slot />
      <Footer>
        <p style="text-align:center;">&#169; 2022 Kućni sigurnosni sistem</p>
      </Footer>
    </main-wrapper>
  </div>
</template>

<script>
import Sidebar from '../generic-components/Sidebar/Sidebar.vue'
import Navbar from '../generic-components/Navbar/Navbar.vue'
import MainWrapper from '../generic-components/Wrappers/MainWrapper.vue'
import Footer from './Footer.vue'

export default {
  components: {
    Navbar,
    Sidebar,
    MainWrapper,
    Footer
  }
}
</script>

<style>

</style>