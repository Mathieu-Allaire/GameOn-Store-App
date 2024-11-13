package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.model.Customer;

public class WishlistResponseDto {
    private String gameName;
    private Customer customerEmail;

    protected WishlistResponseDto() {
    }

    public WishlistResponseDto(WishlistLink wishlistLink) {

        this.gameName = wishlistLink.getWishlistGames().getName();
        this.customerEmail = wishlistLink.getCustomerWish();
    }

    public String getGameName() {
        return gameName;
    }

    public Customer getCustomerEmail() {
        return customerEmail;
    }
}
