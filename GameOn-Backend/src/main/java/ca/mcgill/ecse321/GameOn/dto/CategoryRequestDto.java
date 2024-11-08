package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
     @NotBlank(message = "The e must not be empty")
    private String name;

    public String getName() {
        return name;
    }
}
