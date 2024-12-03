<template>
    <div class="view-customers">
      <div v-if="customers.length==0"><h1>No Customers exist in the system</h1></div>
      <div v-else>
      <h1>Customer List</h1>
      <div class="columns">
        <div class="column">
          <h2>Customers</h2>
          <table>
            <thead>
              <tr>
                <th>Email</th>
                <th>Name</th>
                
                <!--<th>Actions</th>-->
              </tr>
            </thead>
            <tbody>
              <tr v-for="customer in customers" :key="customer.id">
                <td>{{ customer.email }}</td>
                <td>{{ customer.name }}</td>
                
                
                  <!--<td><button @click="redirectToOrders(customer.id)">View Orders</button></td>-->
                
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      </div>
    </div>
  </template>
  
  <script>
  import { Customer } from '@/dto/Customer';
  import { CustomerResponseDto } from '@/dto/CustomerResponseDto';
  export default {
    name: "CustomerView",
    data() {
      return {
        customers: [],
      };
    },
    async mounted() {
      try {
        // Call the API to get all customers
        const response = await Customer.getAllCustomers();

        // Check for errors in the response
        if (response.error) {
          console.error("Error fetching customers:", response.error);
          alert("Failed to fetch customers.");
        } else {
          // Map the response to CustomerResponseDto objects
          this.customers = response.data.map(customer => new CustomerResponseDto(customer));
          console.log("Customers fetched:", this.customers);
        }
      } catch (error) {
        // Handle unexpected errors
        console.error("Unexpected error:", error.message);
        alert("An unexpected error occurred while fetching customers.");
      }
    },



      redirectToOrders(customerId) {
        // Redirect to CustomerOrderView with the customer's ID as a query parameter
        this.$router.push({ name: "customer-orders", query: { customerId } });
      },
    
  };
  </script>
  
  <style scoped>
  body {
    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
  }

  .columns {
    display: flex;
    justify-content: center; /* Centers the columns horizontally */
    width: 100%; /* Ensures the columns take the full width */
    height: 100vh; /* Ensures the columns take the full viewport height */
    align-items: center; /* Vertically centers the content */
  }

  .column {
    flex: 10;
    width: 100%; /* Ensures the column spans the entire width */
    padding: 20px;
    border: none;
    background-color: #f9f9f9;
  }

  table {
    width: 100%; /* Ensures the table spans the entire column width */
    height: auto; /* Allows the table to adjust to its content height */
    border-collapse: collapse;
    font-size: 1.1em; /* Makes the text larger for better readability */
    margin: 0; /* Removes extra margins */
  }

  th, td {
    border: 1px solid #ddd;
    padding: 15px; /* Increases padding for a more spacious look */
    text-align: left;
    vertical-align: middle; /* Ensures content, including buttons, aligns properly */
  }

  th {
    background-color: #4CAF50;
    color: white;
    font-size: 1.2em; /* Increases header font size */
  }

  td {
    font-size: 1.1em; /* Slightly larger font for better readability */
  }

  button {
    background-color: #007BFF;
    color: white;
    padding: 10px 15px; /* Makes the buttons larger */
    border: none;
    cursor: pointer;
    border-radius: 5px;
    font-size: 1em; /* Ensures buttons have a readable font size */
    display: inline-block; /* Ensures buttons display properly within table cells */
  }

  button:hover {
    background-color: #0056b3;
  }

  img {
    display: block;
    max-width: 100%;
    height: auto;
    border-radius: 5px;
  }

  h1 {
    text-align: center;
    font-size: 2em;
    margin-bottom: 20px;
    color: #2c3e50;
  }

  h2 {
    text-align: center;
    color: #333;
    font-size: 1.5em;
    margin-bottom: 20px;
  }
</style>