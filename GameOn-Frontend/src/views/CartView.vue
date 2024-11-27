<template>
  <div class="cart-container">
    <div v-if="cartItems.length > 0">
    <h1>Your Cart</h1>
      <div class="cart-item" v-for="item in cartItems" :key="item.gameId">
        <!-- Game Image -->
        <img :src="item.image" alt="Game Image" class="game-image" />

        <!-- Game Details -->
        <div class="game-details">
          <h2>{{ item.name }}</h2>
          <p>Unit Price: ${{ item.price.toFixed(2) }}</p> <!-- Display price for the game -->
          <p>Total Price: ${{ (item.price * item.quantity).toFixed(2) }}</p> <!-- Total Price -->
          <div class="quantity-controls">
            <button @click="removeGame(item.id)">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="addGame(item.name)">+</button>
          </div>
        </div>
      </div>

      <!-- Cart Actions -->
      <div class="cart-actions">
        <button class="remove-all-button" @click="removeAllGames">Remove All Items</button>
        <button class="purchase-button" @click="purchaseAllItems">Purchase All Items</button>
      </div>
    </div>

    <!-- Empty Cart Message -->
    <div v-else>
      <h1>Your cart is empty.</h1>
    </div>
  </div>
</template>

<script>
/*import { ref } from 'vue';
import axios from 'axios';
const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
})
// Reactive state for cart items
const cartItems = ref([
  {
    name: "The Legend of Zelda",
    image: "https://example.com/images/zelda.jpg",
    quantity: 2,
    price: 8.90,
    id: 1,
  },
  {
    name: "Super Mario Odyssey",
    image: "https://example.com/images/mario.jpg",
    quantity: 1,
    price: 60.00,
    id: 2,
  },
  {
    name: "Minecraft",
    image: "https://example.com/images/minecraft.jpg",
    quantity: 3,
    price: 70.12,
    id: 3,
  },
]);

// Simulating cart ID and customer ID for API requests
const cartId = 1; // Replace with the actual cart ID for the user
const customerId = 123; // Replace with the actual customer ID for the user

// Fetch cart items from the backend
const fetchCartItems = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/carts/${cartId}`);
    cartItems.value = response.data.items;
  } catch (error) {
    console.error("Error fetching cart items:", error);
    alert("Error fetching cart items: " + (error.response?.data || error.message));
  }
};

// Add a game to the cart
const addGame = async (gameName) => {
  try {
    const response = await axiosClient.post("/game-add", {
      gameName,
      customerId,
    });
    console.log("Game added successfully:", response.data); // Log response for debugging
    await fetchCartItems(); // Refresh the cart
  } catch (error) {
    if (error.response) {
      // The request was made, but the server responded with a status code that falls out of the range of 2xx
      console.error("Server responded with an error:", error.response.data);
      alert("Error adding game to cart: " + error.response.data);
    } else if (error.request) {
      // The request was made but no response was received
      console.error("No response received from server:", error.request);
      alert("Error: No response received from the server: " + error.request);
    } else {
      // Something happened in setting up the request that triggered an error
      console.error("Error setting up the request:", error.message);
      alert("Error adding game to cart: " + error.message);
    }
  }
};


// Remove a game from the cart
const removeGame = async (gameId) => {
  try {
    await axios.post(`http://localhost:8080/api/game-remove`, {
      specificGameId: gameId,
      cartId,
    });
    await fetchCartItems(); // Refresh the cart
  } catch (error) {
    if (error.response) {
      // The request was made, but the server responded with a status code that falls out of the range of 2xx
      console.error("Server responded with an error:", error.response.data);
      alert("Error removing game from cart: " + error.response.data);
    } else if (error.request) {
      // The request was made but no response was received
      console.error("No response received from server:", error.request);
      alert("Error: No response received from the server: " + error.request);
    } else {
      // Something happened in setting up the request that triggered an error
      console.error("Error setting up the request:", error.message);
      alert("Error removing game from cart: " + error.message);
    }
  }
  ;

// Remove all games from the cart
  const removeAllGames = async () => {
    try {
      await axios.post(`http://localhost:8080/api/remove-all`, {cartId});
      await fetchCartItems(); // Refresh the cart
    } catch (error) {
      if (error.response) {
        // The request was made, but the server responded with a status code that falls out of the range of 2xx
        console.error("Server responded with an error:", error.response.data);
        alert("Error adding game to cart: " + error.response.data);
      } else if (error.request) {
        // The request was made but no response was received
        console.error("No response received from server:", error.request);
        alert("Error: No response received from the server: " + error.request);
      } else {
        // Something happened in setting up the request that triggered an error
        console.error("Error setting up the request:", error.message);
        alert("Error removing all games from cart: " + error.message);
      }
    }
  };

// Purchase all items
  const purchaseAllItems = async () => {
    try {
      await axios.post(`http://localhost:8080/api/createOrder`, {cartId});
      cartItems.value = []; // Clear cart after purchase
      alert("Order created successfully!");
    } catch (error) {
      if (error.response) {
        // The request was made, but the server responded with a status code that falls out of the range of 2xx
        console.error("Server responded with an error:", error.response.data);
        alert("Error buying games from cart: " + error.response.data);
      } else if (error.request) {
        // The request was made but no response was received
        console.error("No response received from server:", error.request);
        alert("Error: No response received from the server: " + error.request);
      } else {
        // Something happened in setting up the request that triggered an error
        console.error("Error setting up the request:", error.message);
        alert("Error buying games from cart: " + error.message);
      }
    }
  };
}*/


