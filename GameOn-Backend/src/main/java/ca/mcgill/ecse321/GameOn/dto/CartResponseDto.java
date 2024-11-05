package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Cart;

import java.sql.Date;

public class CartResponseDto {

    private int id;
    private Date dateAdded;

    @SuppressWarnings("unused")
    private CartResponseDto() {
    }

    public CartResponseDto(Cart model) {
        this.id = model.getId();
        this.dateAdded = model.getDateAdded();
    }

    public int getId() {
        return id;
    }
    public Date getDateAdded() {
        return dateAdded;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
