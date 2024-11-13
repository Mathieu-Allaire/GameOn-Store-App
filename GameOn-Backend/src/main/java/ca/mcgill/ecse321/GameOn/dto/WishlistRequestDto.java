package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import jakarta.validation.constraints.NotBlank;

public class WishlistRequestDto {
    @NotBlank(message = "The game name must not be empty")
    private String gameName;
    @NotBlank(message = "The customer email must not be empty")
    private String customerEmail;


    protected WishlistRequestDto() {
    }

    public WishlistRequestDto(String name,String email) {
        this.gameName = name;
        this.customerEmail = email;
    }


    public String getGameName() {
        return gameName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

}
