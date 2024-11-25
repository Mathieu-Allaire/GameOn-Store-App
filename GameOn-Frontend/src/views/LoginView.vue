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

<script>
import axios from "axios";
import { setLoggedIn } from '../store/state';

const axiosClient = axios.create({
  // NOTE: it's baseURL, not baseUrl
  baseURL: "http://localhost:8080"
});

export default {
  
  data() {
    return {
      email: '',
      password: '',
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

  methods: {
    async login() {
      try{
        const response = await axiosClient.get(`/login/${this.email}/${this.password}`);
        this.role = response.data.toString(); // Store role from response as a string
        console.log("Login successful, role:", this.role);

        sessionStorage.setItem('LoggedIn', this.role); // Store role in sessionStorage
        setLoggedIn(this.role);
        sessionStorage.setItem('Email', this.email); // Store email in sessionStorage

        this.$router.push('/'); // Go to main page
      }
      catch (error){
        console.error(error.response.data); 
        alert(error.response.data);
      }
      
      
          }
        }
      }
</script>



<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 1em;
}
h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 1em;
  color: #04050a;
  font-family: 'Doto', sans-serif;
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