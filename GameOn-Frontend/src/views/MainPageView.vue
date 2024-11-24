<template>
    <div class="main-page">
        <header>
            <img alt="Vue logo" class="logo" src="../assets/controller.png" />
            <label class="title">GameOn</label>
            <label v-if="role === '1'" class="title">Welcome Customer</label> 
            <div class="wrapper">
                <nav>
                </nav>
            </div>
        </header>
        <RouterView />
    </div>
</template>

<script>

export default {
  
  data() {
    return {
      role: ''
    }
  },

  async created() {
    // Retrieve the role from sessionStorage
    const storedRole = sessionStorage.getItem('LoggedIn');
    if (storedRole) {
      this.role = storedRole; // Update role
    }
    },
      }
</script>