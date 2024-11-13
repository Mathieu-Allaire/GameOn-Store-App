package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.NotNull;

public class GameReqRequestDto {
    //@NotNull(message = "The request creator must not be null")
    private String aEmployee;
    //@NotNull(message = "The requested game must not be null")
    private String requestedGameName;
    //@NotNull(message = "The request type must not be null")
    private String requestType;

    public GameReqRequestDto(String employee, String gameName, String requestType) {
        this.aEmployee = employee;
        this.requestedGameName = gameName;
        this.requestType = requestType;
    }

    public String getaEmployee() {
        return aEmployee;
    }

    public String getrequestedGameName() {
        return requestedGameName;
    }

    public String getrequestType() {
        return requestType;
    }

}
