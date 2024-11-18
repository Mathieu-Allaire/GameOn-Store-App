<template>
  <nav class="navbar">
    <ul class="nav-links">
      <li><RouterLink to="/">Main Page</RouterLink></li>
      <li><RouterLink to="/about">About</RouterLink></li>
      <li><RouterLink to="/main-page">Home</RouterLink></li>
      <li><RouterLink to="/login">Sign In</RouterLink></li>
    </ul>
  </nav>
</template>

<script setup>
import { RouterLink } from 'vue-router'
</script>

<style scoped>
.navbar {
  background-color: #333;
  padding: 0; /* Remove padding to align to the top */
  position: fixed;
  top: 0;
  left: 0; /* Align navbar to the left */
  width: 100%;
}

.nav-links {
  list-style: none;
  display: flex;
  gap: 1em;
  margin: 0;
  padding: 1em; /* Add padding to the nav links */
}

.nav-links li {
  display: inline;
}

.nav-links a {
  color: white;
  text-decoration: none;
}
</style>