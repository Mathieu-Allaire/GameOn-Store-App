<template>
  <div class="game-view">
    <h1>Manage Games</h1>
    <div class="game-container">
      <!-- Left Column -->
      <div class="column">
        <!-- Create Game Section -->
        <div class="create-game">
          <h2>Create Game</h2>
          <form @submit.prevent="createGame">
            <input type="text" v-model="newGame.name" placeholder="Enter game name" required />
            <input type="text" v-model="newGame.category" placeholder="Enter game category" required />
            <input type="text" v-model="newGame.description" placeholder="Enter game description" required />
            <input type="number" v-model="newGame.price" placeholder="Enter game price" required />
            <input type="number" v-model="newGame.quantity" placeholder="Enter game quantity" required />
            <input type="text" v-model="newGame.picture" placeholder="Enter game picture" required />
            <button type="submit">Create</button>
          </form>
          <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
        </div>
      </div>

      <!-- Right Column -->
      <div class="column">
        <!-- Update Game Section -->
        <div class="update-game">
          <h2>Update Game Price and Quantity</h2>
          <form @submit.prevent="updateGame">
            <input type="text" v-model="updateGameData.name" placeholder="Enter game name" required />
            <div class="update-fields">
              <div class="field">
                <input type="number" v-model.number="updateGameData.price" placeholder="New Price" step="1" min="1" @input="updateGameData.price = Math.floor(updateGameData.price || 0)" />
                <button type="button" @click="updateGamePrice">Update Price</button>
              </div>
              <div class="field">
                <input type="number" v-model.number="updateGameData.quantity" placeholder="New Quantity" />
                <button type="button" @click="updateGameQuantity">Update Quantity</button>
              </div>
            </div>
          </form>
          <p v-if="updateErrorMessage" class="error">{{ updateErrorMessage }}</p>
        </div>

        <!-- Delete Game Section -->
        <div class="delete-game">
          <h2>Delete Game</h2>
          <div class="game-list">
            <h3>List Games</h3>
            <ul>
              <li v-for="game in games" :key="game.name">
                <p><strong>Name:</strong> {{ game.name }}</p>
                <p><strong>Price:</strong> {{ game.price }}</p>
                <p><strong>Quantity:</strong> {{ game.quantity }}</p>
                <button @click="deleteGame(game.name)">Delete</button>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="column">
        <div class="Game-Request">
          <h2>Game Requests</h2>
          <div class="gamerequest-list">
        <h3>Pending Requests</h3>
        <ul>
          <li v-for="grequest in gamerequests" :key="grequest.requestedGameId">
            <div class="request-item">
          <p><strong>Game Name:</strong> {{ grequest.requestedGame }}</p>
          <p><strong>Description:</strong> {{ grequest.requestedGameDescription }}</p>
          <p><strong>Type:</strong> {{ grequest.requestType }}</p>
          <button @click="approveGameRequest(grequest.requestedGameId)" class="approve-btn">Approve</button>
            </div>
          </li>
        </ul>
          </div>
        </div>
      </div>
      </div>
    </div>
</template>

<script>
import { Game } from "../dto/Game";
import { GameResponseDTO } from "../dto/GameResponseDTO";
import { GameRequest } from "../dto/GameRequest";
import { GameRequestResponseDto } from "../dto/GameRequestResponseDto";

