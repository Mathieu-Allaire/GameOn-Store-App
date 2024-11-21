export class CustomerResponseDto {
  constructor(response) {
    this.email = response.email;
    this.name = response.name;
  }
}
