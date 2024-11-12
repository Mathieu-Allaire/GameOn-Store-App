package ca.mcgill.ecse321.GameOn.controller;

import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameResponseDto;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Order;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.service.AccountService;
import ca.mcgill.ecse321.GameOn.service.PurchaseGameService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * This class allows us to controller the purchase of a game
 * @author Joseph Rouhana Feghaly, Joël Boyer
 */
@RestController
public class PurchaseGameController {

    @Autowired
    private PurchaseGameService purchaseGameService;
    @Autowired
    private AccountService accountService;
    /**
     * Find a cart by its id
     * @param email, the email of the customer
     * @return Response Entity containing an error message of the CartDto
     */
    @GetMapping("/carts/{email}")
    public ResponseEntity<?> findCartById(@PathVariable String email){

        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            CartResponseDto cartResponseDto = new CartResponseDto(cart);
            return new ResponseEntity<>(cartResponseDto, HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Find a specific game by its id
     * @param id, the id of the game
     * @return Response Entity containing an error message of the SpecificGameResponseDto
     */
    @GetMapping("/specific_games/{id}")
    public ResponseEntity<?> findSpecificGameById(@PathVariable int id){
        try{
            SpecificGame specificGame = purchaseGameService.findSpecificGameById(id);
            SpecificGameResponseDto specificGameResponseDto = new SpecificGameResponseDto(specificGame);
            return new ResponseEntity<>(specificGameResponseDto, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Find an order game by its id
     * @param id, the id of the order
     * @return Response Entity containing an error message or the OrderResponseDto
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable int id){
        try{
            Order order = purchaseGameService.findOrderById(id);
            OrderResponseDto orderResponseDto = new OrderResponseDto(order);
            return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Add a Specific Game to cart
     * @param email, the email of the customer, specifcGame, the game to add to cart
     * @return Response Entity containing an error message or the cartResponseDto
     */
    @PostMapping("/add_to_cart/{email}")
    public ResponseEntity<?> addSpecificGameToCart(@RequestBody SpecificGame specificGame, @PathVariable String email) {
        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            CartResponseDto cartResponseDto = new CartResponseDto(cart);
            purchaseGameService.addSpecificGameToCart(specificGame, cart.getId());
            return new ResponseEntity<>(cartResponseDto,HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Remove a Specific Game from cart
     * @param email, the email of the customer, specifcGame, the game to add to cart
     * @return Response Entity containing an error message or the cartResponseDto
     */
    @PostMapping("/remove_from_cart/{email}")
    public ResponseEntity<?> removeSpecificGameFromCart(@RequestBody SpecificGame specificGame, @PathVariable String email){
        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            CartResponseDto cartResponseDto = new CartResponseDto(cart);
            purchaseGameService.removeSpecificGameFromCart(specificGame, cart.getId());
            return new ResponseEntity<>(cartResponseDto, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Remove all games from cart
     * @param email, the email of the customer
     * @return Response Entity containing an error message or the cartResponseDto
     */
    @PostMapping("/remove_all_games_from_cart/{email}")
    public ResponseEntity<?> removeAllGamesFromCart(@PathVariable String email){
        try {
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            purchaseGameService.removeAllGamesFromCart(cart.getId());
            CartResponseDto cartResponseDto = new CartResponseDto(cart);
            return new ResponseEntity<>(cartResponseDto, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Turn a cart into an order
     * @param email, the email of the customer
     * @return Response Entity containing an error message or the cartResponseDto
     */
    @PostMapping("/cart_to_order_for_purchase/{email}")
    public ResponseEntity<?> cartToOrderForPurchase(@PathVariable String email){
        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            Order order = purchaseGameService.createOrderFromCart(cart.getId());
            OrderResponseDto orderResponseDto = new OrderResponseDto(order);
            return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
