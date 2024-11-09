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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * this class allows us to control the purchase of games
 * @author Luis Jarquin, Neeshal Imrit, Joseph Rouhana Feghaly, Joël Boyer
 */
@RestController
public class PurchaseGameController {

    @Autowired
    private PurchaseGameService purchaseGameService;
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/carts/{id}")
    public CartResponseDto findCartById(@PathVariable int id){
        Cart cart = purchaseGameService.findCartByID(id);
        return new CartResponseDto(cart);
    }

    @GetMapping("/specific_games/{id}")
    public SpecificGameResponseDto findSpecificGameById(@PathVariable int id){
        SpecificGame specificGame = purchaseGameService.findSpecificGameById(id);
        return new SpecificGameResponseDto(specificGame);
    }

    @GetMapping("/orders/{id}")
    public OrderResponseDto findOrderById(@PathVariable int id){
        Order order = purchaseGameService.findOrderById(id);
        return new OrderResponseDto(order);
    }

    @PostMapping("/add_to_cart/{id}")
    public void addSpecificGameToCart(@RequestBody CartResponseDto cart, @PathVariable int id) {
        purchaseGameService.addSpecificGameToCart(id, cart.getId());
    }

    @PostMapping("/remove_from_cart/{id}")
    public void removeSpecificGameFromCart(@RequestBody SpecificGame specificGame, @PathVariable String email){
        Person person = accountService.findCustomerByEmail(email);
        Customer customer = (Customer) person.getRole(0);
        Cart cart = customer.getCart();
        purchaseGameService.removeSpecificGameFromCart(specificGame, cart.getId());
    }

    @PostMapping("/remove_all_games_from_cart/{email}")
    public void removeAllGamesFromCart(@PathVariable String email){
        Person person = accountService.findCustomerByEmail(email);
        Customer customer = (Customer) person.getRole(0);
        Cart cart = customer.getCart();
        purchaseGameService.removeAllGamesFromCart(cart.getId());
    }

    @PostMapping("/cart_to_order_for_purchase/{id}")
    public OrderResponseDto cartToOrderForPurchase(@PathVariable String email){
        Person person = accountService.findCustomerByEmail(email);
        Customer customer = (Customer) person.getRole(0);
        Cart cart = customer.getCart();
        Order order = purchaseGameService.createOrderFromCart(cart.getId());
        return new OrderResponseDto(order);
    }

}
