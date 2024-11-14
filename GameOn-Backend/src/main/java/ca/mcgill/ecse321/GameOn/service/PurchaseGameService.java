package ca.mcgill.ecse321.GameOn.service;

import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.model.OrderClass;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PurchaseGameService {

    @Autowired 
    private CartRepository cartRepository;
    @Autowired
    private SpecificGameRepository specificGameRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GameRepository gameRepository;

    /**
     * Method to retrieve Cart by ID
     * @param id id of cart
     * @throws IllegalArgumentException if id is negative
     */

    public Cart findCartByID(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = cartRepository.findCartById(id);
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + id + ".");
        }
        return cart;
    }

    /**
     * Method to retrieve Specific Game by ID
     * @param id
     * @throws IllegalArgumentException if id is negative
     */

    public SpecificGame findSpecificGameById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        SpecificGame specificGame = specificGameRepository.findSpecificGameById(id);
        if (specificGame == null) {
            throw new IllegalArgumentException("There are no specific game with the ID: " + id + ".");
        }
        return specificGame;
    }

    /**
     * Method to retrieve Order by ID
     * @param id
     * @throws IllegalArgumentException if id is negative
     */

    public OrderClass findOrderById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        OrderClass orderClass = orderRepository.findOrderById(id);
        if (orderClass == null) {
            throw new IllegalArgumentException("There are no order with the ID: " + id + ".");
        }
        return orderClass;
    }

    /**
     * Method to add Specific Game to cart
     * @param aGameName
     * @param cartId
     * @throws IllegalArgumentException if inputs are invalid
     */
    @Transactional
    public Cart addGameToCart(String aGameName, int cartId) {
        if (cartId < 0) {
            throw new IllegalArgumentException("Cart ID is invalid.");
        }
        if (aGameName == null || aGameName.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        Cart cart = findCartByID(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + cartId + ".");
        }

        Game game = gameRepository.findGameByName(aGameName);
        if (game == null) {
            throw new IllegalArgumentException("There are no game with the ID: " + aGameName + ".");
        }
        if (game.getQuantity() == 0) {
            throw new IllegalArgumentException("This game is out of stock.");
        }

        SpecificGame specificGame = new SpecificGame(game);
        specificGame = specificGameRepository.save(specificGame);

        cart.addSpecificGame(specificGame);
        return cartRepository.save(cart);
    }

    /**
     * Method to remove a Specific Game from the cart
     * @param specificGame
     * @param id
     * @throws IllegalArgumentException if inputs are invalid
     */
    @Transactional
    public void removeSpecificGameFromCart(int specificGameId, int cartId) {
        if (cartId < 0) {
            throw new IllegalArgumentException("Cart ID is invalid.");
        }
        if (specificGameId < 0) {
            throw new IllegalArgumentException("Specific Game ID is invalid.");
        }
        Cart cart = findCartByID(cartId);
        SpecificGame specificGame = findSpecificGameById(specificGameId);
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + cartId + ".");
        }
        if (!cart.removeSpecificGame(specificGame)) {
            throw new IllegalArgumentException("This game is not in the cart.");
        }
        cart.removeSpecificGame(specificGame);
        cartRepository.save(cart);
    }

     /**
     * Method to remove all games from the cart
     * @param id
     * @throws IllegalArgumentException if id is negative
     */
    @Transactional
    public void removeAllGamesFromCart(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Cart ID is invalid.");
        }
        Cart cart = findCartByID(id);
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }
        
        if (cart.getSpecificGames().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty.");
        }

        cart.removeAllGamesFromCart();
        cartRepository.save(cart);
    }
    /**
     * Method to create an order from a cart after a transaction
     * @param id
     * @throws IllegalArgumentException if id is negative
     */
    @Transactional
    public OrderClass createOrderFromCart(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = findCartByID(id);
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }
        long millis = System.currentTimeMillis();
        Date aPurchaseDate = new Date(millis);
        Customer aCustomer = cart.getCustomer();
        for (SpecificGame specificGame : cart.getSpecificGames()) {
            Game game = specificGame.getGame();
            game.setQuantity(game.getQuantity() - 1);
            gameRepository.save(game);
        }

        OrderClass orderClass = new OrderClass(aPurchaseDate, cart, aCustomer);
        orderClass = orderRepository.save(orderClass);
        cart.removeAllGamesFromCart();
        cartRepository.save(cart);
        return orderClass;
    }
}
