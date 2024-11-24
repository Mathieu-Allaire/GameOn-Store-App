<template>
  <main class="layout-container">
    <!-- Left Section: Name, Picture, and Description -->
    <div class="left-section">
      <h1 >{{ gameDetails ? gameDetails.name : "Game Not Found" }}</h1>
      <img
          v-if="gameDetails.picture"
          :src="gameDetails.picture"
          alt="Game Image"
          class="game-image"
      />
      <p class="game-description">{{ gameDetails.description }}</p>

    </div>

    <!-- Right Section: Placeholder Table -->
    <div class="right-section">
      <table class="placeholder-table">
        <thead>
        <tr>
          <th>Column 1</th>
          <th>Column 2</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>Row 1, Col 1</td>
          <td>Row 1, Col 2</td>
        </tr>
        <tr>
          <td>Row 2, Col 1</td>
          <td>Row 2, Col 2</td>
        </tr>
        </tbody>
      </table>
      <!-- Placeholder Buttons -->
      <div class="button-container">
        <button class="placeholder-button">Placeholder Button 1</button>
        <button class="placeholder-button">Placeholder Button 2</button>
      </div>
    </div>
  </main>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080" // Update with your backend's base URL
});

export default {
  name: "game-details",
  data() {
    return {
      gameDetails: null // Holds the game details fetched from the backend
    };
  },
  async created() {
    // Fetch game details dynamically using the route parameter
    this.fetchGameDetails(this.$route.params.gameName);
  },
  methods: {
    async fetchGameDetails(gameId) {
      try {
        const response = await axiosClient.get(`/games/${gameId}`);
        this.gameDetails = response.data;
      } catch (e) {
        // Log the error and handle it appropriately
        console.error("Error fetching game details:", e);
      }
    }
  }
};
</script>

<style>
h1 {
  text-align: center;
  margin-bottom: 0;
}
/* Flexbox container for the layout */
.layout-container {
  display: flex;
  align-items: flex-start;
  gap: 5em;
}

/* Left section: Name, Picture, Description */
.left-section {
  flex: 1;
}

/* Game Image Styling */
.game-image {
  max-width: 100%;
  height: auto;
  margin-top: 0px;
}

/* Game Description Styling */
.game-description {
  margin-top: 1em;
}

/* Button Container Styling */
.button-container {
  margin-top: 1em;
}

/* Placeholder Button Styling */
.placeholder-button {
  margin-right: 1em;
}

/* Right section: Placeholder Table */
.right-section {
  flex: 1;
}

/* Placeholder Table Styling */
.placeholder-table {
  width: 100%;
  border: 1px solid #ccc;
  text-align: left;
}



main {
  padding: 20px;
}

h1 {
  font-size: 2em;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

td {
  padding: 10px;
  border: 1px solid #ccc;
}

img {
  width: 100%;
  max-width: 500px;
  margin-top: 20px;
}
</style>
