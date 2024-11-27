<template>
    <div class="container">
        <img :src="gameResponse.picture" :alt="gameResponse.name" @click="goToGamePage" style="cursor: pointer;"
        @mouseover="isHovered=true"
        @mouseleave="isHovered=false"
        />
        <div class="hoverBox">
            <span class="text">{{gameResponse.name}}</span> <span class="text">{{gameResponse.price}}$</span>
        </div>
    </div>
</template>

<script>
import { GameResponseDTO } from "../dto/GameResponseDTO";

export default {
  name: "MainPageGame",
  data() {
    return {
      isHovered : false,
    }
  },
  props: {
    gameResponse: {
      type: GameResponseDTO,
      required: true
    },
  },
  methods: {
    async goToGamePage() {
      console.log("Go to game page");
      this.$router.push(`/home/${this.gameResponse.name}`);
    }
  }
}

</script>

<style scoped>
    .hoverBox {
        bottom:0;
        left:0;
        position: absolute;
        background-color: black;
        width: 100%;
        height: 10%;
        justify-content: space-between;
        display: flex;
        padding-left:2vw;
        padding-right:2vw;
        opacity: 0;
        transition: opacity 0.2s ease;
        align-items:center;
    }
    .container:hover .hoverBox {
        opacity:1
    }
    .text {
        font-size: 1vw;  /* Adjust based on container */
          line-height: 1;  /* Adjust if multiline text */
          white-space: nowrap; /* Prevent wrapping for single line */
    }


    .container {
    display: flex;
    object-fit: contain;
    width: 100%;
    aspect-ratio: 1 ;
    position:relative;
    }
    .container:hover {
        border-style: solid;
        border-width: 1px;
        border-color: greenyellow;
    }


</style>
