import axios from "axios";

export class Category {
  constructor(name) {
    this.name = name;
  }

  async createCategory() {
    const path = "/categories";
    try {
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async deleteCategory(name) {
    const path = "/categories/" + name;
    try {
      const response = await axios.delete(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async findAllCategories() {
    const path = "/categories";
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  static async findCategoryByName(name) {
    const path = "/categories" + name;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
