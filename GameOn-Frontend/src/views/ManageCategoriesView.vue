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
    await this.loadCategories();
  },
};
</script>

<style scoped>
.category-view {
  position: absolute !important;
  top: calc(50% + 30px) !important; 
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); 
  border-radius: 8px; 
  width: 80%; 
  max-width: 600px;
  color: black;
}


h1 {
  font-size: 2.5em; 
  margin-bottom: 20px;
  text-align: center; 
  color: black;
}

h2 {
  font-size: 1.5em; 
  margin-bottom: 10px;
  color: peru
}

.category-container {
  display: flex;
  flex-direction: column;
  gap: 20px; /* Replaces manual margin for consistent spacing */
  width: 100%;
}


.delete-category {
  width: 100%;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Light shadow for better separation */
  border-radius: 8px;
  background-color: #fff;
}

.create-category {
  display: flex;
  flex-direction: column; /* Stack the items vertically */
  justify-content: center; /* Center items vertically */
  align-items: center; /* Center items horizontally */
  width: 100%; /* Ensures the container spans the full width */
  margin-bottom: 40px; /* Maintain space between sections */
  text-align: center; /* Center text inside */
}

.create-category h2 {
  margin-bottom: 20px; /* Add spacing between the heading and the input field */
}

form {
  display: flex;
  flex-direction: column; /* Keep the input and button stacked */
  align-items: center; /* Center them horizontally */
  gap: 10px; /* Add consistent spacing between input and button */
  width: 100%;
  max-width: 400px; /* Optional: Limit form width for better layout */
}


input {
  padding: 10px;
  font-size: 1.2em;
  width: 100%;
  max-width: 400px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 12px 20px;
  font-size: 1em;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease; /* Smooth hover effect */
}

button:hover {
  background-color: #0056b3;
}

.category-list ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.category-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  font-size: 1em;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
}

.category-list button {
  background-color: #dc3545;
  color: white;
  font-size: 0.9em;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.category-list button:hover {
  background-color: #a71d2a;
}

.error {
  color: red;
  font-size: 0.9em;
  margin-top: 5px;
}

/* Responsive Design */
@media (max-width: 768px) {
  .category-view {
    padding: 10px;
  }

  h1 {
    font-size: 2em;
  }

  button {
    font-size: 0.9em;
    padding: 10px 15px;
  }

  input {
    font-size: 1em;
  }

  .category-list li {
    font-size: 0.9em;
  }
}
</style>