<template>
<div class="content">
    <p v-if=gameResponse>
    <img :src="gameResponse.picture"/>
    {{this.gameResponse.name}}
    {{this.gameResponse.description}}
    {{this.gameResponse.name}}
    {{this.gameResponse.name}}
    </p>
</div>
</template>


<script>
import { Game } from "../dto/Game";
import { GameResponseDTO } from "../dto/GameResponseDTO";

export default {
  name: "GameView",
  components: {

  },
  data() {
    return {
      gameResponse: null
    }
  },
  async mounted() {
    const name = this.$route.params.name; //From URL path
    console.log("Game:")
    console.log(name);
    const response = await Game.findGameByName(name);
    this.gameResponse = new GameResponseDTO(response);
    console.log("Game details: ");
    console.log(this.gameResponse);
  },


}
</script>


<style scope>
    .content img {

        width:25%;

    }
</style>
