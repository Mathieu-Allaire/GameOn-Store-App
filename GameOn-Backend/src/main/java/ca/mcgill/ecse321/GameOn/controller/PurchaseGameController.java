package ca.mcgill.ecse321.GameOn.controller;

import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameInCartDto;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Order;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.service.AccountService;
import ca.mcgill.ecse321.GameOn.service.PurchaseGameService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @return Response Entity containing an error message or a List SpecificGameInCartDto
     */
    @GetMapping("/customer/{email}")
    public ResponseEntity<?> findListOfGamesByEmail(@PathVariable String email){

        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            List<SpecificGame>  specificGames = purchaseGameService.getListOfSpecificGamesById(cart.getId());
            List<SpecificGameInCartDto> specificGameInCartDtos = new ArrayList<>();
            for (SpecificGame specificGame : specificGames) {
                specificGameInCartDtos.add(new SpecificGameInCartDto(specificGame.getId(), cart.getId()));
            }
            return new ResponseEntity<>(specificGameInCartDtos, HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Find a specific game by its id
     * @param id, the id of the game
     * @param email, the email of the customer
     * @return Response Entity containing an error message of the SpecificGameInCartDto
     */
    @GetMapping("/specific_games/{id}")
    public ResponseEntity<?> findSpecificGameByIdInCartByEmail(@PathVariable int id, @PathVariable String email){
        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            SpecificGameInCartDto specificGameInCartDto = new SpecificGameInCartDto(id, cart.getId());
            return new ResponseEntity<>(specificGameInCartDto, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/games/{name}")
    public ResponseEntity<?> findGameByName(@PathVariable String name){
        try {
            Game game = purchaseGameService.findGameByName(name);
            GameResponseDTO gameResponseDTO = new GameResponseDTO(game);
            return new ResponseEntity<>(gameResponseDTO, HttpStatus.OK);
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
    @PostMapping("/games/addGame")
    public ResponseEntity<?> addGameToCart(@RequestParam String name, @RequestParam String email) {
        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            CartResponseDto cartResponseDto = new CartResponseDto(cart);
            purchaseGameService.addGameToCart(name, cart.getId());
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
    @PostMapping("/carts/removeGame")
    public ResponseEntity<?> removeSpecificGameFromCart(@RequestParam int specificGameId, @RequestParam String email){
        try{
            Person person = accountService.findCustomerByEmail(email);
            Customer customer = (Customer) person.getRole(0);
            Cart cart = customer.getCart();
            CartResponseDto cartResponseDto = new CartResponseDto(cart);
            purchaseGameService.removeSpecificGameFromCart(specificGameId, cart.getId());
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
    @PostMapping("/carts/removeAllGames")
    public ResponseEntity<?> removeAllGamesFromCart(@RequestParam String email){
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
    @PostMapping("/carts/cartToOrder")
    public ResponseEntity<?> cartToOrderForPurchase(@RequestParam String email){
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
