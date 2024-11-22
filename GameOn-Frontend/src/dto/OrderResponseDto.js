export class OrderResponseDto {
  constructor(response) {
    this.id = response.id;
    this.purchaseDate = response.purchaseDate;
    this.specificGameNames = response.specificGameNames;
  }
}
