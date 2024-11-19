<!-- Logo.vue -->
<template>
    <div class="logo-container">
      <img alt="Controller Logo" class="logo" :src="controllerImage" />
    </div>
  </template>
  
  <script setup>
  import controllerImage from '../assets/controller.png' // Adjust the path if necessary
  </script>
  
  <style scoped>
  .logo-container {
    position: fixed;
    bottom: 0;
    left: 0;
    padding: 1em; /* Adjust padding as needed */
  }
  
  .logo {
    width: 50px; /* Adjust to desired size */
    height: auto;
  }
  
  /* Optional: Add z-index if necessary to control layering */
  .logo-container {
    z-index: 10;
  }
  </style>