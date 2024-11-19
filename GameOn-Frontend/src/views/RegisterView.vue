<template>
    <div>
        <h1>Create Account</h1>
        <form @submit.prevent="register">
            <label for="email">Email</label>
            <input id="email" v-model="email" type="text" required>
            <label for="name">Name</label>
            <input id="name" v-model="name" type="text" required>
            <label for="password">Password</label>
            <input id="password" v-model="password" type="password" required>
            <label if="passwordConfirmation">Confirm Password</label>
            <input id="passwordConfirmation" v-model="passwordConfirmation" type="password" required>
            <label for="cardNumber">Card Number</label>
            <input id="cardNumber" v-model="cardNumber" type="text" required>
            <label for="expirationDate">Expiration Date</label>
            <input id="expirationDate" v-model="expirationDate" type="text" required>
            <label for="billingAddress">Billing Address</label>
            <input id="billingAddress" v-model="billingAddress" type="text" required>
            <div class="form-actions">
                <button type="submit">Create Account</button>
                <label> Already have an account? <RouterLink to="/login">Login</RouterLink></label>
            </div>
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