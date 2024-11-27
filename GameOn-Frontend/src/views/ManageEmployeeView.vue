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
        alert(error.response.data);
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
  position: absolute !important;
  top: calc(50% + 30px) !important; /* Adjust offset based on the navbar height */
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f8f9fa; /* Light background for the view */
  border-radius: 10px; /* Rounded corners */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
  max-width: 1200px;
  margin: auto;
}

.employee-view h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
  font-size: 2.5em;
}

.employee-container {
  display: flex;
  justify-content: space-between;
  gap: 20px; /* Gap between columns */
  flex-wrap: wrap; /* Wrap for smaller screens */
}

.create-employee,
.deactivate-employee {
  width: 48%;
  background-color: white; /* Card-like section */
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow for cards */
}

.create-employee h2,
.deactivate-employee h2 {
  text-align: center;
  color: #007bff;
  font-size: 1.8em;
  margin-bottom: 15px;
}

form {
  display: flex;
  flex-direction: column;
}

form label {
  margin-top: 10px;
  color: #555;
  font-size: 14px;
  font-weight: bold;
}

form input {
  margin-bottom: 15px;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

form input:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

button {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

button:hover {
  background-color: #0056b3;
  transform: scale(1.02);
}

.employee-list {
  margin-top: 20px;
}

.employee-list h3 {
  text-align: center;
  color: #28a745;
  font-size: 1.5em;
  margin-bottom: 15px;
}

.employee-list ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.employee-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f8f9fa;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-bottom: 10px;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.employee-list li:hover {
  background-color: #e9ecef;
  transform: scale(1.01);
}

.employee-list button {
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.employee-list button:hover {
  background-color: #a71d2a;
}

/* Responsive Design */
@media (max-width: 768px) {
  .employee-container {
    flex-direction: column;
    gap: 20px;
  }

  .create-employee,
  .deactivate-employee {
    width: 100%;
  }
}

</style>