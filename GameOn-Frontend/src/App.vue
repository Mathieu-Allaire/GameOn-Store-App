<!-- App.vue -->
<script setup>
import { RouterView } from 'vue-router'
import Navbar from './components/Navbar.vue'
import Logo from './components/Logo.vue'
//axios.defaults.baseURL = "http://localhost:8080";
sessionStorage.setItem("LoggedIn", "0");
</script>

<template>
    <RouterView style="position:absolute; top:50px"/>
    <Logo />
    <Navbar style="height: 50px"/>
</template>

<style>
/* Remove logos styles if previously added here */

/* Ensure body has no margin to prevent unexpected spacing */
body {
  margin: 0px;
  padding: 0px;

}

</style>
