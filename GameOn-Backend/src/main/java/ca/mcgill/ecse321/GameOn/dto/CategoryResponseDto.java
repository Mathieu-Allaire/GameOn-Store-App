package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.Category;

public class CategoryResponseDto {
    private String name;

    public CategoryResponseDto(Category category) {
        this.name = category.getName();
    }

    @SuppressWarnings("unused")
    private CategoryResponseDto() {
    }

    public String getName() {
        return name;
    }
    
}