import { Cart } from "@/dto/Cart.js";

export default {
  name: "CartView",
  data() {
    return {
      cartId: 1, // Replace with actual cart ID
      customerId: 123, // Replace with actual customer ID
      cartItems: [
        {
          name: "The Legend of Zelda",
          image: "https://example.com/images/zelda.jpg",
          quantity: 2,
          price: 8.9,
          id: 1,
        },
        {
          name: "Super Mario Odyssey",
          image: "https://example.com/images/mario.jpg",
          quantity: 1,
          price: 60.0,
          id: 2,
        },
        {
          name: "Minecraft",
          image: "https://example.com/images/minecraft.jpg",
          quantity: 3,
          price: 70.12,
          id: 3,
        },
      ], // Provided cartItems, // Initialize as empty, will fetch from backend
    };
  },
  methods: {
    async fetchCartItems() {
      try {
        const response = await Cart.getCart(this.cartId);
        if (response.error) {
          console.error("Error fetching cart items:", response.error);
          alert(response.error);
        } else {
          this.cartItems = response.items; // Assume API returns a property `items`
        }
      } catch (error) {
        alert("Unexpected error fetching cart items:", error);
      }
    },
    async addGame(gameName) {
      try{
        const cart = new Cart(this.customerId, gameName);
        const response = await cart.addGameToCart();
        if (response.error) {
          console.error("Error adding game:", response.error);
          alert(response.error);
        } else {
          this.fetchCartItems(); // Refresh cart
        }
      } catch (error) {
        alert("Unexpected error adding game: ", error);
      }
    },
    async removeGame(gameId) {
      try {
        const response = await Cart.removeGameFromCart({
          specificGameId: gameId,
          cartId: this.cartId,
        });
        if (response.error) {
          console.error("Error removing game:", response.error);
          alert(response.error);
        } else {
          this.fetchCartItems(); // Refresh cart
        }
      } catch (error) {
        alert("Unexpected error removing game:", error);
      }
    },
    async removeAllGames() {
      try {
        const response = await Cart.removeAllGamesFromCart(this.cartId);
        if (response.error) {
          console.error("Error removing all games:", response.error);
          alert(response.error);
        } else {
          this.fetchCartItems(); // Refresh cart
        }
      } catch (error) {
        alert("Unexpected error removing all games:", error);
      }
    },
    async purchaseAllItems() {
      try {
        const response = await Cart.createOrder({ cartId: this.cartId });
        if (response.error) {
          console.error("Error purchasing items:", response.error);
          alert(response.error);
        } else {
          alert("Order created successfully!");
          this.cartItems = []; // Clear the cart
        }
      } catch (error) {
        alert("Unexpected error purchasing items:", error);
      }
    },
  },
  mounted() {
    this.fetchCartItems(); // Fetch items on component mount
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