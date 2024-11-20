<template>
  <div style="height:80vh; width:100vw; display:flex;">
    <GameListItem />
    <GameListItem  />
    <GameListItem  />
  </div>
</template>


<script>
import GameListItem from '../components/GameListItem.vue';

export default {
  name: "App",
  components: {
    GameListItem, 
  },
};
</script>

<style scoped>

</style>