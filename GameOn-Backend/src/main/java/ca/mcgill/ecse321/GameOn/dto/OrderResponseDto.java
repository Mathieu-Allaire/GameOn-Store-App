package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.OrderClass;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderResponseDto {

    private int id;
    private Date purchaseDate;
    private List<String> specificGameNames;
    private List<Integer> specificGameIds;
    private HashMap<Game, Integer> gameHashMap;

    @SuppressWarnings("unused")
    private OrderResponseDto() {}

    public OrderResponseDto(OrderClass aOrderClass) {
        this.specificGameNames = new ArrayList<>();
        this.specificGameIds = new ArrayList<>();
        this.gameHashMap = new HashMap<>();
        for (SpecificGame specificGame : aOrderClass.getOrderGames()) {
            specificGameNames.add(specificGame.getGame().getName());
            specificGameIds.add(specificGame.getId());
            gameHashMap.put(specificGame.getGame(), gameHashMap.getOrDefault(specificGame.getGame(), 0) + 1);
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
    public List<String> getSpecificGameNames(){
        return specificGameNames;
    }
    public List<Integer> getSpecificGameIds() {return specificGameIds;}
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public HashMap<Game, Integer> getHashMap() {return gameHashMap;}
}
