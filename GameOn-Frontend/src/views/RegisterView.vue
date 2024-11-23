<template>
    <div>
        <h1>Create Account</h1>
        <form @submit.prevent="register">
            <label for="email">Email</label>
            <input id="email" v-model="email" type="email" required>
            <label for="name">Name</label>
            <input id="name" v-model="name" type="text" required>
            <label for="password">Password</label>
            <input id="password" v-model="password" type="password" required>
            <label for="passwordConfirmation">Confirm Password</label>
            <input id="passwordConfirmation" v-model="passwordConfirmation" type="password" required>
            <label for="cardNumber">Card Number</label>
            <input id="cardNumber" v-model="cardNumber" type="text" required>
            <label for="expiracyDate">Expiration Date</label>
            <input id="expiracyDate" v-model="expiracyDate" type="date" required>
            <label for="billingAddress">Billing Address</label>
            <input id="billingAddress" v-model="billingAddress" type="text" required>
            <div class="form-actions">
                <button type="submit" @click="createCustomer">Create Account</button>
                <label> Already have an account? <RouterLink to="/login">Login</RouterLink></label>
            </div>
        </form>
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
