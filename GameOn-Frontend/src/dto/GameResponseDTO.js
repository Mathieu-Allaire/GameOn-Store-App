export class GameResponseDTO {
  constructor(response) {
    this.picture = response.picture;
    this.name = response.name;
    this.description = response.description;
    this.price = response.price;
    this.quantity = response.quantity;
    this.category = response.category;
  }
}
