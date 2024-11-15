package ca.mcgill.ecse321.GameOn.dto;


import jakarta.validation.constraints.NotNull;

public class AddToCartRequestDto {
    @NotNull(message = "Customer ID must not be null")
    private Integer customerId;

    @NotNull(message = "Game name must not be null")
    private String aGameName;


    public Integer getCustomerId() {
        return customerId;
    }

    public String getGameName() {
        return aGameName;
    }

    public void setGameName(String gameName) {
        this.aGameName = gameName;
    }

    public void setCustomerId(int cId) {
        this.customerId = cId;
    }

}
