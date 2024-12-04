<template>
    <nav class="navbar" >
        <ul class="nav-links">
            <li ><RouterLink to="/">Home</RouterLink></li>
            <li v-if="state.loggedIn === '1'"><RouterLink to="/cart">My Cart</RouterLink></li>
            <li v-if="state.loggedIn === '1'"><RouterLink to="/orders">My Orders</RouterLink></li>
            <li v-if="state.loggedIn === '2' || state.loggedIn === '3'"><RouterLink to="/customers">All Customers</RouterLink></li>
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

            <li> <SearchBar @searchEvent="searchEvent"/> </li>
            <li style="background-color: orange; cursor: pointer; color: black; border-radius: 1vw; padding:0.25vw; padding-left: 3vw; padding-right: 3vw;"
            @click="displayCategories" @mouseleave="hideCategories"> Category
                <div v-if="categoryClicked" class="categoryBox">
                <ul  class="category-list" multiple>
                    <li v-for="categoryResponse in categoryResonses" :key="categoryResponse.name"  class="category-item" @click="goToCategory(categoryResponse.name)">
                        {{categoryResponse.name}}
                    </li>
                </ul>
                </div>
            </li>

            <li v-if="['1','2', '3'].includes(state.loggedIn)"><button @click="logout" class="logout-btn">Logout</button></li>
            <li v-if="['10'].includes(state.loggedIn)"><button @click="whoisLogged" class="who-btn">Who</button></li>

          <li v-if="state.loggedIn === '1'"><RouterLink to="/wishlist">Wishlist</RouterLink></li>


        </ul>
    </nav>
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
      await this.$router.push("/");
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





.navbar {
    background-color: #222; /* Dark background for contrast */
    padding: 1em 2em; /* Add padding for spacing */
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between; /* Space elements apart */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow */
    z-index: 1000;
    font-family: 'Arial', sans-serif; /* Clean font style */

}

.nav-links {
    list-style: none;
    display: flex;
    gap: 2em; /* Add spacing between links */
    margin: 0;
    padding: 0;
    align-items: center;
    justify-items: center;
}

.nav-links li {
    display: inline-block;
}

.nav-links a {
    color: white;
    text-decoration: none;
    font-size: 1em;
    font-weight: 500;
    transition: color 0.3s ease, transform 0.2s ease; /* Smooth hover transition */
}

.nav-links a:hover {
    color: #ffa726;
    transform: scale(1.1); /* Slight zoom effect on hover */
}

.categoryBox {

    display: block;
    position: absolute;

    background-color: #333;
    padding: 0.75em;
    border-radius: 0.5em;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
    z-index: 10;
}

.category-list {

    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    flex-direction: column;
    align-items: flex-start; /* Align items to the start */
}

.category-item {
    padding: 0.5em 1em;
    color: white;
    font-size: 1em;
    cursor: pointer;
    border-radius: 0.25em;
    transition: background-color 0.3s ease, transform 0.2s ease;
}

.category-item:hover {
    background-color: #ff9800;
    color: #222;
    transform: scale(1.05); /* Slight zoom on hover */
}

.dropdown {
    position: relative;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #444;
    min-width: 200px;
    border-radius: 0.5em;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
    z-index: 10;
}

.dropdown-content a {
    color: white;
    padding: 0.75em 1.5em;
    text-decoration: none;
    display: block;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.dropdown-content a:hover {
    background-color: #ffa726;
    color: #222;
}

.dropdown:hover .dropdown-content {
    display: block;
    animation: fadeIn 0.3s ease-in-out; /* Smooth dropdown appearance */
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.logout-btn {
    background-color: #f44336;
    color: white;
    border: none;
    padding: 0.6em 1.2em;
    border-radius: 0.5em;
    font-size: 0.9em;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease;
}

.logout-btn:hover {
    background-color: #d32f2f;
    transform: scale(1.05); /* Slight zoom on hover */
}

.category {
    background-color: #ffa726;
    color: #222;
    padding: 0.6em 1.5em;
    border-radius: 0.5em;
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.2s ease;
}

.category:hover {
    background-color: #ff9800;
    transform: scale(1.1);
}

@media (max-width: 768px) {
    .navbar {
        flex-direction: column;
        align-items: flex-start;
        padding: 1em;
    }

    .nav-links {
        flex-direction: column;
        gap: 1em;
        width: 100%;
        justify-content: flex-start;
    }

    .nav-links li {
        width: 100%;
        text-align: left;
    }

    .logout-btn {
        width: 100%;
        text-align: center;
    }
}

</style>
