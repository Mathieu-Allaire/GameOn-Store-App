export class CartResponseDto {
  constructor(response) {
    this.specificGameNames = [] = response.specificGameNames;
    this.id = response.id;
  }
}