export default {
  data() {
    return {
      newGame: {
        name: "",
        category: "",
        description: "",
        price: "",
        quantity: "",
        picture: "",
      },
      updateGameData: {
        name: "",
        price: null,
        quantity: null,
      },
      games: [],
      errorMessage: "",
      updateErrorMessage: "",
      gamerequests: [],

    };
  },
  methods: {
    async createGame() {
      if (
        this.newGame.name &&
        this.newGame.category &&
        this.newGame.description &&
        this.newGame.price &&
        this.newGame.quantity &&
        this.newGame.picture
      ) {
        const existingGame = this.games.find((game) => game.name === this.newGame.name);
        if (existingGame) {
          this.errorMessage = "Game already exists.";
          return;
        }

        try {
          await this.addGame(
            this.newGame.name,
            this.newGame.category,
            this.newGame.description,
            this.newGame.price,
            this.newGame.quantity,
            this.newGame.picture
          );
          // Clear the form
          this.newGame = {
            name: "",
            category: "",
            description: "",
            price: "",
            quantity: "",
            picture: "",
          };
          this.errorMessage = ""; // Clear any previous error
        } catch (error) {
          this.errorMessage =
            error.response?.data?.message || error.message || "Failed to create game. Please try again.";
        }
      }
    },
    async updateGamePrice() {
      if (this.updateGameData.name && this.updateGameData.price !== null) {
        try {
          await Game.updateGamePrice(this.updateGameData.name, this.updateGameData.price);
          // Update the local game's price
          const game = this.games.find((g) => g.name === this.updateGameData.name);
          if (game) {
            game.price = this.updateGameData.price;
          }
          this.updateErrorMessage = "";
        } catch (error) {
          this.updateErrorMessage =
            error.response?.data?.message || error.message || "Failed to update price.";
        }
      } else {
        this.updateErrorMessage = "Please enter game name and new price.";
      }
    },
    async updateGameQuantity() {
      if (this.updateGameData.name && this.updateGameData.quantity !== null) {
        try {
          await Game.updateGameQuantity(this.updateGameData.name, this.updateGameData.quantity);
          // Update the local game's quantity
          const game = this.games.find((g) => g.name === this.updateGameData.name);
          if (game) {
            game.quantity = this.updateGameData.quantity;
          }
          this.updateErrorMessage = "";
        } catch (error) {
          this.updateErrorMessage =
            error.response?.data?.message || error.message || "Failed to update quantity.";
        }
      } else {
        this.updateErrorMessage = "Please enter game name and new quantity.";
      }
    },
    deleteGame(name) {
      const index = this.games.findIndex((game) => game.name === name);
      if (index !== -1) {
        Game.deleteGame(name); // Call the backend method
        this.games.splice(index, 1); // Remove from the list
      }
    },
    async approveGameRequest(id) {
      const index = this.gamerequests.findIndex((grequest) => grequest.requestedGameId === id);
      if (index !== -1) {
        await GameRequest.approveGameRequest(id);
        this.gamerequests.splice(index, 1);
        this.games = [];
        await this.loadGames();
      }
    },
    async addGame(name, category, description, price, quantity, picture) {
      const game = new Game(picture, name, description, price, quantity, category);
      await game.createGame();
      Game.updateGame(name)
      this.games.push({
        name: game.name,
        category: game.category,
        description: game.description,
        price: game.price,
        quantity: game.quantity,
        picture: game.picture,
      });
    },
    async loadGames() {
      try {
        const response = await Game.findAllGames();
        if (response.error) {
          this.errorMessage = response.error;
        } else {
          response.forEach((game) => {
            this.games.push({
              name: game.name,
              category: game.category,
              description: game.description,
              price: game.price,
              quantity: game.quantity,
              picture: game.picture,
            });
          });
        }
      } catch (error) {
        console.error(error);
        this.errorMessage = error.message || "Failed to load games. Please try again.";
      }
    },
    async loadGameRequests() {
    try {
      const response = await GameRequest.findAllGameRequests();
      if (response.error) {
        this.errorMessage = response.error;
      } else {
        response.forEach((grequest) => {
          this.gamerequests.push({
            requestedGameId: grequest.requestedGameId,
            requestedGame: grequest.requestedGame,
            requestedGameDescription: grequest.requestedGameDescription,
            requestType: grequest.requestType,
          });
        });
      }
    } catch (error) {
      console.error(error);
      this.errorMessage = error.message || "Failed to load game requests. Please try again.";
    }
  }
  },
  async mounted() {
    await this.loadGameRequests();
    await this.loadGames();
  },
};
</script>
<style scoped>
.game-view {
  padding: 20px;
  background-color: #f9f9f9;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 2.5em;
  color: #0056b3;
}

h2 {
  color: peru;
}

.game-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: space-around;
}

.column {
  flex: 1;
  min-width: 500px;
  max-width: 45%;
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  color: #04050a;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 1.8em;
}

form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

input {
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px;
  font-size: 1em;
  color: white;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

.update-fields {
  display: flex;
  gap: 10px;
  justify-content: space-between;
}

.update-fields .field {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.error {
  color: red;
  text-align: center;
}

.game-list ul {
  list-style: none;
  padding: 0;
}

.game-list li {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.game-list button {
  align-self: flex-start;
  background-color: #dc3545;
  color: white;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.game-list button:hover {
  background-color: #a71d2a;
}

.Game-Request {
  margin-top: 20px;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.gamerequest-list h3 {
  margin-bottom: 15px;
  font-size: 1.5em;
  text-align: center;
  color: #333;
}

.gamerequest-list ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.request-item {
  background-color: #fff;
  padding: 15px;
  border-radius: 6px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
}

.request-item p {
  margin: 5px 0;
  color: #444;
}

.approve-btn {
  margin-top: 10px;
  padding: 8px 12px;
  background-color: #28a745;
  color: white;
  font-size: 1em;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.approve-btn:hover {
  background-color: #218838;
}


@media (max-width: 768px) {
  .game-container {
    flex-direction: column;
    gap: 15px;
  }

  .column {
    max-width: 100%;
  }
}
</style>