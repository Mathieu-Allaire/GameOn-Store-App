<template>
  <div class="View my orders">
    <h1>List Of All Orders</h1>
    <div class="columns">
      <div class="column">
        <h2>Orders</h2>
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Purchase Date</th>
              <th>Price</th>
              <th>View Details</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in orders" :key="order.id">
              <td>{{ order.id }}</td>
              <td>{{ order.date }}</td>
              <td>{{ order.price }}</td>
              <td>
                <button @click="toggleOrderDetails(order)">
                  {{ selectedOrder && selectedOrder.id === order.id ? "Hide Info" : "View" }}
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div v-if="selectedOrder" class="column">
        <h2>Order Details</h2>
        <table>
          <thead>
            <tr>
              <th>Game Image</th>
              <th>Game Name</th>
              <th>Quantity</th>
              <th>Unit Price</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in selectedOrder.items" :key="item.specificGame.id">
              <td>
                <img :src="item.specificGame.image" alt="Game Image" width="100" />
              </td>
              <td>{{ item.specificGame.name }}</td>
              <td>{{ item.quantity }}</td>
              <td>{{ item.specificGame.price }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderView",
  data() {
    return {
      orders: [
        // Example data for orders
        {
          id: 1,
          date: "2024-11-21",
          price: "$120",
          items: [
            {
              specificGame: {
                id: 101,
                image: "https://example.com/game1.jpg",
                name: "Game 1",
                price: "$20",
              },
              quantity: 2,
            },
            {
              specificGame: {
                id: 102,
                image: "https://example.com/game2.jpg",
                name: "Game 2",
                price: "$40",
              },
              quantity: 1,
            },
          ],
        },
        {
          id: 2,
          date: "2024-11-20",
          price: "$80",
          items: [
            {
              specificGame: {
                id: 103,
                image: "https://example.com/game3.jpg",
                name: "Game 3",
                price: "$30",
              },
              quantity: 2,
            },
          ],
        },
      ],
      selectedOrder: null, // Tracks the currently selected order
    };
  },
  methods: {
    toggleOrderDetails(order) {
      if (this.selectedOrder && this.selectedOrder.id === order.id) {
        this.selectedOrder = null; // Hide details if the same order is clicked again
      } else {
        this.selectedOrder = order; // Show details for the selected order
      }
    },
  },
};
</script>

<style scoped>
.columns {
  display: flex;
  gap: 20px;
  justify-content: center; /* Centers the columns horizontally */
}

.column {
  flex: 1;
  max-width: 80%; /* Limits the column width to make it more visually appealing */
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 10px;
  background-color: #f9f9f9;
  margin: 0 auto; /* Centers the column within its container */
}

table {
  width: 100%;
  max-width: 95%; /* Limits the maximum width of the table */
  margin: 20px auto; /* Centers the table within its column */
  border-collapse: collapse;
  font-size: 1.1em; /* Makes the text larger for better readability */
}

th, td {
  border: 1px solid #ddd;
  padding: 15px; /* Increases padding for a more spacious look */
  text-align: left;
}

th {
  background-color: #4CAF50;
  color: white;
  font-size: 1.2em; /* Increases header font size */
}

button {
  background-color: #007BFF;
  color: white;
  padding: 10px 15px; /* Makes the buttons larger */
  border: none;
  cursor: pointer;
  border-radius: 5px;
  font-size: 1em; /* Ensures buttons have a readable font size */
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

</style>
