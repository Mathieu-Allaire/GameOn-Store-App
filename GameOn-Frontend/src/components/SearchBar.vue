<template>
<div >
<input
  style="border-radius: 10px; width:20vw; height: 2vw;"
  type="text"
  placeholder="Search..."
  v-model="searchText"
  @keydown.enter="onSearch"
/>
</div>
</template>


<script>

export default {
  name: "SearchBar",
  components: {

  },
  data() {
    return {
      searchText : ""
    };
  },
  methods: {
    async onSearch() {
      if (this.searchText === "") {
        await this.$router.push("/home")
      }
      else {
        await this.$router.push("/home/" + this.searchText)
      }
      window.location.reload();
    }
  },
};

</script>
