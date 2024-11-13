package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.WishlistLink;


public class WishlistResponseDto {
    private String gameName;
    private int gameQuantity;
    private String gamePicture;
    private int gamePrice;

    protected WishlistResponseDto() {
    }

    public WishlistResponseDto(WishlistLink wishlistLink) {
        this.gameName = wishlistLink.getKey().getWishlistGames().getName();
        this.gameQuantity = wishlistLink.getKey().getWishlistGames().getQuantity();
        this.gamePicture = wishlistLink.getKey().getWishlistGames().getPicture();
        this.gamePrice = wishlistLink.getKey().getWishlistGames().getPrice();
    }

    public String getGameName() {
        return gameName;
    }

    public int getGameQuantity() {
        return gameQuantity;
    }

    public String getGamePicture() {
        return gamePicture;
    }

    public int getGamePrice() {
        return gamePrice;
    }
}
