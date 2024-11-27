<template>
  <div class="game-request-view">
    <h1>Manage Games</h1>
    <div class="game-request-container">
      <!-- Left Column -->
      <div class="column">
        <!-- Create Game Section -->
        <div class="create-game-request">
          <h2>Create Game Request</h2>
          <form @submit.prevent="createGameRequest">
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

      <div class="column">
        <div class="Game-Request">
          <h2>Game Request</h2>
          <div class="game-request-list">
            <h3>List Game Requests</h3>
            <ul>
            <li v-for="grequest in gamerequests" :key="grequest.requestedGameId ">
              <p><strong>Requested Game Id:</strong>  {{ grequest.requestedGameId }}</p>
              <p><strong>Requested Game Name:</strong>  {{ grequest.requestedGame }}</p>
              <p><strong>Requested Game Description:</strong> {{ grequest.requestedGameDescription }}</p>
              <p><strong>Request Type:</strong> {{ grequest.requestType }}</p>
              </li>
            </ul>
          </div>
        </div>

        
      </div>
      <div class="column">
          <div class="archive-game-request">
        <h2>Archive Game Request</h2>
        <div class="game-list">
          <h3>List Games</h3>
          <ul>
            <li v-for="game in games" :key="game.name">
              <p><strong>Name:</strong> {{ game.name }}</p>
              <p><strong>Price:</strong> {{ game.price }}</p>
              <p><strong>Quantity:</strong> {{ game.quantity }}</p>
              <button @click="archiveGame(game.name)">Request Archive</button>
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
    async createGameRequest() {
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
            error.response?.data?.message || error.message || "Failed to create game request. Please try again.";
        }
      }
    },
    async addGame(name, category, description, price, quantity, picture) {
      const game = new Game(picture, name, description, price, quantity, category);
      await game.createGame();
      const gameRequest = new GameRequest(sessionStorage.getItem('Email'), name, "Create");
      await gameRequest.createGameRequest();
      this.gamerequests = [];
      await this.loadGameRequests();

    },
    async archiveGame(name) {
      const game = this.games.find((game) => game.name === name);
      if (game) {
          try {
          // if the game is already archived, return error
          const existingGame = this.gamerequests.find((grequest) => grequest.requestedGame === name);
          if (existingGame) {
              alert("Game already archived.");
              return;
          }
          const gameRequest = new GameRequest(sessionStorage.getItem('Email'), name, "Archive");
          const response = await gameRequest.createGameRequest();
          this.games.splice(this.games.indexOf(game), 1);
          // Push the request to gamerequests if the response is successful
          this.gamerequests.push({
              requestedGameId: response.requestedGameId || gameRequest.requestedGameId,
              requestedGame: response.requestedGame || gameRequest.requestedGameName,
              requestedGameDescription: response.requestedGameDescription || "",
              requestType: response.requestType || gameRequest.requestType,
          });
          } catch (error) {
          console.error("Error archiving game request:", error);
          this.errorMessage = error.message || "Failed to archive game request.";
          }
      }
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
.game-r-view {
  padding: 20px;
  background-color: #f9f9f9;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 2.5em;
}

.game-request-container {
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
}

.game-request-list {
background-color: #f1f1f1;
padding: 20px;
border-radius: 8px;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.game-request-list h3 {
font-size: 1.5em;
margin-bottom: 15px;
text-align: center;
color: #333;
}

.game-request-list ul {
list-style: none;
padding: 0;
margin: 0;
}

.game-request-list li {
background-color: #ffffff;
padding: 15px 20px;
margin-bottom: 10px;
border-radius: 8px;
border: 1px solid #ddd;
transition: transform 0.3s, box-shadow 0.3s;
}

.game-request-list li:hover {
transform: translateY(-3px);
box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.game-request-list li p {
margin: 5px 0;
font-size: 1em;
color: #555;
}

.game-request-list li p strong {
font-weight: 600;
color: #222;
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

@media (max-width: 768px) {
  .game-container {
    flex-direction: column;
    gap: 15px;
  }

  .game-request-list {
  padding: 15px;
}

.game-request-list h3 {
  font-size: 1.2em;
}

.game-request-list li {
  padding: 10px 15px;
}

.game-request-list li p {
  font-size: 0.95em;
}

  .column {
    max-width: 100%;
  }
}
</style>