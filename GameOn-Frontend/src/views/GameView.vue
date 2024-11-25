<template>
  <main class="layout-container">
    <!-- Top Section: Title, Picture, Description, Price, Category -->
    <div class="top-section">
      <!-- Left: Picture -->
      <div class="picture-container">
        <img
            v-if="gameDetails?.picture"
            :src="gameDetails.picture"
            :alt="gameDetails.name"
            class="game-image"
        />
      </div>

      <!-- Right: Title, Description, Price, Category -->
      <div class="details-container">
        <h1 class="game-title">{{ gameDetails ? gameDetails.name : "Game Not Found" }}</h1>
        <p v-if="gameDetails?.description" class="game-description">
          {{ gameDetails.description }}
        </p>
        <p v-if="gameDetails?.price" class="game-price">
          Price: ${{ gameDetails.price }}
        </p>
        <p v-if="gameDetails?.category" class="game-category">
          Category: {{ gameDetails.category }}
        </p>
      </div>
    </div>

    <!-- Middle Section: Buttons -->
    <div class="button-section">
      <button class="action-button">Add to Wishlist</button>
      <button class="action-button">Add to Cart</button>
      <button class="buy-button">Buy</button>
    </div>

    <!-- Bottom Section: Reviews Table -->
    <div class="reviews-section">
      <table class="reviews-table">
        <thead>
        <tr>
          <th>Reviewer</th>
          <th>Review</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>John Doe</td>
          <td>Great game!</td>
        </tr>
        <tr>
          <td>Jane Smith</td>
          <td>Could use some improvements.</td>
        </tr>
        </tbody>
      </table>
    </div>
  </main>
</template>




<script>
import { Game } from "../dto/Game"; // Import the Game class

export default {
  name: "GameDetailsPage",
  data() {
    return {
      gameDetails: null, // To hold game details
    };
  },
  async created() {
    // Fetch game details by name from the route parameter
    const gameName = this.$route.params.gameNameNoSpace; // Assuming your route is configured as `/game/:name`
    try {
      const response = await Game.findGameByName(gameName);
      if (response.error) {
        console.error(response.error);
      } else {
        this.gameDetails = response;
      }
    } catch (error) {
      console.error("Error fetching game details:", error.message);
    }
  },
};
</script>


<style>
html,
body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center; /* Horizontal centering */
  align-items: center; /* Vertical centering */
  background-color: #f5f5f5; /* Optional: Light background */
  overflow: hidden; /* Prevent scrolling if not needed */
}
/* Center Content in the Layout */
.layout-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* Center content horizontally */
  gap: 2em; /* Add spacing between sections */
  width: 100%;
  margin: 0 auto; /* Center layout in the viewport */
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* Top Section: Title, Picture, Description, Price, Category */
.top-section {
  display: flex;
  align-items: flex-start; /* Align items vertically */
  justify-content: space-between; /* Space out the picture and details */
  gap: 2em; /* Space between picture and details */
  width: 100%;
}

/* Picture Container */
.picture-container {
  flex: 2;
  display: flex;
  justify-content: center; /* Center the picture horizontally */
  align-items: center; /* Center the picture vertically */
}

.game-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 10px;
  object-fit: contain;
}

/* Details Container */
.details-container {
  flex: 2;
  display: flex;
  flex-direction: column;
  gap: 1em; /* Space between details */
}

.game-title {
  font-size: 2em;
  font-weight: bold;
  text-align: left; /* Center the title */
}

.game-description {
  font-size: 1.2em;
}

.game-price,
.game-category {
  font-size: 1.1em;
  font-weight: bold;
}

/* Button Section */
.button-section {
  display: flex;
  justify-content: center; /* Center buttons horizontally */
  gap: 1em; /* Space between buttons */
  width: 100%;
  margin: 2em;
}

.action-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s;
}
.buy-button{
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: green;
  color: white;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s;
}
.buy-button:hover {
  background-color: darkgreen;
}

.action-button:hover {
  background-color: #0056b3;
}

/* Reviews Section */
.reviews-section {
  width: 100%;
}

.reviews-table {
  width: 100%;
  border-collapse: collapse;
}

.reviews-table th,
.reviews-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}

.reviews-table th {
  background-color: #f0f0f0;
}
</style>
