<template>
  <div class="category-view">
    <h1>Manage Categories</h1>
    <div class="category-container">
      <div class="create-category">
        <h2>Create Category</h2>
        <form @submit.prevent="createCategory">
          <input type="text" id="name" v-model="newCategory.name" placeholder="Enter category name" required />
          <button type="submit">Create</button>
        </form>
      </div>

      <div class="delete-category">
        <h2>Delete Category</h2>
        <div class="category-list">
          <h3>List Categories</h3>
          <ul>
            <li v-for="category in categories" :key="category.name">
              {{ category.name }}
              <button @click="deleteCategory(category.name)">Delete</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newCategory: {
        name: "",
      },
      categories: [
        // Example data; replace with real data from API
        { name: "Action" },
        { name: "Adventure" },
      ],
    };
  },
  methods: {
    createCategory() {
      if (this.newCategory.name) {
        // Add new category to the list
        this.categories.push({
          ...this.newCategory,
        });

        // Clear the form
        this.newCategory.name = "";
      }
    },
    deleteCategory(name) {
      // Find the index of the category to delete
      const index = this.categories.findIndex((category) => category.name === name);

      // Remove the category from the list
      this.categories.splice(index, 1);
    },
  },
};
</script>

<style scoped>
.manage-categories {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 60px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
}

h1 {
  font-size: 3em;
  margin-bottom: 40px;
}

.category-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 800px;
}

.create-category,
.delete-category {
  width: 100%;
  margin-bottom: 40px;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

form label {
  margin-top: 30px;
  font-size: 1.5em;
}

form input {
  margin-bottom: 30px;
  padding: 15px;
  font-size: 1.5em;
  width: 100%;
  max-width: 400px;
}

button {
  padding: 10px 30px;
  font-size: 1.5em;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.category-list ul {
  list-style: none;
  padding: 0;
  width: 100%;
}

.category-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  font-size: 1.5em;
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.category-list button {
  background-color: #dc3545;
  padding: 10px 20px;
  font-size: 1em;
  color: white;
  border: none;
  cursor: pointer;
}
form button {
  padding: 15px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}


.category-list button:hover {
  background-color: #a71d2a;
}
</style>