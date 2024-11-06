package ca.mcgill.ecse321.GameOn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.dto.GameCreateDto;
import ca.mcgill.ecse321.GameOn.service.GameService;
import ca.mcgill.ecse321.GameOn.model.Game;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

/**
 * This class represents the Game Controller, which handles the HTTP
 * requests related to games.
 * It provides endpoints for creating, updating, deleting, and retrieving
 * games.
 *
 * @author Neeshal Imrit
 */

@RestController
public class GameController {
    @Autowired
    private GameService gameService;


    /**
     * Create a game
     *
     * @param gameCreateDto the game create DTO
     * @return the created game response DTO
     * @author Neeshal Imrit
     */
    @PostMapping("/games")
    public ResponseEntity<?> createGame(@Valid @RequestBody GameCreateDto gameCreateDto) {
        try {
            Game game = gameService.createGame(
                gameCreateDto.getPicture(),
                gameCreateDto.getName(),
                gameCreateDto.getDescription(),
                gameCreateDto.getPrice(),
                gameCreateDto.getQuantity(),
                gameCreateDto.getCategory()
            );
            GameResponseDTO response = new GameResponseDTO(game);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Delete a game
     *
     * @param name the name of the game to delete
     * @return the HTTP response status
     * @Author Neeshal Imrit
     */
    @DeleteMapping("/games/{name}")
    public ResponseEntity<?> deleteGame(@PathVariable String name){
        try{
            gameService.deleteGame(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all sessions.
     *
     * @return a list of game response DTOs
     * @author Neeshal Imrit
     */
    @GetMapping("/games")
    public ResponseEntity<?> findAllGames() {
        List<GameResponseDTO> dtos = new ArrayList<>();
        try{
            for (Game g : gameService.getAllGames()) {
                dtos.add(new GameResponseDTO(g));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a game by name
     *
     * @param name the name of the game to retrieve
     * @return the game response DTO
     * @Author Neeshal Imrit
     */
    @GetMapping("/games/{name}")
    public ResponseEntity<?> findGameByName(@PathVariable String name){
        try{
            Game game = gameService.findGameByName(name);
            GameResponseDTO response = new GameResponseDTO(game);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a game by category
     *
     * @param category the category of the game to retrieve
     * @return the game response DTO
     * @Author Neeshal Imrit
     */
    @GetMapping("/games/category/{category}")
    public ResponseEntity<?> findGameByCategory(@PathVariable String category){
        List<GameResponseDTO> dtos = new ArrayList<>();
        try{
            for (Game g : gameService.getGamesInCategory(category)) {
                dtos.add(new GameResponseDTO(g));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update the price of a game
     *
     * @param price the new price of the game
     * @param name the name of the game to update
     * @return the game response DTO
     * @Author Neeshal Imrit
     */
    @PostMapping("/games/{name}/price/{price}")
    public ResponseEntity<?> updateGamePrice(@PathVariable String name, @PathVariable int price){
        try{
            Game game = gameService.updateGamePrice(name, price);
            GameResponseDTO response = new GameResponseDTO(game);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update the quantity of a game
     * @param quantity the new quantity of the game
     * @param name the name of the game to update
     * @return the game response DTO
     */
    @PostMapping("/games/{name}/quantity/{quantity}")
    public ResponseEntity<?> updateGameQuantity(@PathVariable String name, @PathVariable int quantity){
        try{
            Game game = gameService.updateGameQuantity(name, quantity);
            GameResponseDTO response = new GameResponseDTO(game);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    


}
