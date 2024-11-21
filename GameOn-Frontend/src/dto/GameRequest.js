class GameRequest {
  constructor(employeeEmail, requestedGameName, requestType) {
    this.employeeEmail = employeeEmail;
    this.requestedGameName = requestedGameName;
    this.requestType = requestType;
  }

  async createGameRequest() {
    path = "/games/request";
    try {
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async approveGameRequest(gameRequestId) {
    path = "games/request/approve";
    try {
      const response = await axios.post(path, null, {
        params: { gameRequestId },
      });
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
