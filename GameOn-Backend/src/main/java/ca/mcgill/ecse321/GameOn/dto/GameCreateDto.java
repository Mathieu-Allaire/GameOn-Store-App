package ca.mcgill.ecse321.GameOn.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ca.mcgill.ecse321.GameOn.model.Category;

public class GameCreateDto {
    @NotBlank(message = "The picture must not be empty")
    private String picture;
    @NotBlank(message = "The name must not be empty")
    private String name;
    @NotBlank(message = "The description must not be empty")
    private String description;
    @Min(value = 0, message = "The price must be positive")
    private int price;
    @Min(value = 0, message = "The price must be positive")
    private int quantity;
    @NotNull(message = "The category must not be empty")
    private Category category;

    public String getPicture() {
        return picture;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
