package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
    @NotBlank(message = "The category name must not be empty")
    private String name;

    public CategoryRequestDto() {
    }

    public CategoryRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
