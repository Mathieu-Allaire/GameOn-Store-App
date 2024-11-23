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
  static async removeGameFromCart(sgId, cId) {
    //specific game id, cart id
    const path = "/game-remove/" + sgId + cId;
    try {
      const response = await axios.post(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async removeAllGamesFromCart(id) {
    const path = "/remove-all/" + id;
    try {
      const response = await axios.post(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
