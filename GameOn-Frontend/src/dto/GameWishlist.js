import axios from "axios";

export class GameWishlist {
  constructor(gameName, customerEmail) {
    this.gameName = gameName;
    this.customerEmail = customerEmail;
  }

  async addGameToWishlist(gameName) {
    const path = "/wishlist-add";
    try {
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async removeGameFromWishlist() {
    const path = "/wishlist-remove";
    try {
      const response = await axios.delete(path, {
        data: {
          gameName: this.gameName,
          customerEmail: this.customerEmail,
        },
      });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async getAllGamesFromWishlist(customerEmail) {
    const path = "/wishlist-get-all/" + customerEmail;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
