package ca.mcgill.ecse321.GameOn.dto;
import jakarta.validation.constraints.NotBlank;
import java.util.*;
import ca.mcgill.ecse321.GameOn.model.Review;

public class GameCreateDto {
    @NotBlank(message = "The picture must not be empty")
    private String picture;
    @NotBlank(message = "The name must not be empty")
    private String name;
    @NotBlank(message = "The description must not be empty")
    private String description;
    // Validation done in the service layer
    private int price;
    // Validation done in the service layer
    private int quantity;
    @NotBlank(message = "The category must not be empty")
    private String category;
    private List<Review> reviews;

    public GameCreateDto() {
    }

    public GameCreateDto(String picture, String name, String description, int price, int quantity, String category, List<Review> reviews) {
        this.picture = picture;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.reviews = new ArrayList<Review>();
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
    public List<Review> getReviews() {return reviews;}
}
