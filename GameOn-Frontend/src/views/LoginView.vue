<template>
  <div>
    <h1>Login</h1>
    <form @submit.prevent="login">
      <label for="email">Email</label>
      <input id="email" v-model="email" type="text" required>
      <label for="password">Password</label>
      <input id="password" v-model="password" type="password" required>
      <button type="submit">Login</button>
      <label>Don't have an account? <RouterLink to="/register">Register</RouterLink></label>
    </form>
  </div>
</template>

<script setup>

</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 1em;
}
label {
  font-weight: bold;
}
input {
  padding: 0.5em;
  border-radius: 0.5em;
  border: 1px solid #ccc;
}
</style>