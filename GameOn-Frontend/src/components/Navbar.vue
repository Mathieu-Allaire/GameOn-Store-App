<template>
    <nav class="navbar">
        <ul class="nav-links">
            <li><RouterLink to="/">Main Page</RouterLink></li>
            <li><RouterLink to="/about">About</RouterLink></li>
            <li><RouterLink to="/home">Home</RouterLink></li>
            <li><RouterLink to="/login">Sign In</RouterLink></li>
            <li><RouterLink to="/register">Sign Up</RouterLink></li>
            <li class="dropdown">
                <a href="javascript:void(0)" class="dropbtn">Manage</a>
                <div class="dropdown-content">
                    <RouterLink to="/manage/games">Games</RouterLink>
                    <RouterLink to="/manage/categories">Categories</RouterLink>
                    <RouterLink to="/manage/staff">Staff</RouterLink>
                </div>
            </li>
            <li><RouterLink to="/debug">DEBUG</RouterLink></li>
        </ul>
    </nav>
</template>

<script setup>
import { RouterLink } from "vue-router";
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
