import axios from "axios";

export class Customer {
  constructor(
    id,
    email,
    name,
    password,
    cardNum,
    cardExpiracyDate,
    billingAddress,
  ) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.password = password;
    this.cardNum = cardNum;
    this.aCardExpiracyDate = cardExpiracyDate;
    this.billingAddress = billingAddress;
  }

  async createCustomer() {
    const path = "/customer";
    try {
      const response = await axios.post(path, this);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async findCustomerByEmail(email) {
    const path = "/customer/" + email;
    try {
      const response = await axios.get(path);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }

  static async getAllCustomers() {
    const path = "/allCustomers";
    try {
      const response = await axios.get(path);
      return response;
    } catch (error) {
      return {error : error.message};
    }
  }
}
