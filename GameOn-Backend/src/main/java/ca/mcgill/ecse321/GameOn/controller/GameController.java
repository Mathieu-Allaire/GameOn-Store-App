package ca.mcgill.ecse321.GameOn.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.dto.GameCreateDto;
import ca.mcgill.ecse321.GameOn.service.GameService;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.dto.CategoryResponseDto;
import ca.mcgill.ecse321.GameOn.dto.CategoryRequestDto;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.GameRequest;
import ca.mcgill.ecse321.GameOn.dto.GameRequestResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameReqRequestDto;

import jakarta.validation.Valid;

/**
 * This class represents the Game Controller, which handles the HTTP
 * requests related to games.
 * It provides endpoints for creating, updating, deleting, and retrieving
 * games.
 *
 * @author Neeshal Imrit
 */
@CrossOrigin(origins="*")
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
    @PostMapping("/games/updatePrice")
    public ResponseEntity<?> updateGamePrice(@RequestParam String name, @RequestParam int price){
        try{
            Game game = gameService.updateGamePrice(name, price);
            GameResponseDTO response = new GameResponseDTO(game);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Game does not exist")) {
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update the quantity of a game
     * @param name the name of the game to update
     * @param quantity the new quantity of the game
     * @return the game response DTO
     */
    @PostMapping("/games/updateQuantity")
    public ResponseEntity<?> updateGameQuantity(@RequestParam String name, @RequestParam int quantity){
        try{
            Game game = gameService.updateGameQuantity(name, quantity);
            GameResponseDTO response = new GameResponseDTO(game);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Game does not exist")) {
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a category
     * @param categoryRequestDto the game create DTO
     * @return the game response DTO
     */
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto){
        try{
            Category category = gameService.createCategory(categoryRequestDto.getName());
            CategoryResponseDto response = new CategoryResponseDto(category);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Category already exists")) {
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.IM_USED);
            }
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a category
     * @param name the name of the category to delete
     * @return the HTTP response status
     */
    @DeleteMapping("/categories/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable String name){
        try{
            gameService.deleteCategory(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all categories
     * @return a list of category response DTOs
     */
    @GetMapping("/categories")
    public ResponseEntity<?> findAllCategories(){
        List<CategoryResponseDto> dtos = new ArrayList<>();
        try{
            for (Category c : gameService.getAllCategories()) {
                dtos.add(new CategoryResponseDto(c));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a category by name
     * @param name the name of the category to retrieve
     * @return the category response DTO
     */
    @GetMapping("/categories/{name}")
    public ResponseEntity<?> findCategoryByName(@PathVariable String name){
        try{
            Category category = gameService.findCategoryByName(name);
            CategoryResponseDto response = new CategoryResponseDto(category);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a game request
     * @param GameReqRequestDto the game request response DTO
     * @return response entity with the game request response DTO
     */
    @PostMapping("/games/request")
    public ResponseEntity<?> createGameRequest(@Valid @RequestBody GameReqRequestDto gameReqRequestDto){
        try{
            GameRequest gameRequest = gameService.createGameRequest(
                gameReqRequestDto.getEmployeeEmail(),
                gameReqRequestDto.getRequestedGameName(),
                gameReqRequestDto.getRequestType()
            );
            GameRequestResponseDto response = new GameRequestResponseDto(gameRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Approve a game request
     * @param gameRequestId the ID of the game request to approve
     */
    @PostMapping("/games/request/approve")
    public ResponseEntity<?> approveGameRequest(@RequestParam Integer gameRequestId ){
        try{
            gameService.approveGameRequest(gameRequestId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
