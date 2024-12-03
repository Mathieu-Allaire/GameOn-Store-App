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

import { Cart } from "@/dto/Cart.js";
import { CartResponseDto } from "@/dto/CartResponseDto";

export default {
  name: "CartView",
  data() {
    return {
      cartItems: [
        {
          name: "The Legend of Zelda",
          image: "https://www.zeldadungeon.net/wiki/Gallery%3AThe_Legend_of_Zelda#/media/File:Link-Kneeling-Hyrule-Map.png",
          quantity: 2,
          price: 8.9,
          id: 1,
        },
        {
          name: "Super Mario Odyssey",
          image: "https://www.mariowiki.com/Gallery%3ASuper_Mario_Odyssey#/media/File:SMO_Artwork_Box_Art.png",
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
        const id = sessionStorage.getItem("CartId");
        if (!id) {
          //alert("Cart ID is missing. Please log in.");
        } else {
          //alert(" id =", id);
        }
        const response = await Cart.getCart(this.id);
        if (response.error) {
          console.error("Error fetching cart items:", response.error);
          //alert(response.error);
        } else {
          this.cartItems = response.items; // Assume API returns a property `items`
        }
      } catch (error) {
        //alert("Unexpected error fetching cart items:", error);
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
    async removeAllGamesFromCart(customerId) {
    const path = "/remove-all";
    try {
      const response = await axios.post(path, { customerId });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
    },
    async purchaseAllItems(cartId) {
      const path = "/createOrder";
      try {
        const response = await axios.post(path, { cartId });
        return response.data;
      } catch (error) {
        return { error: error.message };
      }
    }
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