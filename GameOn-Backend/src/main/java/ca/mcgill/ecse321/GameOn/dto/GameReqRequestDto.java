package ca.mcgill.ecse321.GameOn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class GameReqRequestDto {
    @NotNull(message = "The request creator must not be null")
    @JsonProperty("employeeEmail")
    private String employeeEmail;

    @NotNull(message = "The requested game must not be null")
    @JsonProperty("requestedGameName")
    private String requestedGameName;

    @NotNull(message = "The request type must not be null")
    @JsonProperty("requestType")
    private String requestType;

    public GameReqRequestDto() {
    }

    public GameReqRequestDto(String employeeEmail, String requestedGameName, String requestType) {
        this.employeeEmail = employeeEmail;
        this.requestedGameName = requestedGameName;
        this.requestType = requestType;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getRequestedGameName() {
        return requestedGameName;
    }

    public String getRequestType() {
        return requestType;
    }
}