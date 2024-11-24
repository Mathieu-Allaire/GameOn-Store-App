<template>
  <div class="employee-view">
    <h1>Manage Staff</h1>
    <div class="employee-container">
      <div class="create-employee">
        <h2>Create Employee</h2>
        <form @submit.prevent="createEmployee">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="newEmployee.email" placeholder="Enter employee email" required />
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="newEmployee.name" placeholder="Enter employee name" required />
          <button type="submit">Create</button>
        </form>
      </div>

    
      <div class="deactivate-employee">
        <h2>Deactivate Employee</h2>
        <div class="employee-list">
          <h3>List Employees</h3>
          <ul>
            <li v-for="employee in employees" :key="employee.id">
              {{ employee.name }} ({{ employee.email }})
              <button @click="deactivateEmployee(employee.id)">Deactivate</button>
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
      newEmployee: {
        email: "",
        name: "",
      },
      employees: [
        // Example data; replace with real data from API
        { id: 1, name: "John Doe", email: "john.doe@example.com" },
        { id: 2, name: "Jane Smith", email: "jane.smith@example.com" },
      ],
    };
  },
  methods: {
    createEmployee() {
      //Verify if email and name are not empty
      if (this.newEmployee.email && this.newEmployee.name) {
        // Add new employee to the list
        const newId = this.employees.length + 1;
        this.employees.push({
          id: newId,
          ...this.newEmployee,
        });

        // Clear the form
        this.newEmployee.email = "";
        this.newEmployee.name = "";
      }
    },
    deactivateEmployee(id) {
      // Remove employee from the list
      this.employees = this.employees.filter((employee) => employee.id !== id);
    },
  },
};
</script>



<style scoped>
.employee-view {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.employee-container {
  display: flex;
  justify-content: space-between;
}

.create-employee,
.deactivate-employee {
  width: 45%;
}

form {
  display: flex;
  flex-direction: column;
}

form label {
  margin-top: 10px;
}

form input {
  margin-bottom: 10px;
  padding: 5px;
  font-size: 14px;
}

button {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.employee-list ul {
  list-style: none;
  padding: 0;
}

.employee-list li {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.employee-list button {
  background-color: #dc3545;
}

.employee-list button:hover {
  background-color: #a71d2a;
}
</style>