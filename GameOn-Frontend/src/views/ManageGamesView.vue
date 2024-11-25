<template>
  <div class="manage-games">
    <h1>Game Management System</h1>
    <div class="columns">
      <div class="column">
        <h2>Games</h2>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="gameResponse in gameResponses" :key="gameResponse.name">
              <td>{{ gameResponse.name }}</td>
              <td><button class="remove-button" @click="deleteGame(gameResponse.name)">Remove</button></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="column">
        <h2>Game Requests</h2>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="gameRequestResponse in gameRequestResponses" :key="gameRequestResponse.email">
              <td>{{ gameRequestResponse.name }}</td>
              <td>
                <button @click="approveGameRequest()">Approve</button>
                <button @click="rejectGameRequest()">Reject</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="column" >
        <div v-if="!debug_is_manager">
            <h2>Request Game </h2>
            <form @submit.prevent="requestAddGame">
            <label for="name">Game Name</label>
            <input id="name" v-model="gameRequest.requestedGameName" type="text" required>
            <button type="submit" >Request Game</button>
            </form>
        </div>

        <div v-if="debug_is_manager">
            <h2>Create Game </h2>
            <form @submit.prevent="createGame">
            <label for="name">Name</label>
            <input id="name" v-model="gameToAdd.name" type="text" required>

            <label for="quantity">Quantity</label>
            <input id="quantity" v-model="gameToAdd.quantity" type="number" required>

            <label for="category">Category</label>
            <input id="category" v-model="gameToAdd.category" type="text" required>

            <label for="price">Price</label>
            <input id="price" v-model="gameToAdd.price" type="number" step="0.01" required>

            <label for="pictureUrl">Picture URL</label>
            <input id="pictureUrl" v-model="gameToAdd.picture" type="url" required>

            <label for="description">Description</label>
            <textarea id="description" v-model="gameToAdd.description" required></textarea>

            <button type="submit" > Create Game</button>
            </form>
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
  name: "ManageGamesView",
  components: {

  },
  data() {
    return {
      gameToAdd : new Game(),
      gameResponses:[],
      gameRequestResponses : [],

      gameRequest : new GameRequest(),
      debug_is_manager: true
    }
  },
  methods: {
    async updateGames() {
      const gameResponses = await Game.findAllGames();
      this.gameResponses = gameResponses.map(gameResponses => new GameResponseDTO(gameResponses));
      console.log("Games: ");
      console.log(this.gameResponses);
    },
    async updateGameRequests() {
      const gameRequestResponse = await GameRequest.findAllGameRequests();
      this.gameRequestResponses = gameRequestResponse.map(gameRequestResponse => new GameRequestResponseDto(gameRequestResponse));
      console.log("Games Requests: ");
      console.log(this.gameRequestResponses);
    },
    async deleteGame(gameName) {
      const response = await Game.deleteGame(gameName);
      if (!response || response.error) {
        console.log("Error deleting game: ", response.error);
      } else {
        await this.updateGames();
      }
    },
    async createGame() {
      console.log('Game to Add:', this.gameToAdd);
      await this.gameToAdd.createGame();
      await this.updateGames();
    },
    async getEmail() {
      //i.e. user.session.getEmail
      const DEBUG_EMAIL = "bobby@mail.com"
      return DEBUG_EMAIL;
    },
    async requestAddGame() {
      this.gameRequest.employeeEmail = await this.getEmail();
      this.gameRequest.requestType = "Create";
      console.log("Game Request: ", this.gameRequest);
      await this.gameRequest.createGameRequest();
      await this.updateGameRequests();
    },

  },
  async mounted() {
    await this.updateGames();
    await this.updateGameRequests();
  },

}


const approveRequest = (id) => {
  const requestIndex = gameRequests.value.findIndex(request => request.id === id);
  if (requestIndex !== -1) {
    const approvedGame = gameRequests.value.splice(requestIndex, 1)[0];
    games.value.push(approvedGame);
  }
};


</script>

<style scoped>
.manage-games {
  padding: 40px;
}

h1 {
  color: #2f3c53;
  font-size: 2em; /* Increase font size */
}

h2 {
  color: #ff0000;
  font-size: 1.5em; /* Increase font size */
}

.columns {
  display: flex;
  gap: 40px;
}

.column {
  flex: 1;
  padding: 100px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #645656;
  min-width: 400px; /* Ensure columns have a minimum width */
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  border: 1px solid #ccc;
  padding: 30px;
  text-align: left;
}

th {
  background-color: #000000;
  color: white;
  text-align: left;
  font-size: 1.5em; /* Increase font size */
  padding: 10px 30px; /* Increase padding */
}

button {
  padding: 10px 20px; /* Increase padding */
  font-size: 1em; /* Increase font size */
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.remove-button {
  background-color: #f44336; /* Red background */
}

.remove-button:hover {
  background-color: #d32f2f; /* Darker red on hover */
}

form {
  display: flex;
  flex-direction: column;
  gap: 1em;
  margin-top: 20px;
}

label {
  font-weight: bold;
}

input, textarea {
  padding: 0.5em;
  border-radius: 0.5em;
  border: 1px solid #ccc;
  font-size: 1em;
}
</style>
