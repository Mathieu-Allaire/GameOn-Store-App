<template>
  <div class="container">
    <div class="spacer"></div>
    <div class="content">
      <h1>Login</h1>
      <form @submit.prevent="login">
        <label for="email">Email</label>
        <input id="email" v-model="email" type="text" required />
        <label for="password">Password</label>
        <input id="password" v-model="password" type="password" required />
        <button type="submit">Login</button>
        <label>
          Don't have an account? <RouterLink to="/register">Register</RouterLink>
        </label>
      </form>
    </div>
    <div class="spacer"></div>
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
/* General page styling */
body {
  margin: 0;
  padding: 0;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  font-family: 'Doto', sans-serif;
}

/* Container for the content */
.container {
  display: grid;
  grid-template-columns: 1fr auto 1fr; /* Spacer, content, spacer */
  align-items: center;
  height: 100vh;
  width: 100%;
}

/* Left and right empty spaces */
.spacer {
  background-color: transparent;
}

/* Centered content */
.content {
  display: flex;
  flex-direction: column;
  align-items: stretch; /* Stretch elements to align */
  justify-content: center;
  background: #ffffff;
  padding: 2em; /* Adjusted padding for better spacing */
  border-radius: 1em;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  width: 400px;
  text-align: center;
}

h1 {
  font-size: 2.5rem; /* Title size remains large */
  font-weight: bold;
  text-align: center;
  margin-bottom: 0.5em; /* Tighter gap below the title */
  color: #04050a;
}

form {
  display: flex;
  flex-direction: column;
  gap: 0.8em; /* Reduced spacing between fields for a tighter layout */
}

label {
  font-weight: bold;
  text-align: left;
  margin-bottom: 0.2em; /* Reduced gap between labels and inputs */
}

input {
  padding: 0.6em; /* Reduced padding for compact fields */
  font-size: 1rem;
  border-radius: 0.5em;
  border: 1px solid #ccc;
}

button {
  padding: 0.7em;
  font-size: 1.1rem;
  border-radius: 0.5em;
  border: none;
  background-color: #007BFF;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

label a {
  color: #007BFF;
  text-decoration: none;
  font-weight: normal;
}

label a:hover {
  text-decoration: underline;
}

/* Adjust form text alignment */
form label,
form input,
form button {
  width: 100%; /* Ensure inputs and labels stretch uniformly */
}
</style>
