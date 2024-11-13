package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotBlank;

public class WishlistRequestDto {
    @NotBlank(message = "The game name must not be empty")
    private String gameName;
    @NotBlank(message = "The customer email must not be empty")
    private String customerEmail;

    public String getGameName() {
        return gameName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

}
