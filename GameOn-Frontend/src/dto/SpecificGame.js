export class SpecificGame {
  constructor(specificGameId, cartId) {
    this.cartId = cartId;
    this.specificGameId = specificGameId;
  }
  static async getGameById(id) {
    const path = "/specificGames/" + id;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
