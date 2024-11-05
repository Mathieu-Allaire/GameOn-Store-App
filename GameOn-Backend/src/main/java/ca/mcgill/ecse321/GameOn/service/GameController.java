package ca.mcgill.ecse321.GameOn.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.GameOn.model.Category;


@Service
public class GameController {
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Method to create new category
     * @param aName
     * @throws IllegalArgumentException if name is invalid
     */
    @Transactional
    public Category createCategory(String aName){
        if (aName == null || aName.trim().length() == 0) {
            throw new IllegalArgumentException("Name is invalid");
        }
        Category category = new Category(aName);
        category.setName(aName);
        categoryRepository.save(category);
        return category;
    }

    /**
     * Method to delete category
     * @param aName
     * @throws IllegalArgumentException if name is invalid
     */
    @Transactional
    public void deleteCategory(String aName){
        if (aName == null || aName.trim().length() == 0) {
            throw new IllegalArgumentException("Name is invalid");
        }

        Category category = categoryRepository.findCategoryByName(aName);

        if (category == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
        categoryRepository.delete(category);
    }

    /**
     * Method to get all categories
     * @param List<Category>
     * @throws IllegalArgumentException if name is invalid
     */
    @Transactional
    public Iterable<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
