<template>
<div >
<input
  style="border-radius: 10px; width:20vw; height: 100%;"
  type="text"
  placeholder="Search..."
  v-model="searchText"
  @keydown.enter="onSearch"
/>
</div>
</template>


<script>
import { mapActions, mapMutations, mapState } from 'vuex';

export default {
  name: "SearchBar",
  components: {

  },
  data() {
    return {
      searchText:""
    };
  },
  methods: {
    async onSearch() {
      console.log("searchText (search bar):")
      console.log(this.sharedData)
      await this.$router.push("/home")
    },

    ...mapActions(['updateSharedData']),
    udpateSearch() {

    }
  },
  watch: {
    searchText: function () {
      console.log("Updating store")
      this.$store.dispatch('updateSharedData', this.searchText);
    }
  }

};

</script>
