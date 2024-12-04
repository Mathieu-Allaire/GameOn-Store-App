<template>
  <div class="cart-container">
    <!-- If cart is empty -->
    <div v-if="cartItems.length === 0">
      <h1>Your cart is empty.</h1>
    </div>

    <!-- If cart has items -->
    <div v-else>
      <h1>Your Cart</h1>
      <div
        class="cart-item"
        v-for="item in cartItems"
        :key="item.id"
      >
        <!-- Game Image -->
        <img
          :src="item.image"
          alt="Game Image"
          class="game-image"
        />

        <!-- Game Details -->
        <div class="game-details">
          <h2>{{ item.name }}</h2>
          <p>Unit Price: ${{ item.price.toFixed(2) }}</p>
          <p>Total Price: ${{ (item.price * item.quantity).toFixed(2) }}</p>
          <div class="quantity-controls">
            <button @click="removeGame(item.id)">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="addGame(item.name)">+</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Cart Actions -->
    <div class="cart-actions" v-if="cartItems.length > 0">
      <button
        class="remove-all-button"
        @click="removeAllGames"
      >
        Remove All Items
      </button>
      <button
        class="purchase-button"
        @click="purchaseAllItems"
      >
        Purchase All Items
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CartView",
  data() {
    return {
      cartItems: [],
    };
  },
  methods: {
    async fetchCartItems() {
      try {
        const email = sessionStorage.getItem("Email");
        if (!email) {
          alert("No email found in session storage.");
          return;
        }
        const response = await axios.get(`/cartEmail/${email}`);
        if (response.error) {
          console.error("Error fetching cart items:", response.error);
          alert("Error fetching cart items:", response.error)
        } else {
          this.cartItems = response.items || [];
        }
      } catch (error) {
        alert("Unexpected error fetching cart items: " + error.message);
      }
    },
    async addGame(gameName) {
      try {
        const response = await axios.post("/addGameToCart", { gameName });
        if (response.error) {
          console.error("Error adding game:", response.error);
          alert(response.error);
        } else {
          this.fetchCartItems(); // Refresh cart
        }
      } catch (error) {
        alert("Unexpected error adding game: " + error.message);
      }
    },
    async removeGame(gameId) {
      try {
        const response = await axios.post("/removeGameFromCart", { gameId });
        if (response.error) {
          console.error("Error removing game:", response.error);
          alert(response.error);
        } else {
          this.fetchCartItems(); // Refresh cart
        }
      } catch (error) {
        alert("Unexpected error removing game: " + error.message);
      }
    },
    async removeAllGames() {
      try {
        const response = await axios.post("/removeAllGames");
        if (response.error) {
          console.error("Error removing all games:", response.error);
          alert(response.error);
        } else {
          this.cartItems = []; // Clear the cart
        }
      } catch (error) {
        alert("Unexpected error removing all games: " + error.message);
      }
    },
    async purchaseAllItems() {
      try {
        const response = await axios.post("/purchaseCart", {
          cartId: 1, // Example: Replace with actual cartId if needed
        });
        if (response.error) {
          console.error("Error purchasing items:", response.error);
          alert(response.error);
        } else {
          alert("Purchase successful!");
          this.cartItems = []; // Clear the cart after purchase
        }
      } catch (error) {
        alert("Unexpected error purchasing items: " + error.message);
      }
    },
  },
  mounted() {
    // Uncomment to fetch real cart items on component mount
    // this.fetchCartItems();
  },
};
</script>


<style scoped>
.cart-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  text-align: left;
  color: #2c3e50;
}

.cart-item {
  display: flex;
  align-items: center;
  border: 1px solid #ccc;
  margin: 10px 0;
  padding: 10px;
  border-radius: 5px;
}

.game-image {
  width: 100px;
  height: 100px;
  margin-right: 20px;
  object-fit: cover;
  border-radius: 5px;
}

.game-details {
  flex: 1;
}

.quantity-controls {
  display: flex;
  align-items: center;
  gap: 10px;
}

.quantity-controls button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
}

.quantity-controls button:hover {
  background-color: #113965;
}

.cart-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.remove-all-button,
.purchase-button {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 5px;
}

.remove-all-button:hover {
  background-color: #218838;
}

.purchase-button:hover {
  background-color: #218838;
}
</style>