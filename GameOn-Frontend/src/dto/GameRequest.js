import axios from "axios";

export class GameRequest {
  constructor(employeeEmail, requestedGameName, requestType) {
    this.employeeEmail = employeeEmail;
    this.requestedGameName = requestedGameName;
    this.requestType = requestType;
  }

  async createGameRequest() {
    const path = "/games/request";
    try {
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async approveGameRequest(gameRequestId) {
    const path = "games/request/approve";
    try {
      const response = await axios.post(path, null, { params: {gameRequestId}});
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }

  static async findAllGameRequests() {
    const path = "/games/gameRequests";
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
