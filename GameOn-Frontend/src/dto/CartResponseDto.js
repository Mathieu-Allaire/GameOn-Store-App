export class CartResponseDto {
  constructor(response) {
    this.customer = response.customer;
    this.id = response.id;
    this.items = Object.entries(response.gameHashMap).map(([game, quantity]) => ({
      specificGame: new GameResponseDTO(game), // Parse the game details using GameResponseDTO
      quantity: quantity,
    }));
  }
}
