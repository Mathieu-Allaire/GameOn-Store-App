<template>
  <div class="wishlist-view">
    <h1>My Wishlist</h1>
    <div v-if="loading" class="loading">Loading your wishlist...</div>
    <div v-if="error" class="error">{{ error }}</div>
    <ul v-if="wishlist.length > 0" class="wishlist-list">
      <li v-for="game in wishlist" :key="game.gameName" class="wishlist-item">
        <img :src="game.gamePicture" alt="Game Image" class="game-picture" />
        <div class="game-info">
          <h2>{{ game.gameName }}</h2>
          <p>Price: ${{ game.gamePrice.toFixed(2) }}</p>
          <p>Available Quantity: {{ game.gameQuantity }}</p>
        </div>
        <button @click="removeGame(game.gameName)" class="remove-button">Remove</button>
      </li>
    </ul>
    <div v-else class="empty-wishlist">
      <p>Your wishlist is empty.</p>
    </div>
  </div>
</template>

<script>
import { GameWishlist } from "../dto/GameWishlist";

export default {
  name: "WishlistView",
  data() {
    return {
      wishlist: [], // Holds the list of games in the wishlist
      loading: true, // Loading state
      error: null, // Error message, if any
      customerEmail: sessionStorage.getItem('Email'), // Replace with dynamic data in a real app
    };
  },
  methods: {
    async fetchWishlist() {
      this.loading = true;
      this.error = null;
      try {
        const response = await GameWishlist.getAllGamesFromWishlist(this.customerEmail);
        if (response.error) {
          throw new Error(response.error);
        }
        this.wishlist = response.map((game) => ({
          gameName: game.gameName,
          gameQuantity: game.gameQuantity,
          gamePicture: game.gamePicture,
          gamePrice: game.gamePrice,
        }));
      } catch (error) {
        this.error = error.message;
      } finally {
        this.loading = false;
      }
    },
    async removeGame(gameName) {
      try {
        const response = await GameWishlist.removeGameFromWishlist.call({
          gameName,
          customerEmail: this.customerEmail,
        });
        if (response.error) {
          throw new Error(response.error);
        }
        this.wishlist = this.wishlist.filter((game) => game.gameName !== gameName);
      } catch (error) {
        this.error = `Failed to remove ${gameName}: ${error.message}`;
      }
    },
  },
  mounted() {
    this.fetchWishlist();
  },
};
</script>

<style scoped>
.wishlist-view {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.loading,
.error {
  text-align: center;
  margin: 20px 0;
  color: red;
}

.wishlist-list {
  list-style: none;
  padding: 0;
}

.wishlist-item {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
}

.game-picture {
  max-width: 100px;
  max-height: 100px;
  margin-right: 20px;
}

.game-info {
  flex-grow: 1;
}

.remove-button {
  background-color: #ff4d4f;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

.remove-button:hover {
  background-color: #e43f3f;
}

.empty-wishlist {
  text-align: center;
  margin: 20px 0;
  font-size: 18px;
  color: #555;
}
</style>
