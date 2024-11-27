<!-- App.vue -->
<script>
import { RouterView } from 'vue-router'


import Navbar from './components/Navbar.vue'
import Logo from './components/Logo.vue'



export default {
  components: {
    RouterView,
    Navbar,
    Logo,
  },
  mounted() {
    sessionStorage.setItem("LoggedIn", "0");
  },
}

</script>



<template>
    <RouterView style="position:absolute; top:50px" />
    <Logo />
    <Navbar style="height: 50px" searchBar:="null"/>
</template>

<style>



body {
  margin: 0px;
  padding: 0px;

}


* {
  font-family: 'Roboto', sans-serif;
}

</style>
