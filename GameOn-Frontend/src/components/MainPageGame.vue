<template>
    <img :src="gameResponse.picture" :alt="gameResponse.name"/>
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
  }
}

</script>
