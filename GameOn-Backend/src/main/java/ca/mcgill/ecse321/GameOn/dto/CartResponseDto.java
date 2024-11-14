package ca.mcgill.ecse321.GameOn.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;


public class CartResponseDto {

    private List<String> specificGameNames;
    private int id;
    
    @SuppressWarnings("unused")
    private CartResponseDto() {
    }
    
    public CartResponseDto(Cart aCart) {
        this.specificGameNames = new ArrayList<>();
        for (SpecificGame specificGame : aCart.getSpecificGames()) {
            specificGameNames.add(specificGame.getGame().getName());
        }
        this.id = aCart.getId();
    }
    
    
    public List<String> getSpecificGameNames() {
        return specificGameNames;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int aid) {
        this.id = aid;
    }

    @Override
    public String toString() {
        return "CartResponseDto{id=" + id + ", specificGameNames=" + specificGameNames + "}";
    }
    
}
