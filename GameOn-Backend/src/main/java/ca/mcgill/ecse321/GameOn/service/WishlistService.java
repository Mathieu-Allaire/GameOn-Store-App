package ca.mcgill.ecse321.GameOn.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.WishlistLinkRepository;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;

/**
 * This class contains the business logic for the wishlist service.
 * It handles the addition and removal of games from the wishlist.
 * It can add items from wishlist to cart and remove items from wishlist.
 * It can also retrieve the wishlist of a user.
 * 
 * @author Neeshal Imrit
 */
@Service
public class WishlistService {
    @Autowired
    private WishlistLinkRepository wishlistLinkRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private GameRepository gameRepository;

    /**
     * Add a game to the wishlist of a customer.
     * 
     * @param customerEmail the customer email
     * @param gameName the game name
     * @throws IllegalArgumentException if the customer or game is null
     */
    @Transactional
    public WishlistLink addGameToWishlist(String customerEmail, String gameName) {
        if (customerEmail == null) {
            throw new IllegalArgumentException("Customer email cannot be null");
        }
        if (gameName == null) {
            throw new IllegalArgumentException("Game name cannot be null");
        }

        WishlistLink existingWishlistLink = findWishlistLink(customerEmail, gameName);
        if (existingWishlistLink != null){
            return existingWishlistLink;
        } else {
            Person aPerson = personRepository.findPersonByEmail(customerEmail);
            if (aPerson == null) {
                throw new IllegalArgumentException("Person not found");
            }
            if (!(aPerson.getRole(0).getClass() != Customer.class)) {
                throw new IllegalArgumentException("Person is not a customer");
            }
            Long aCustomerId = aPerson.getRole(0).getId();
            Customer aCustomer = customerRepository.findCustomerById(aCustomerId.intValue());
            Game game = gameRepository.findGameByName(gameName);
            WishlistLink wishlistLink = new WishlistLink(new WishlistLink.Key(game,aCustomer));
            wishlistLinkRepository.save(wishlistLink);
            return wishlistLink;
        }
    }

    /**
     * Remove a game from the wishlist of a customer.
     * 
     * @param customerEmail the customer email
     * @param gameName the game name
     * @throws IllegalArgumentException if the customer or game is null
     */
    @Transactional
    public void removeGameFromWishlist(String customerEmail, String gameName) {
        if (customerEmail == null) {
            throw new IllegalArgumentException("Customer email cannot be null");
        }
        if (gameName == null) {
            throw new IllegalArgumentException("Game name cannot be null");
        }

        WishlistLink wishlistLink = findWishlistLink(customerEmail, gameName);
        if (wishlistLink == null) {
            throw new IllegalArgumentException("Wishlist link not found");
        }
    
        wishlistLinkRepository.delete(wishlistLink);
    }

    /**
     * Find a wishlist link.
     * 
     * @param customerEmail the email of the customer
     * @param gameName the name of the game
     * @throws IllegalArgumentException if the wishlist link is not found
     */
    @Transactional
    public WishlistLink findWishlistLink(String customerEmail, String gameName) {
        if (customerEmail == null) {
            throw new IllegalArgumentException("Customer email cannot be null");
        }
        if (gameName == null) {
            throw new IllegalArgumentException("Game name cannot be null");
        }

        Person aPerson = personRepository.findPersonByEmail(customerEmail);
        if (aPerson == null) {
            throw new IllegalArgumentException("Person not found");
        }
        if (!(aPerson.getRole(0).getClass() != Customer.class)) {
            throw new IllegalArgumentException("Person is not a customer");
        }

        Game game = gameRepository.findGameByName(gameName);

        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        Long aCustomerId = aPerson.getRole(0).getId();
        Customer aCustomer = customerRepository.findCustomerById(aCustomerId.intValue());
        WishlistLink wishlistLink = wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, aCustomer));
        if (wishlistLink == null) {
            throw new IllegalArgumentException("Wishlist link not found");
        }
        return wishlistLink;
    }

    /**
     * Remove all games from the wishlist of a customer.
     * 
     * @param customerEmail the email of the customer
     * @throws IllegalArgumentException if the customer is null
     */
    @Transactional
    public void removeAllGamesFromWishlist(String customerEmail) {
        if (customerEmail == null) {
            throw new IllegalArgumentException("Customer email cannot be null");
        }
        for (WishlistLink wishlistLink : wishlistLinkRepository.findAll()) {
            Person aPerson = personRepository.findPersonByEmail(customerEmail);
            Customer customer = customerRepository.findCustomerById(aPerson.getRole(0).getId().intValue());
            if (wishlistLink.getKey().getCustomer().equals(customer)) {
                wishlistLinkRepository.delete(wishlistLink);
            }
        }
    }

    /**
     * Retrieve the wishlist of a customer.
     * 
     * @param customerEmail the email of the customer
     * @throws IllegalArgumentException if the customer is null
     */
    @Transactional
    public List<Game> getAllGamesFromWishlist(String customerEmail) {
        if (customerEmail == null) {
            throw new IllegalArgumentException("Customer email cannot be null");
        }
        Person aPerson = personRepository.findPersonByEmail(customerEmail);
        if (aPerson == null) {
            throw new IllegalArgumentException("Person not found");
        }
        if (!(aPerson.getRole(0).getClass() != Customer.class)) {
            throw new IllegalArgumentException("Person is not a customer");
        }
        Long aCustomerId = aPerson.getRole(0).getId();
        Customer aCustomer = customerRepository.findCustomerById(aCustomerId.intValue());
        Iterable<WishlistLink> wishlistsCustomer = aCustomer.getCustomerWish();
        List<Game> games = new ArrayList<>();
        // Get all games from wishlist
        for (WishlistLink wishlistLink : wishlistsCustomer) {
            games.add(wishlistLink.getKey().getWishlistGames());
        }

        return games;
    }
}
