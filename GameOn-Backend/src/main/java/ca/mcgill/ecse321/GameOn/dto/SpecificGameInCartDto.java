package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotNull;

public class SpecificGameInCartDto {
    @NotNull(message = "Specific Game ID must not be null")
    private int specificGameId;
    @NotNull(message = "Cart ID must not be null")
    private int cartId;
    
    public SpecificGameInCartDto(int specifcGameId, int cartId) {
        this.specificGameId = specifcGameId;
        this.cartId = cartId;
    } 
    // Getter methods
    public int getSpecificGameId() {
        return specificGameId;
    }

    public int getCartId() {
        return cartId;
    }
}
