package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.GameRequest;


public class GameRequestResponseDto {
    private String requestType;
    private String requestedGame;
    private String requestedGameStatus;
    private String requestedGameDescription;
    private int requestedGameId;


    protected GameRequestResponseDto() {
    }

    public GameRequestResponseDto(GameRequest gameRequest) {
        this.requestType = gameRequest.getRequestType().toString();
        this.requestedGame = gameRequest.getRequestedGame().getName();
        this.requestedGameStatus = gameRequest.getRequestedGame().getGameStatus().toString();
        this.requestedGameId = gameRequest.getId();
        this.requestedGameDescription = gameRequest.getRequestedGame().getDescription();
    }

    public String getRequestType() {
        return requestType;
    }

    public String getRequestedGameDescription() {
        return requestedGameDescription;
    }

    public int getRequestedGameId() {
        return requestedGameId;
    }

    public String getRequestedGame() {
        return requestedGame;
    }

    public String getRequestedGameStatus() {
        return requestedGameStatus;
    }

}
