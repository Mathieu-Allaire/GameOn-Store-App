package ca.mcgill.ecse321.GameOn.controller;

import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameResponseDto;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Order;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.service.PurchaseGameService;
import jakarta.validation.Valid;
import ca.mcgill.ecse321.GameOn.dto.AddToCartRequestDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameInCartDto;


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
            Order order = purchaseGameService.findOrderById(id);
            OrderResponseDto response = new OrderResponseDto(order);
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
     * @param specificGameInCartDto
     * @return the http status
     */
    @PostMapping("/game-remove/{dto}") //done 
    public ResponseEntity<?> removeGameFromCart(@Valid @RequestBody SpecificGameInCartDto specificGameInCartDto){
        try{
            purchaseGameService.removeSpecificGameFromCart(specificGameInCartDto.getSpecificGameId(), specificGameInCartDto.getCartId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/game-remove/{sgid}/{cid}") //done 
    public ResponseEntity<?> removeGameFromCart(@PathVariable("sgId") int sgId, @PathVariable("cId") int cId){
        try{
            SpecificGameInCartDto specificGameInCartDto = new SpecificGameInCartDto(sgId, cId);
            purchaseGameService.removeSpecificGameFromCart(specificGameInCartDto.getSpecificGameId(), specificGameInCartDto.getCartId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove all games from cart
     * @param cartId
     * @return the http status
     */
    @PostMapping("/remove-all/{id}")
    public ResponseEntity<?> removeAllGamesFromCart(@PathVariable("id") int cartId){
        try{
            purchaseGameService.removeAllGamesFromCart(cartId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create an order from a cart
     * @param cartId
     * @return a ResponseEntity containing the OrderResponseDto or an error message
     */
    @PostMapping("/createOrder/{cartId}")
    public ResponseEntity<?> createOrder(@PathVariable int cartId){
        try{
            Order order = purchaseGameService.createOrderFromCart(cartId);
            OrderResponseDto response = new OrderResponseDto(order);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
