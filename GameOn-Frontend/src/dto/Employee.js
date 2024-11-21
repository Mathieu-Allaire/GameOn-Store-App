export class Employee {
  constructor(email, name) {
    this.email = email;
    this.name = name;
  }
  async createEmployee() {
    const path = "/employee";
    try {
      const response = await axios.post(path, this);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async findEmployeeByEmail(email) {
    const path = "/employee/" + email;
    try {
      const response = await axios.get(path);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async deactivateEmployeeByEmail(email) {
    const path = "/employee/deactivate/" + email;
    try {
      const response = await axios.put(path);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }
}
