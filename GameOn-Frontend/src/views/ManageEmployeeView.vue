<template>
  <div class="employee-view">
    <h1>Manage Staff</h1>
    <div class="employee-container">
      <div class="create-employee">
        <h2>Create Employee</h2>
        <form @submit.prevent="createEmployee">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="email" placeholder="Enter employee email" required />
          <label for="name">Name:</label>
          <input type="text" id="name" v-model="name" placeholder="Enter employee name" required />
          <button type="submit">Create</button>
        </form>
      </div>

    
      <div class="deactivate-employee">
        <h2>Deactivate Employee</h2>
        <div class="employee-list">
          <h3>List Employees</h3>
          <ul>
            <li v-for="employee in employees" :key="employee.email">
              {{ employee.name }} ({{ employee.email }})
              <button @click="deactivateEmployee(employee.email)">Deactivate</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  // NOTE: it's baseURL, not baseUrl
  baseURL: "http://localhost:8080"
});

export default {
  
  data() {
    return {
      employees: [],
      email: "",
      name: "",
    }
  },

  async created() {
        // Retrieve employees
        try {
          const response = await axiosClient.get("/employee");
          this.employees = response.data;
        } catch (error) {
          console.error(error);
        }
    },

  methods: {
    async createEmployee() {
      //Create employee 
      const newEmployee = {
        email: this.email,
        name: this.name,
      };
      try {
        const response = await axiosClient.post("/employee", newEmployee);
        console.log("Employee created:", this.name);
        // Add employee to the list
        this.employees.push(newEmployee);
        this.clearInput();
      } catch (error) {
        console.error(error);
      }
    },
    async deactivateEmployee(email) {
      // Deactivate employee
      const response = await axiosClient.put(`/employee/deactivate/${email}`);
      this.employees = this.employees.filter((employee) => employee.email !== email);
    },
    clearInput() {
      this.email = "";
      this.name = "";
    },
    async loadEmployees() {
      // Retrieve employees
      try {
        const response = await axiosClient.get("/employee");
        this.employees = response.data;

        response.forEach((employee) => {
          this.employees.push(
            {
              email: employee.email,
              name: employee.name,
            }
          );
        });
      } catch (error) {
        console.error(error);
      }
    },

    async mounted() {
      await this.loadEmployees();
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