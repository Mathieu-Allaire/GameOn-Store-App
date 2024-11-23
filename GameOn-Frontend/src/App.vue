<!-- App.vue -->
<script setup>
import { RouterView } from 'vue-router'
import Navbar from './components/Navbar.vue'
import Logo from './components/Logo.vue'
sessionStorage.setItem("LoggedIn", "0");
</script>

<template>
  <Navbar />
  <RouterView />
  <Logo />
</template>

<style>
/* Remove logo styles if previously added here */

/* Ensure body has no margin to prevent unexpected spacing */
body {
  margin: 0;
  padding: 0;
}
</style>