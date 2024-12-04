<template>
  <div class="container">
    <div class="spacer"></div>
    <div class="content">
      <h1>Create Account</h1>
      <!-- labels and inputs for email, name, password, password confirmation, card number, expiration date, and billing address
      to create a new customer account -->
      <form @submit.prevent="createCustomer">
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
          <button type="submit">Create Account</button>
          <label>
            <!-- going to login page after creating the account -->
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
// RegisterView component to create a new customer account
export default {
  name: "RegisterView",
  components: {
    RouterLink
  },
  // data properties for email, name, password, password confirmation, card number, expiration date, and billing address
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
  // createCustomer method to create a new customer account
  methods: {
    async createCustomer() {
      console.log(typeof this.cardNumber + " " + this.cardNumber);
      try {
        if (!this.validateFields()) {
          return;
        }
        if (!this.confirmPasswords()) {
          return;
        }
        if (!this.validCardNumber()) {
          return;
        }

        let processedCardNumber = this.cardNumber.trim();
        if (processedCardNumber.length > 8) {
          processedCardNumber = processedCardNumber.substring(0, 8);
        }

        // newCustomer object with email, name, password, card number, expiration date, and billing address
        const newCustomer = {
          email: this.email,
          name: this.name,
          password: this.password,
          cardNumber: processedCardNumber,
          expiracyDate: this.expiracyDate,
          billingAddress: this.billingAddress
        };

        // POST request to create a new customer account
        const response = await axiosClient.post("/customer", newCustomer);
        if (response.status === 201) {
          alert("Account created successfully");
          this.$router.push("/login");
        }
        // alert if an error occurs
      } catch (error) {
        if (typeof error.response.data === "string") {
          alert(error.response.data);
        } else if (error.response.data.errors && Array.isArray(error.response.data.errors)) {
          alert(error.response.data.errors[0].defaultMessage);
        } else if (error.response.data.error) {
          alert(error.response.data.error);
        } else if (error.response.data.message) {
          alert(error.response.data.message);
        } else {
          alert("An error occurred");
        }
      }
    },
    // confirmPasswords method to check if the password and password confirmation match
    confirmPasswords() {
      if (this.password !== this.passwordConfirmation) {
        alert("Passwords do not match");
        return false;
      }
      return true;
    },
    // validateFields method to check if all fields are filled
    validCardNumber() {
      const cardNumber = this.cardNumber.trim();
      if (!/^\d+$/.test(cardNumber)) {
        alert("Card number must be a valid number");
        return false;
      }
      return true;
    },
    // validateFields method to check if all fields are filled
    validateFields() {
      if (
        !this.email.trim() ||
        !this.name.trim() ||
        !this.password ||
        !this.passwordConfirmation ||
        !this.cardNumber.trim() ||
        !this.expiracyDate ||
        !this.billingAddress.trim()
      ) {
        alert("Please fill in all fields");
        return false;
      }
      return true;
    }
  }
};
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
  color: #04050a;
  padding: 2em;
  border-radius: 1em;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  width: 450px;
  text-align: center;
}

h1 {
  font-size: 2.2rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 0.8em;
  color: peru;
}

form {
  display: flex;
  flex-direction: column;
  gap: 0.7em;
}

label {
  font-weight: bold;
  text-align: left;
  margin-bottom: 0.2em;
}

input {
  padding: 0.5em;
  font-size: 0.95rem;
  border-radius: 0.5em;
  border: 1px solid #000000;
}

button {
  padding: 0.6em;
  font-size: 1rem;
  border-radius: 0.5em;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

label a {
  color: #007bff;
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
  gap: 0.4em;
}
</style>