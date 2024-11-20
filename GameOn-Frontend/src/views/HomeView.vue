<template>
  <div>
    <GameListItem />
    <GameListItem />
    <GameListItem />
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
  .view {
    position: relative;
    width:100%;
    top: 0;
    left: 0;
  }
</style>