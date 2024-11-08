package ca.mcgill.ecse321.GameOn.dto;

import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;


public class WishlistLinkDto {
    private String gameName;
    private String customerEmail;

    @Autowired
    private PersonRepository personRepository;

    @SuppressWarnings("unused")
    private WishlistLinkDto() {
    }

    public WishlistLinkDto(WishlistLink wishlistLink) {
        this.gameName = wishlistLink.getKey().getWishlistGames().getName();
        Long aCustomer = wishlistLink.getKey().getCustomer().getId();
        this.customerEmail = personRepository.findPersonEmailByRoleId(aCustomer.intValue());
    }

    public String getGameName() {
        return gameName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

}
