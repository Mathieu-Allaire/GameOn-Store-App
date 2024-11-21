class Game {
  constructor(picture, name, description, price, quantity, category) {
    this.picture = picture;
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
    this.category = category;
  }
  async createGame() {
    path = "/games";
    try {
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async deleteGame(name) {
    path = "/games/" + name;
    try {
      const response = await axios.delete(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async findAllGames() {
    path = "/games";
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async findGameByName(name) {
    path = "/games/" + name;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async findGameByCategory(category) {
    path = "/games/category/" + category;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async updateGamePrice(name, price) {
    path = "/games/updatePrice";
    try {
      const response = await axios.post(path, { name, price });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async updateGameQuantity(name, quantity) {
    path = "games/updateQuantity";
    try {
      const response = await axios.post(path, { name, quantity });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
