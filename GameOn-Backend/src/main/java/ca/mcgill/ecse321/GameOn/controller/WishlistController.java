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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GameOn.dto.WishlistRequestDto;
import ca.mcgill.ecse321.GameOn.dto.WishlistResponseDto;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.service.WishlistService;
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
    public ResponseEntity<?> addGameToWishlist(@Valid @RequestBody WishlistRequestDto wishlistLinkDto) {
        try {
            WishlistLink wishlistLink = wishlistService.addGameToWishlist(wishlistLinkDto.getCustomerEmail(), wishlistLinkDto.getGameName());
            WishlistResponseDto wishlistLinkDtoResponse = new WishlistResponseDto(wishlistLink);
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
    public ResponseEntity<?> removeGameFromWishlist(@Valid @RequestBody WishlistRequestDto wishlistLinkDto) {
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
    @GetMapping("/wishlist-get-all/{customerEmail}")
    public ResponseEntity<?> getAllGamesFromWishlist(@PathVariable String customerEmail) {
        List<WishlistResponseDto> dtos = new ArrayList<>();
        try {
            List<WishlistLink> wishlistLinks = wishlistService.getAllGamesFromWishlist(customerEmail);
            for (WishlistLink wishlistLink : wishlistLinks) {
                dtos.add(new WishlistResponseDto(wishlistLink));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
