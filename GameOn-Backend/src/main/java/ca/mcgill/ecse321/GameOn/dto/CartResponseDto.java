package ca.mcgill.ecse321.GameOn.dto;



import java.util.List;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;


public class CartResponseDto {

    private List<SpecificGame> specificGames;

    private int id;
    
    @SuppressWarnings("unused")
    private CartResponseDto() {
    }
    
    public CartResponseDto(Cart aCart) {
        this.specificGames = aCart.getSpecificGames();
        
        this.id = aCart.getId();
    }
    
    
    public List<SpecificGame> getSpecificGames() {return this.specificGames;}
    public int getId() {
        return this.id;
    }

    public void setId(int aid) {
        this.id = aid;
    }
    
}
