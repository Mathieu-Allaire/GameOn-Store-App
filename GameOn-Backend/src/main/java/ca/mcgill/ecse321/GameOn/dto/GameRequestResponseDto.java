package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.GameRequest;
import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.model.Game;


public class GameRequestResponseDto {
    private String requestType;
    private Employee requestCreator;
    private Game requestedGame;

    protected GameRequestResponseDto() {
    }

    public GameRequestResponseDto(GameRequest gameRequest) {
        this.requestType = gameRequest.getRequestType().toString();
        this.requestCreator = gameRequest.getRequestCreator();
        this.requestedGame = gameRequest.getRequestedGame();
    }

    public String getRequestType() {
        return requestType;
    }

    public Employee getRequestCreator() {
        return requestCreator;
    }

    public Game getRequestedGame() {
        return requestedGame;
    }

}
