export class SpecificGameResponseDto {
  constructor(response) {
    this.name = response.id.name;
    this.quantity = response.quantity;
    this.description = response.description;
    this.price = response.price;
    this.picture = response.picture;
    this.Category = response.Category;
    this.id = response.id;
  }
}
