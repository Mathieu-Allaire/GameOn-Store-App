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