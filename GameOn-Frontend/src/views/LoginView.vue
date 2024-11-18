<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="handleLogin">
      <!-- Email Input -->
      <div class="form-group">
        <label for="email">Email:</label>
        <input 
          type="email" 
          id="email" 
          v-model="email" 
          placeholder="Enter your email" 
          required 
        />
      </div>

      <!-- Password Input -->
      <div class="form-group">
        <label for="password">Password:</label>
        <input 
          type="password" 
          id="password" 
          v-model="password" 
          placeholder="Enter your password" 
          required 
        />
      </div>

      <!-- Buttons -->
      <div class="button-group">
        <button type="submit" class="btn btn-primary">Enter</button>
        <button type="button" class="btn btn-secondary">
          Register
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "LoginView",
  data() {
    return {
      email: "",
      password: ""
    };
  },
  methods: {
    handleLogin() {
      // Handle login logic
      // console.log("Logging in with", this.email, this.password);
      // alert("Login successful!");
    },
    goToRegister() {
      // Navigate to the register page
      // alert("Redirecting to Register page...");
      // this.$router.push("/register");
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

h1 {
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

button {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button.btn-primary {
  background-color: #007bff;
  color: white;
}

button.btn-primary:hover {
  background-color: #0056b3;
}

button.btn-secondary {
  background-color: #6c757d;
  color: white;
}

button.btn-secondary:hover {
  background-color: #5a6268;
}
</style>
