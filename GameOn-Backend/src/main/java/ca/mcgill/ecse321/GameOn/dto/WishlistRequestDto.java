package ca.mcgill.ecse321.GameOn.dto;

public class WishlistRequestDto {
    //@NotBlank(message = "The game name must not be empty")
    private String gameName;
    //@NotBlank(message = "The customer email must not be empty")
    private String customerEmail;

    public WishlistRequestDto() {
    }

    public WishlistRequestDto(String gameName,String customerEmail) {
        this.gameName = gameName;
        this.customerEmail = customerEmail;
    }


    public String getGameName() {
        return gameName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

}
