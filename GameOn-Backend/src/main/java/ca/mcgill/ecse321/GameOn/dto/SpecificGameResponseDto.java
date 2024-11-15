package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.SpecificGame;

public class SpecificGameResponseDto {

    private String name;
    private int quantity;
    private String description;
    private int price;
    private String picture;
    private String Category;
    private int id;

    @SuppressWarnings("unused")
    private SpecificGameResponseDto() {
    }

    public SpecificGameResponseDto(SpecificGame aSpecificGame) {
        this.name = aSpecificGame.getGame().getName();
        this.quantity = aSpecificGame.getGame().getQuantity();
        this.description = aSpecificGame.getGame().getDescription();
        this.price = aSpecificGame.getGame().getPrice();
        this.picture = aSpecificGame.getGame().getPicture();
        this.Category = aSpecificGame.getGame().getCategory().getName();
        this.id = aSpecificGame.getId();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public String getCategory() {
        return Category;
    }

    public int getId() {
        return id;
    }

}
