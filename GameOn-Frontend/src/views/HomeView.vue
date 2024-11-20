<template>
  <div style="height:80vh; width:100vw;">
    <GameListItem  gamePK="The Witcher 3"/>
    <GameListItem  gamePK="Portal 2"/>
    <GameListItem  gamePK="Elden Ring"/>
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