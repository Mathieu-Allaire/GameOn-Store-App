package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.Category;

public class CategoryResponseDto {
    private String name;

    protected CategoryResponseDto() {
    }

    public CategoryResponseDto(Category category) {
        this.name = category.getName();
    }

    public String getName() {
        return name;
    }
}
