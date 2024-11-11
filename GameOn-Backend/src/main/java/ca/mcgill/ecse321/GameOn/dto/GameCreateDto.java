package ca.mcgill.ecse321.GameOn.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


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
    @NotBlank(message = "The category must not be empty")
    private String category;

    public GameCreateDto() {
    }

    public GameCreateDto(String picture, String name, String description, int price, int quantity, String category) {
        this.picture = picture;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public String getCategory() {
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
