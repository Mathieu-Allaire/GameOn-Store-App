<template>
    <div class="main-page">
        <header>
            <img alt="Vue logo" class="logo" src="../assets/controller.png" />
            <label class="title">GameOn</label>
            <label v-if="state.loggedIn === '1'" class="title">Welcome Customer</label> 
            <div class="wrapper">
                <nav>
                </nav>
            </div>
        </header>
        <RouterView />
    </div>
</template>

<script>
import { state } from '../store/state'; // Ensure the correct path to the state file

export default {
  computed: {
    state() {
      return state; // Make the global state reactive in this component
    }
  },

  data() {
    return {
      role: ''
    };
  },

  async created() {
    // Retrieve the role from sessionStorage
    this.role = state.loggedIn; // Update local role
  }
};
</script>