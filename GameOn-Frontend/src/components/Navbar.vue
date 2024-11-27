<template>
  <nav class="navbar">
    <ul class="nav-links">
      <li><RouterLink to="/">Main Page</RouterLink></li>
      <li><RouterLink to="/about">About</RouterLink></li>
      <li><RouterLink to="/home">Home</RouterLink></li>

      <!-- Navbar Buttons -->
      <li v-if="state.loggedIn === '1'"><RouterLink to="/cart">My Cart</RouterLink></li>
      <li v-if="state.loggedIn === '1'"><RouterLink to="/orders">My Orders</RouterLink></li>
      <li v-if="state.loggedIn === '2' || state.loggedIn === '3'"><RouterLink to="/customers">All Customers</RouterLink></li>

      <!-- Other navbar links -->
      <li v-if="state.loggedIn === '0'"><RouterLink to="/login">Sign In</RouterLink></li>
      <li v-if="state.loggedIn === '0'"><RouterLink to="/register">Sign Up</RouterLink></li>
      <li v-if="['3'].includes(state.loggedIn)" class="dropdown">
        <a href="javascript:void(0)" class="dropbtn">Manage</a>
        <div class="dropdown-content">
          <RouterLink to="/manage/games">Games</RouterLink>
          <RouterLink to="/manage/categories">Categories</RouterLink>
          <RouterLink to="/manage/employee">Staff</RouterLink>
        </div>
      </li>
      <li v-if="state.loggedIn === '2'"><RouterLink to="/manage/requests">Game Request</RouterLink></li>
      <li><RouterLink to="/debug">DEBUG</RouterLink></li>

      <!-- Logout and Search -->
      <li> <SearchBar @searchEvent="searchEvent" /> </li>
      <li
        style="background-color: orange; cursor: pointer; color: black; border-radius: 1vw; padding:0.25vw; padding-left: 3vw; padding-right: 3vw;"
        @click="displayCategories"
        @mouseleave="hideCategories"
      >
        Category
        <div v-if="categoryClicked" class="categoryBox">
          <ul class="category-list" multiple>
            <li
              v-for="categoryResponse in categoryResonses"
              :key="categoryResponse.name"
              class="category-item"
              @click="goToCategory(categoryResponse.name)"
            >
              {{ categoryResponse.name }}
            </li>
          </ul>
        </div>
      </li>
      <li v-if="['1','2', '3'].includes(state.loggedIn)">
        <button @click="logout" class="logout-btn">Logout</button>
      </li>
      <li><button @click="whoisLogged" class="logout-btn">Who</button></li>
    </ul>
  </nav>

  <!-- White page content -->
  <div class="content">
    <!-- Removed "My Cart" and "My Orders" buttons -->
    <p>GameOn Welcome Customer</p>
  </div>
</template>



<script >

import { mapActions } from "vuex";
import { RouterLink, useRouter } from "vue-router";
import { state } from '../store/state';

import SearchBar from "./SearchBar.vue"

import { Category } from "../dto/Category"
import { CategoryResponseDto } from "../dto/CategoryResponseDto"

export default {
  setup() {
    return { state };
  },
  name: "Navbar",
  components: {
    SearchBar
  },
  data() {
    return {
      categoryClicked:false,
      categoryResonses : [],
      category: ""
    }
  },
  methods: {
    whoisLogged() {
       console.log(state.loggedIn);
       console.log(sessionStorage.getItem('Email'));
    },
    logout() {
        state.loggedIn = '0'; // Update global state to logged out
        sessionStorage.setItem('LoggedIn', '0'); // Update sessionStorage to logged out
        sessionStorage.setItem('Email', ''); // Update sessionStorage to logged out
        console.log(state.loggedIn);
        this.$router.push('/');
    },
    searchEvent(data) { //pass to app
      console.log("NavBar received: ")
      console.log(data)
      this.$emit("searchEvent", data)
      console.log("navbar transmitted")
    },
    displayCategories() {
      this.updateCategories();
      this.categoryClicked = true;

    },
    hideCategories() {
      this.categoryClicked = false;
    },
    async updateCategories() {
      const response = await Category.findAllCategories();
      this.categoryResonses = response.map(response => new CategoryResponseDto(response));
      console.log("Categories: ");
      console.log(this.categoryResonses);
    },
    async goToCategory(c) {
      console.log(c)
      this.category = c;
      await this.$router.push("/home");
    },
    ...mapActions(["updateSharedCategory"]),
  },
  async mounted() {
    this.updateCategories();
  },
  watch: {
      searchText: function () {
          console.log("Updating search store");
          this.$store.dispatch("updateSharedSearch", this.searchText);
      },
    category: function () {
      console.log("Updating category store")
      this.$store.dispatch("resetSearch",);
      this.$store.dispatch("updateSharedCategory", this.category);
    }
  },

}
</script>

<style scoped>
.categoryBox {
  display: block;
}

.category-item {
  display: block;
}

.category-item:hover {
  color: white;
}

.category-list {
  list-style: none; /* Removes default list style */
  padding: 0;
  margin: 0;
  display: block;
  flex-direction: column;
  display: flex;
  justify-content: center;
}

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