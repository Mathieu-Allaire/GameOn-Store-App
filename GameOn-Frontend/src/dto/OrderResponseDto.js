export class OrderResponseDto {
  constructor(response) {
    this.id = response.id;
    this.purchaseDate = response.purchaseDate;
    this.specificGameNames = response.specificGameNames;
    this.items = Object.entries(response.gameHashMap).map(([game, quantity]) => ({
      specificGame: new GameResponseDTO(game), // Use GameResponseDTO for game details
      quantity: quantity,
    }));
  }
}
