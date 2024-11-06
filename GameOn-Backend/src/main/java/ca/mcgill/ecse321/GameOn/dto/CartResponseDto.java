package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Cart;

import java.sql.Date;

public class CartResponseDto {

    private int id;

    @SuppressWarnings("unused")
    private CartResponseDto() {
    }

    public CartResponseDto(Cart model) {
        this.id = model.getId();
    }

    public int getId() {
        return id;
    }
   

    public void setId(int id) {
        this.id = id;
    }
  
}
