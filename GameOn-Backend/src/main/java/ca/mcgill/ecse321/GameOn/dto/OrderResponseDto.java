package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Order;
import java.sql.Date;

public class OrderResponseDto {

    private int id;
    private Date purchaseDate;

    @SuppressWarnings("unused")
    private OrderResponseDto() {
    }

    public OrderResponseDto(Order model) {
        this.id = model.getId();
        this.purchaseDate = model.getPurchaseDate();
    }

    public int getId() {
        return id;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
