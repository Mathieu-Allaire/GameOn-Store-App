class CustomerRequestDto {
  constructor(
    email,
    name,
    password,
    cardNum,
    cardExpiracyDate,
    billingAddress,
  ) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.cardNum = cardNum;
    this.aCardExpiracyDate = cardExpiracyDate;
    this.billingAddress = billingAddress;
  }
}
