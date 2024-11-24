<template>
    <img :src="gameResponse.picture" :alt="gameResponse.name" @click="goToGamePage" style="cursor: pointer;"/>
</template>

<script>
import { GameResponseDTO } from "../dto/GameResponseDTO";

export default {
  name: "MainPageGame",
  props: {
    gameResponse: {
      type: GameResponseDTO,
      required: true
    },
  },
  methods: {
    async goToGamePage() {
      console.log("Go to game page");
      this.$router.push(`/game/${this.gameResponse.name}`);
    }
  }
}

</script>
