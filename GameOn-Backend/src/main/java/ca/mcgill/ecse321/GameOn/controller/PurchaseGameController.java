package ca.mcgill.ecse321.GameOn.controller;

import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.CartToOrderDto;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.RemoveAllGamesFromCartRequestDto;
import ca.mcgill.ecse321.GameOn.dto.RemoveFromCartRequestDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameResponseDto;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.OrderClass;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.service.PurchaseGameService;
import jakarta.validation.Valid;
import ca.mcgill.ecse321.GameOn.dto.AddToCartRequestDto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * this class allows us to control the purchase of games
 * @author Neeshal Imrit, Joseph Feghaly
 */
@RestController
public class PurchaseGameController {

    @Autowired
    private PurchaseGameService purchaseGameService;
    
    /**
     * Find a cart by its id
     * @param id the id of the cart
     * @return a ResponseEntity containing the CartDto or an error message
     */
    @GetMapping("/carts/{id}")
    public ResponseEntity<?> getCart(@PathVariable int id) {
        try{
            Cart cart = purchaseGameService.findCartByID(id);
            CartResponseDto response = new CartResponseDto(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Find a specific game by its id
     * @param id
     * @return a ResponseEntity containing the SpecificGameResponseDto or an error message
     */
    @GetMapping("/specificGames/{id}")
    public ResponseEntity<?> getSpecificGame(@PathVariable int id) {
        try{
            SpecificGame specificGame = purchaseGameService.findSpecificGameById(id);
            SpecificGameResponseDto response = new SpecificGameResponseDto(specificGame);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Find an order by its id
     * @param id
     * @return a ResponseEntity containing the OrderResponseDto or an error message
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {
        try{
            OrderClass orderClass = purchaseGameService.findOrderById(id);
            OrderResponseDto response = new OrderResponseDto(orderClass);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Add a specific game to a cart
     * @param addToCartRequestDto
     * @return a ResponseEntity containing the CartResponseDto or an error message
     */
    @PostMapping("/game-add")
    public ResponseEntity<?> addGameToCart(@Valid @RequestBody AddToCartRequestDto addToCartRequestDto){
        try{
            Cart aCart = purchaseGameService.addGameToCart(addToCartRequestDto.getGameName(), addToCartRequestDto.getCustomerId());
            CartResponseDto response = new CartResponseDto(aCart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove spefific game from cart
     * @param removeFromCartRequestDto
     * @return the http status
     */
    @PostMapping("/game-remove") //done 
    public ResponseEntity<?> removeGameFromCart(@Valid @RequestBody RemoveFromCartRequestDto removeFromCartRequestDto){
        try{
            Cart cart = purchaseGameService.removeSpecificGameFromCart(removeFromCartRequestDto.getSpecificGameId(),removeFromCartRequestDto.getCartId());
            CartResponseDto response = new CartResponseDto(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove all games from cart
     * @param removeAllGamesFromCartRequestDto
     * @return the http status
     */
    @PostMapping("/remove-all")
    public ResponseEntity<?> removeAllGamesFromCart(@Valid @RequestBody RemoveAllGamesFromCartRequestDto removeAllGamesFromCartRequestDto){
        try{
            Cart cart = purchaseGameService.removeAllGamesFromCart(removeAllGamesFromCartRequestDto.getCartId());
            CartResponseDto response = new CartResponseDto(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create an order from a cart
     * @param cartToOrderDto
     * @return a ResponseEntity containing the OrderResponseDto or an error message
     */
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody @Valid CartToOrderDto cartToOrderDto){
        try{
            int cartId = cartToOrderDto.getCartId();
            OrderClass orderClass = purchaseGameService.createOrderFromCart(cartId);
            if (orderClass == null) {
                return new ResponseEntity<>("Order could not be created", HttpStatus.BAD_REQUEST);
            }
            OrderResponseDto response = new OrderResponseDto(orderClass);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
