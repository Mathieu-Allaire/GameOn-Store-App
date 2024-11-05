package ca.mcgill.ecse321.GameOn.controller;

import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameResponseDto;
import ca.mcgill.ecse321.GameOn.model.Cart;

import ca.mcgill.ecse321.GameOn.model.Order;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseGameController {

    @Autowired
    private PurchaseGameService purchaseGameService;

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
    public void addSpecificGameToCart(@PathVariable int id) {
        purchaseGameService.addSpecificGameToCart();
    }

    @PostMapping("/remove_from_cart/{id}")
    public void removeSpecificGameFromCart(@PathVariable int id){
        purchaseGameService.removeSpecificGameFromCart();
    }

    @PostMapping("/remove_all_games_from_cart")
    public void removeAllGamesFromCart(){
        purchaseGameService.removeAllGamesFromCart();
    }

    @PostMapping("TODO")
    public void cartToOrderForPurchase(){

    }

}
