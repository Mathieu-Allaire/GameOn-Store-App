package ca.mcgill.ecse321.GameOn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GameOn.dto.WishlistLinkDto;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.service.WishlistService;
import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ca.mcgill.ecse321.GameOn.model.Game;
import jakarta.validation.Valid;

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

    /*
     * Add game to wishlist of customer
     * 
     * @param WishlistLinkDto the wishlist link DTO
     * @author Neeshal Imrit
     */
    @PostMapping("/wishlist-add")
    public ResponseEntity<?> addGameToWishlist(@Valid @RequestBody WishlistLinkDto wishlistLinkDto) {
        try {
            WishlistLink wishlistLink = wishlistService.addGameToWishlist(wishlistLinkDto.getCustomerEmail(), wishlistLinkDto.getGameName());
            WishlistLinkDto wishlistLinkDtoResponse = new WishlistLinkDto(wishlistLink);
            return new ResponseEntity<>(wishlistLinkDtoResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Remove game from wishlist of customer
     * 
     * @param WishlistLinkDto the wishlist link DTO
     * @author Neeshal Imrit
     */
    @DeleteMapping("/wishlist-remove")
    public ResponseEntity<?> removeGameFromWishlist(@Valid @RequestBody WishlistLinkDto wishlistLinkDto) {
        try {
            wishlistService.removeGameFromWishlist(wishlistLinkDto.getCustomerEmail(), wishlistLinkDto.getGameName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    

    /*
     * Get all games from a customer's wishlist
     * 
     * @param customer email
     * @Author Neeshal Imrit
     */
    @PostMapping("/wishlist-get-all/{customerEmail}")
    public ResponseEntity<?> getAllGamesFromWishlist(@PathVariable String customerEmail) {
        try {
            List<Game> games = wishlistService.getAllGamesFromWishlist(customerEmail);
            List<GameResponseDTO> dtos = new ArrayList<>();
            for (Game g : games) {
                dtos.add(new GameResponseDTO(g));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
