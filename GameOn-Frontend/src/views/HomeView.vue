<template>
    <ul class="gameGrid">
        <li v-for="gameResponse in filteredGameResponses" :key="gameResponse.name">
            <MainPageGame :gameResponse="gameResponse"/>
        </li>
    </ul>

</template>

<script>
import { inject } from 'vue';

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
      searchText : ""
    };
  },

  async mounted() {
    const search = this.$route.params.search;
    console.log("search:")
    console.log(search)
    if (search && search !== "") { this.searchText = search }
    else { this.searchText = ""}

    const response = await Game.findAllGames();
    this.gameResponses = response.map(response => new GameResponseDTO(response));
    console.log("Games: ");
    console.log(this.gameResponses);
  },
  methods: {
    applySearch(search) {

    },
  },
  computed: {
    filteredGameResponses() {
      return this.gameResponses.filter(game =>
        this.searchText === '' || game.name.startsWith(this.searchText)
      );
    }
  }
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
