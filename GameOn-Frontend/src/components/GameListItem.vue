<template>
    
    <div class="rect" v-if ="gameDto">
        <ul style="display: flex; list-style:none; gap: 1vw;" >
            <li><img src="../assets/controller.png" style="object-fit: contain;" /></li>
            <li> <p style="align-content: center;">{{ gameDto.name }}}</p></li>
            <li> <p style="align-content: center;">{{ gameDto.price }}}</p></li>
        </ul>
    </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "GameListItem",

  props: {
    gamePK: {
      type: String,
      required: true,
    },
  },

  data() {
    return {
        gameDto: null 
    }
  },
  methods: {


  },
  mounted() {
    axios
      .get(`/games/${encodeURIComponent(this.gamePK)}`)
      .then((response) => {
        this.gameDto = response.data;
      })
      .catch((error) => {
        console.error("Error fetching game data:", error);
      });
  },


};

</script>


<style scoped>
.rect {
    height:10%;
    display: flex;
    
}
</style>

