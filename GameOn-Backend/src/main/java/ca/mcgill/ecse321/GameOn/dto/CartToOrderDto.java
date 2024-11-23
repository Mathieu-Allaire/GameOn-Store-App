package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotNull;

public class CartToOrderDto {

    @NotNull(message = "Cart ID must not be null")
    private int cartId;

    // Default constructor
    public CartToOrderDto() {}

    // Constructor for initializing
    public CartToOrderDto(int cartId) {
        this.cartId = cartId;
    }

    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "CartToOrderDto{cartId=" + cartId + "}";
    }
}