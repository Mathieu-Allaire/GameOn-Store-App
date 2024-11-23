package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotNull;

public class RemoveAllGamesFromCartRequestDto {
    @NotNull(message = "Customer ID must not be null")
    private Integer cartId;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(int cId) {
        this.cartId = cId;
    }
}
