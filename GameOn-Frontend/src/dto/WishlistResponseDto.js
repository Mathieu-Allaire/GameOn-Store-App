export class WishlistResponseDto {
  constructor(response) {
    this.gameName = response.gameName;
    this.gameQuantity = response.gameQuantity;
    this.gamePicture = response.gamePicture;
    this.gamePrice = response.gamePrice;
  }
}
