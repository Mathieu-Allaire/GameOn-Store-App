package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotNull;

public class RemoveFromCartRequestDto {
    @NotNull(message = "Customer ID must not be null")
    private Integer cartId;
    @NotNull(message = "Game ID must not be null")
    private int specificGameId;

    public Integer getCartId() {
        return cartId;
    }

    public Integer getSpecificGameId(){
        return specificGameId ;
    }

    public void setCartId(int cId) {
        this.cartId = cId;
    }

    public void setSpecificGameId(int sgId) {
        this.specificGameId = sgId;
    }
}
