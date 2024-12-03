export class CustomerResponseDto {
  constructor(response) {
    this.email = response.email;
    this.name = response.name;
    this.address = response.address; 
    this.cartId = response.cartId;
  }
}
