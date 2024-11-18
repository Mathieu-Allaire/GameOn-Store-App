package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.OrderClass;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseDto {

    private int id;
    private Date purchaseDate;
    private List<String> specificGameNames;

    @SuppressWarnings("unused")
    private OrderResponseDto() {
    }

    public OrderResponseDto(OrderClass aOrderClass) {
        this.specificGameNames = new ArrayList<>();
        for (SpecificGame specificGame : aOrderClass.getOrderGames()) {
            specificGameNames.add(specificGame.getGame().getName());
        }
        this.id = aOrderClass.getId();
        this.purchaseDate = aOrderClass.getPurchaseDate();
    }

    public int getId() {
        return id;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
