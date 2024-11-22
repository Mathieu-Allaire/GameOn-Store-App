<template>
    <ul class="gameGrid">
        <li v-for="gameResponse in gameResponses" :key="gameResponse.name">
            <MainPageGame :gameResponse="gameResponse"/>
        </li>
    </ul>
</template>

<script>
import MainPageGame from "../components/MainPageGame.vue";
import { Game } from "../dto/Game";
import { GameResponseDTO } from "../dto/GameResponseDTO";

export default {
  name: "HomeView",
  components: {
    MainPageGame,
  },
  data() {
    return {
      gameResponses: [],
    };
  },
  async mounted() {
    const response = await Game.findAllGames();
    this.gameResponses = response.map(response => new GameResponseDTO(response));
    console.log("Games: ");
    console.log(this.gameResponses);
  },
};
</script>

<style scoped>
.gameGrid {
    display: flex;
    margin: 0 0;
    padding: 0 0;
    flex-wrap: wrap;
    position: relative;
    left : 0;
    top:0;
    height:auto;
    flex-flow: row;
    width:100%;
    list-style: none;

}
.gameGrid li {
    display: flex;
    object-fit: contain;
    width: 20%;
    aspect-ratio: 1 ;
}

</style>
