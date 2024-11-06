package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.Game;

public class GameResponseDTO {
    private String picture;
    private String name;
    private String description;
    private int price;
    private int quantity;

    public GameResponseDTO(Game game) {
        this.picture = game.getPicture();
        this.name = game.getName();
        this.description = game.getDescription();
        this.price = game.getPrice();
        this.quantity = game.getQuantity();
    }

    public String getPicture() {
        return picture;
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
