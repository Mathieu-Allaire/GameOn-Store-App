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
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
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
import { Category } from "../dto/Category";

export default {
  data() {
    return {
      newCategory: {
        name: "",
      },
      categories: [],
      errorMessage: "",
    };
  },
  methods: {
    createCategory() {
      if (this.newCategory.name) {
        // Check if the category already exists
        const existingCategory = this.categories.find(category => category.name === this.newCategory.name);
        if (existingCategory) {
          this.errorMessage = "Category already exists.";
          return;
        }

        // Add new category to the list
        try {
          this.addCategory(this.newCategory.name);
          this.newCategory.name = "";
          this.errorMessage = ""; // Clear any previous error message
        } catch (error) {
          console.error(error);
          this.errorMessage = error.message || "Failed to create category. Please try again.";
        }
      }
    },
    async deleteCategory(name) {
      // Find the index of the category to delete
      const index = this.categories.findIndex((category) => category.name === name);

      // Remove the category from the list
      await Category.deleteCategory(name);
      this.categories.splice(index, 1);
    },
    addCategory(name) {
      const category = new Category(name); // DTO
      const response = category.createCategory(); // Send request to server
      this.categories.push({ name }); // Add to local list
    },
    async loadCategories() {
      try {
        const response = await Category.findAllCategories();
        if (response.error) {
          this.errorMessage = response.error;
        } else {
          response.forEach(category => {
            this.categories.push({ name: category.name });
          });
        }
      } catch (error) {
        console.error(error);
        this.errorMessage = error.message || "Failed to load categories. Please try again.";
      }
    },
  },
  async mounted() {
    // Create two categories by default
    await this.loadCategories();
  },
};
</script>

<style scoped>
.category-view {
  position: absolute !important;
  top: calc(50% + 30px) !important; /* Adjust offset based on the navbar height */
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Optional shadow */
  border-radius: 8px; /* Optional rounded corners */
  width: 80%; /* Optional: Adjust width for responsiveness */
  max-width: 600px;
}


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

.error {
  color: red;
  margin-top: -80px; /* Adjust margin to position the error message closer to the input */
  margin-bottom: 10px; /* Ensure there is space between the error message and the button */
}

.category-list button:hover {
  background-color: #a71d2a;
}
</style>