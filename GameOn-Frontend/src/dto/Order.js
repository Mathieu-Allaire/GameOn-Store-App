import axios from "axios";

export class Order {
  static async getOrder(id) {
    const path = "/orders/" + id;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async createOrder() {
    const path = "/createOrder";
    try {
      const response = await axios.post(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }

  static async findAllCustomerOrders(email) {
    const path = "/allOrders/" + email;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error : error.message};
    }
  }
}
