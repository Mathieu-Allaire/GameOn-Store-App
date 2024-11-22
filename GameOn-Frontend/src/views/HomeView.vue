<template>
    <ul class="gameGrid">
        <li class="game" v-for="gameResponse in gameResponses" :key="gameResponse.name">
            <MainPageGame :game-response="gameResponse"/>
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
    flex-wrap: wrap;
    position: relative;
    left : 0;
    top:0;
    height:100vh;
    flex-flow: row;
    width:100vw;
    list-style: none;

}
.game{
    display: flex;
    object-fit: contain;
    width: 100px;
    min-height: 0;
    min-width: 0;
}


</style>
