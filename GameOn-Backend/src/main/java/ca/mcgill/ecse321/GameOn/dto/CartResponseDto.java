package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Cart;

import java.sql.Date;
import ca.mcgill.ecse321.GameOn.model.Customer;

public class CartResponseDto {

    private Customer customer;
    private Date dateAdded;

    @SuppressWarnings("unused")
    private CartResponseDto() {
    }

    public CartResponseDto(Cart model) {
        this.customer = model.getCustomer();
        this.dateAdded = model.getDateAdded();
    }

    public Customer getCustomer() {
        return customer;
    }
    public Date getDateAdded() {
        return dateAdded;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
