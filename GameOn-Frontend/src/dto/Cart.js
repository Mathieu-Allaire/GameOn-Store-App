import axios from "axios";

export class Cart {
  constructor(customerId, aGameName) {
    this.customerId = customerId;
    this.aGameName = aGameName;
  }

  static async getCart(id) {
    const path = "/carts/" + id;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async addGameToCart() {
    const path = "/game-add";
    try {
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async removeGameFromCart() {
  
    const path = "/game-remove";
    try {
      const response = await axios.post(path,this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async removeAllGamesFromCart(id) {
    const path = "/remove-all";
    try {
      const response = await axios.post(path,this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async createOrder() {
    const path = "/createOrder";
    try{
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async getCartByEmail(email) {
    const path = "/cartEmail/" + email;
    try{
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}