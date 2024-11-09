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
        if (customerEmail == null || customerEmail.trim().length() == 0 || customerEmail.contains(" ") || customerEmail.contains("@") == false || customerEmail.contains(".") == false) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (gameName == null) {
            throw new IllegalArgumentException("Game name cannot be null");
        }

        Person customer = personRepository.findPersonByEmail(customerEmail);

        //No person with that email
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        //The person found with that email is not a customer
        if(personRepository.findPersonByEmail(customerEmail).getRole(0).getClass() != Customer.class){
            throw new IllegalArgumentException("No customer with this email");
        }

        Game game = gameRepository.findGameByName(gameName);

        //Verify that the game exists
            if(game == null ){
                throw new IllegalArgumentException("The game does not exist");
            }

        Customer customerRole = (Customer) customer.getRole(0);
        WishlistLink wishlistLink = wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole));
        //Verify that the customer does not already have the game in the wishlist
        if( wishlistLink != null){
            throw new IllegalArgumentException("The Game is already at the customer wishlist");
        }

        WishlistLink wishlist = new WishlistLink(game, customerRole);
        wishlistLinkRepository.save(wishlist);
        customerRole.addCustomerWish(wishlist);
        customerRepository.save(customerRole);

        //Long aCustomerId = aPerson.getRole(0).getId();
        //Customer aCustomer = customerRepository.findCustomerById(aCustomerId.intValue()); //this is the customer role associated to the person's email
       // WishlistLink wishlistLink = new WishlistLink(new WishlistLink.Key(game,aCustomer));
        //wishlistLinkRepository.save(wishlistLink);
        return wishlist;
        
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
        //Long aCustomerId = aPerson.getRole(0).getId();
        //Customer aCustomer = customerRepository.findCustomerById(aCustomerId.intValue());
        Customer customerRole = (Customer) aPerson.getRole(0);
        WishlistLink wishlistLink = wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole));
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
