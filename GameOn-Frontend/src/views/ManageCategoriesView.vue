<template>
    <div class="manage-categories">
      <h1>Category Management System</h1>
      <div class="columns">
        <div class="column">
          <h2>Categories</h2>
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="category in categories" :key="category.id">
                <td>{{ category.name }}</td>
                <td><button class="remove-button" @click="removeCategory(category.id)">Remove</button></td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="column">
          <h2>Create Category</h2>
          <form @submit.prevent="createCategory">
            <label for="name">Name</label>
            <input id="name" v-model="newCategory.name" type="text" required>
            <button type="submit">Create Category</button>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const categories = ref([
    { id: 1, name: 'Category 1' },
    { id: 2, name: 'Category 2' },
    { id: 3, name: 'Category 3' },
  ]);
  
  const newCategory = ref({
    name: ''
  });
  
  const createCategory = () => {
    const category = { ...newCategory.value, id: Date.now() };
    categories.value.push(category);
    newCategory.value = {
      name: ''
    };
  };
  
  const removeCategory = (id) => {
    const categoryIndex = categories.value.findIndex(category => category.id === id);
    if (categoryIndex !== -1) {
      categories.value.splice(categoryIndex, 1);
    }
  };
  </script>
  
  <style scoped>
  .manage-categories {
    padding: 140px;
  }
  
  h1 {
    color: #2f3c53;
    font-size: 2em; /* Increase font size */
  }
  
  h2 {
    color: #ff0000;
    font-size: 1.5em; /* Increase font size */
  }

  .columns {
    display: flex;
    gap: 40px;
  }
  
  .column {
    flex: 1;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 10px;
    background-color: #645656;
    min-width: 300px; /* Ensure columns have a minimum width */
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  
  th, td {
    border: 1px solid #ccc;
    padding: 10px;
    text-align: left;
  }
  
  th {
    background-color:#645656;
    color: white;
    text-align: left;
    font-size: 1.5em; /* Increase font size */
    padding: 10px 30px; /* Increase padding */
  }
  
  button {
    padding: 10px 20px; /* Increase padding */
    font-size: 1em; /* Increase font size */
    background-color: #4CAF50;
    color: white;
    border: none;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #45a049;
  }
  
  .remove-button {
    background-color: #f44336; /* Red background */
  }
  
  .remove-button:hover {
    background-color: #d32f2f; /* Darker red on hover */
  }
  
  form {
    display: flex;
    flex-direction: column;
    gap: 1em;
    margin-top: 20px;
  }
  
  label {
    font-weight: bold;
  }
  
  input {
    padding: 0.5em;
    border-radius: 0.5em;
    border: 1px solid #ccc;
    font-size: 1em;
  }
  </style>