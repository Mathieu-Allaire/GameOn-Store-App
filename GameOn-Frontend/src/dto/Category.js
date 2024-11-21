class Category {
  constructor(name) {
    this.name = name;
  }

  async createCategory() {
    path = "/categories";
    try {
      const response = await axios.post(path, this);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async deleteCategory(name) {
    path = "/categories/" + name;
    try {
      const response = await axios.delete(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async findAllCategories() {
    path = "/categories";
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
  async findCategoryByName(name) {
    path = "/categories" + name;
    try {
      const response = await axios.get(path);
      return response.data;
    } catch (error) {
      return { error: error.message };
    }
  }
}
