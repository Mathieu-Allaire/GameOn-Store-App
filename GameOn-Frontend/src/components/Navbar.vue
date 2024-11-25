<template>
    <nav class="navbar">
        <ul class="nav-links">
            <li><RouterLink to="/">Main Page</RouterLink></li>
            <li><RouterLink to="/about">About</RouterLink></li>
            <li ><RouterLink to="/home">Home</RouterLink></li>

            <li v-if="state.loggedIn === '0'"><RouterLink to="/login">Sign In</RouterLink></li>
            <li v-if="state.loggedIn === '0'"><RouterLink to="/register">Sign Up</RouterLink></li>
            <li v-if="['2', '3'].includes(state.loggedIn)" class="dropdown">
                <a href="javascript:void(0)" class="dropbtn">Manage</a>
                <div class="dropdown-content">
                    <RouterLink to="/manage/games">Games</RouterLink>
                    <RouterLink to="/manage/categories">Categories</RouterLink>
                    <RouterLink to="/manage/employee">Staff</RouterLink>
                </div>
            </li>
            <li><RouterLink to="/debug">DEBUG</RouterLink></li>
            <li v-if="['1','2', '3'].includes(state.loggedIn)"><button @click="logout" class="logout-btn">Logout</button></li>
            <li><button @click="whoisLogged" class="logout-btn">Who</button></li>
        </ul>
    </nav>
</template>

<script setup>
import { RouterLink, useRouter } from "vue-router";
import { state } from '../store/state';

const router = useRouter(); // Get the router instance

function logout() {
    state.loggedIn = '0'; // Update global state to logged out
    sessionStorage.setItem('LoggedIn', '0'); // Update sessionStorage to logged out
    sessionStorage.setItem('Email', ''); // Update sessionStorage to logged out
    console.log(state.loggedIn);
    router.push('/');
}

function whoisLogged() {
    console.log(state.loggedIn);
    console.log(sessionStorage.getItem('Email'));
    
}
</script>

<style scoped>
.navbar {
    background-color: #333;
    padding: 0; /* Remove padding to align to the top */
    position: fixed;
    top: 0;
    left: 0; /* Align navbar to the left */
    width: 100%;
}

.nav-links {
    list-style: none;
    display: flex;
    gap: 1em;
    margin: 0;
    padding: 1em; /* Add padding to the nav links */
}

.nav-links li {
    display: inline;
}

.nav-links a {
    color: white;
    text-decoration: none;
}

.dropdown {
    display: block;
    position: relative;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #050505;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
    z-index: 1;
  display: none;
  position: absolute;
  background-color: #333;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
    color: rgb(243, 243, 243);
    padding: 1em 2em;
    text-decoration: none;
    display: block;
    text-align: left;
}

.dropdown-content a:hover {
    background-color: #f10202;
}

.dropdown:hover .dropdown-content {
    display: block;
}
</style>
