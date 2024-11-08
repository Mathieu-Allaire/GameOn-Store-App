package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.RequestType;
import ca.mcgill.ecse321.GameOn.model.GameRequest;

public class GameRequestResponseDto {
    private Employee aRequestCreator;
    private Game aRequestedGame;
    private RequestType aRequestType;
    private GameRequest gameRequest;
    
    @SuppressWarnings("unused")
    private GameRequestResponseDto() {
    }

    public GameRequestResponseDto(GameRequest gameRequest) {
        this.aRequestCreator = gameRequest.getRequestCreator();
        this.aRequestedGame = gameRequest.getRequestedGame();
        this.aRequestType = gameRequest.getRequestType();
        this.gameRequest = gameRequest;
    }

    public GameRequest getGameRequest() {
        return gameRequest;
    }

    public Employee getARequestCreator() {
        return aRequestCreator;
    }

    public Employee setARequestCreator(Employee aRequestCreator) {
        return this.aRequestCreator = aRequestCreator;
    }

    public Game setARequestedGame(Game aRequestedGame) {
        return this.aRequestedGame = aRequestedGame;
    }

    public RequestType setARequestType(RequestType aRequestType) {
        return this.aRequestType = aRequestType;
    }

    public Game getARequestedGame() {
        return aRequestedGame;
    }

    public RequestType getARequestType() {
        return aRequestType;
    }
    
}
