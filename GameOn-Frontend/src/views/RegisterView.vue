<template>
    <div class="container">
      <div class="spacer"></div>
      <div class="content">
        <h1>Create Account</h1>
        <form @submit.prevent="register">
          <label for="email">Email</label>
          <input id="email" v-model="email" type="email" required />
  
          <label for="name">Name</label>
          <input id="name" v-model="name" type="text" required />
  
          <label for="password">Password</label>
          <input id="password" v-model="password" type="password" required />
  
          <label for="passwordConfirmation">Confirm Password</label>
          <input id="passwordConfirmation" v-model="passwordConfirmation" type="password" required />
  
          <label for="cardNumber">Card Number</label>
          <input id="cardNumber" v-model="cardNumber" type="text" required />
  
          <label for="expiracyDate">Expiration Date</label>
          <input id="expiracyDate" v-model="expiracyDate" type="date" required />
  
          <label for="billingAddress">Billing Address</label>
          <input id="billingAddress" v-model="billingAddress" type="text" required />
  
          <div class="form-actions">
            <button type="submit" @click="createCustomer">Create Account</button>
            <label>
              Already have an account?
              <RouterLink to="/login">Login</RouterLink>
            </label>
          </div>
        </form>
      </div>
      <div class="spacer"></div>
    </div>
  </template>

<script>
import axios from "axios";
import { RouterLink } from "vue-router";

const axiosClient = axios.create({
    baseURL: "http://localhost:8080"
});

export default {
    name: "register",
    data() {
        return {
            email: "",
            name: "",
            password: "",
            passwordConfirmation: "",
            cardNumber: "",
            expiracyDate: "",
            billingAddress: ""
        };
    },

    async created() {
        // Fetch data from the API
    },

    methods: {
        async createCustomer() {
            const newCustomer = {
                email: this.email,
                name: this.name,
                password: this.password,
                date: this.date,
                cardNumber: this.cardNumber,
                expiracyDate: this.expiracyDate,
                billingAddress: this.billingAddress
            };
            try {
                this.confirmPasswords();
                const response = await axiosClient.post("/customer", newCustomer);
                if (response.status === 201) {
                    alert("Account created successfully");
                    this.$router.push("/login");
                }
            } catch (error) {
                console.error(error);
            }
        },
        confirmPasswords() {
            if (this.password !== this.passwordConfirmation) {
                alert("Passwords do not match");
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
  align-items: stretch;
  justify-content: center;
  background: #ffffff;
  padding: 2em; /* Slightly reduced padding */
  border-radius: 1em;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  width: 450px; /* Reduced width for a smaller form */
  text-align: center;
}

h1 {
  font-size: 2.2rem; /* Slightly smaller title */
  font-weight: bold;
  text-align: center;
  margin-bottom: 0.8em; /* Adjusted spacing below the title */
  color: #04050a;
}

form {
  display: flex;
  flex-direction: column;
  gap: 0.7em; /* Reduced spacing between form elements */
}

label {
  font-weight: bold;
  text-align: left;
  margin-bottom: 0.2em;
}

input {
  padding: 0.5em; /* Reduced input padding */
  font-size: 0.95rem; /* Slightly smaller input text */
  border-radius: 0.5em;
  border: 1px solid #ccc;
}

button {
  padding: 0.6em; /* Slightly reduced button padding */
  font-size: 1rem; /* Slightly smaller button text */
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
}

label a:hover {
  text-decoration: underline;
}

/* Align form text */
form label,
form input,
form button {
  width: 100%;
}

/* Action buttons styling */
.form-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.4em; /* Slightly reduced spacing for actions */
}
</style>