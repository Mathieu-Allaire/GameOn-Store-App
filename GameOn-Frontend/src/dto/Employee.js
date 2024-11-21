class Employee {
  constructor(email, name) {
    this.email = email;
    this.name = name;
  }
  async createEmployee() {
    path = "/employee";
    try {
      const response = await axios.post(path, this);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }
  async findEmployeeByEmail(email) {
    path = "/employee/" + email;
    try {
      const response = await axios.get(path);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }
  async deactivateEmployeeByEmail(email) {
    path = "/employee/deactivate/" + email;
    try {
      const response = await axios.put(path);
      return response;
    } catch (error) {
      return { error: error.message };
    }
  }
}
