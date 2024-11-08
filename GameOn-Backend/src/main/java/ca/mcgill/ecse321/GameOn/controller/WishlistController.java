package ca.mcgill.ecse321.GameOn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.GameOn.service.PurchaseGameService;
import ca.mcgill.ecse321.GameOn.service.WishlistService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * This class represents the Wishlist Controller, which handles the HTTP
 * requests related to wishlists.
 * It provides endpoints for adding and removing games from the wishlist. 
 * It provides the endpoints for adding a game to the cart and removing a game from the wishlist.
 * 
 * @Author Neeshal Imrit
 */

@RestController
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private PurchaseGameService purchaseGameService;

    /*
     * Add game to wishlist of customer
     * 
     * @param customer email
     * @param game name
     */
    @PostMapping("/wishlist-add")
    public ResponseEntity<?> addGameToWishlist(@RequestBody String customerEmail, @RequestBody String gameName) {
        try {
            wishlistService.addGameToWishlist()
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
}
