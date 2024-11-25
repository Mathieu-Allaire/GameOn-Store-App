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
      <!-- <div class="column">
        <h2>Game Requests</h2>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="request in gameRequests" :key="request.id">
              <td>{{ request.name }}</td>
              <td><button @click="approveRequest(request.id)">Approve</button></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="column" >
        <h2>Create Game</h2>
        <form @submit.prevent="createGame">
          <label for="name">Name</label>
          <input id="name" v-model="newGame.name" type="text" required>

          <label for="quantity">Quantity</label>
          <input id="quantity" v-model="newGame.quantity" type="number" required>

          <label for="price">Price</label>
          <input id="price" v-model="newGame.price" type="number" step="0.01" required>

          <label for="pictureUrl">Picture URL</label>
          <input id="pictureUrl" v-model="newGame.pictureUrl" type="url" required>

          <label for="description">Description</label>
          <textarea id="description" v-model="newGame.description" required></textarea>

          <button type="submit">Create Game</button>
        </form>
      </div> -->
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
      gameResponses:[],
      gameRequestResponses : [],
    }
  },
  async mounted() {
    const gameResponses = await Game.findAllGames();
    this.gameResponses = gameResponses.map(gameResponses => new GameResponseDTO(gameResponses));
    console.log("Games: ");
    console.log(this.gameResponses);

    const gameRequestResponse = await GameRequest.findAllGameRequests();
    console.log("DATA");
    console.log(gameRequestResponse.data);
    this.gameRequestResponses = gameRequestResponse.map(gameRequestResponse => new GameRequestResponseDto(gameRequestResponse));
    console.log("Games Requests: ");
    console.log(this.gameRequestResponses);
  },
  methods: {
    async deleteGame(gameName) {
      const response = await Game.deleteGame(gameName);
      if (response.error) {
        console.log("Error deleting game: ", response.error);
      } else {
        this.myArray = this.myArray.filter(game => game.name !== gameName);
      }
    },
  },
}


const approveRequest = (id) => {
  const requestIndex = gameRequests.value.findIndex(request => request.id === id);
  if (requestIndex !== -1) {
    const approvedGame = gameRequests.value.splice(requestIndex, 1)[0];
    games.value.push(approvedGame);
  }
};

const createGame = () => {
  const game = { ...newGame.value, id: Date.now() };
  games.value.push(game);
  newGame.value = {
    name: '',
    quantity: 0,
    price: 0.00,
    pictureUrl: '',
    description: ''
  };
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
