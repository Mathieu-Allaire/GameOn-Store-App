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
    private List<Integer> specificGameIds;
    private double price;

    @SuppressWarnings("unused")
    private OrderResponseDto() {}

    public OrderResponseDto(OrderClass aOrderClass) {
        this.specificGameNames = new ArrayList<>();
        this.specificGameIds = new ArrayList<>();
        for (SpecificGame specificGame : aOrderClass.getOrderGames()) {
            specificGameNames.add(specificGame.getGame().getName());
            specificGameIds.add(specificGame.getId());
        }
        this.id = aOrderClass.getId();
        this.purchaseDate = aOrderClass.getPurchaseDate();
        this.price = aOrderClass.getPrice();
    }

    public int getId() {
        return id;
    }

    public double getPrice() {return this.price;}
    
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public List<String> getSpecificGameNames(){
        return specificGameNames;
    }
    public List<Integer> getSpecificGameIds() {return specificGameIds;}
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
