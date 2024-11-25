<template>
<div >
<input
  type="text"
  placeholder="Search..."
  v-model="searchText"
  @input="onSearch"
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
    onSearch() {

    }
  },
  async mounted() {

  },
};

</script>
