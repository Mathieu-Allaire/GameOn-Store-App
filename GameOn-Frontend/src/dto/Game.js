import axios from "axios";

export class Game {
  constructor(picture, name, description, price, quantity, category) {
    this.picture = picture;
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
    this.category = category;
  }
  async createGame() {
    const path = "/games";
    try {
      const response = await axios.post(path, this);
      return response.data; // Return successful response
    } catch (error) {
      throw error;
    }
  }
  static async updateGame(name) {
    const path = "/gamesStatus/" + name;
    try {
      const response = await axios.post(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async deleteGame(name) {
    const path = "/games/" + name;
    try {
      const response = await axios.post(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async findAllGames() {
    const path = "/games";
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async findGameByName(name) {
    const path = "/games/" + name;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async findGameByCategory(category) {
    const path = "/games/category/" + category;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async updateGamePrice(name, price) {
    const path = "/games/updatePrice";
    try {
      const response = await axios.post(path, null, {
        params: { name, price },
      });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async updateGameQuantity(name, quantity) {
    const path = "games/updateQuantity";

    try {
      const response = await axios.post(path, null, {
        params: { name, quantity },
      });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }

  // Add review to a game
  static async addReview(name, review) {
    const path = "http://localhost:8087/home/game/addReview";
    try {
      const response = await axios.post(path, null, {
        params: { name, review },
      });
      return response.data; // Return the updated game response
    } catch (error) {
      return { error: error.message };
    }
  }

  // Get reviews of a game
  static async getReviews(name) {
    const path = "/game/" + name + "/reviews";
    try {
      const response = await axios.get(path);
      console.log("Reviews response:", response);
      return response.data; // Return the list of reviews
    } catch (error) {
      return { error: error.message };
    }
  }
}
