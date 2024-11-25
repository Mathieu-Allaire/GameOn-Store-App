export class GameRequestResponseDto {
  constructor(response = {}) {
    this.requestType = response.requestType;
    this.requestedGame = response.requestedGame;
    this.requestedGameStatus = response.requestedGameStatus;
  }
}
