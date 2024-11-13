package ca.mcgill.ecse321.GameOn.service;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Order;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

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
     * @param cartId id of cart
     * @throws IllegalArgumentException if cartId is negative
     * @return List<SpecificGame> returns the list of specific games in a cart
     */
    public List<SpecificGame> findListOfGamesInCart(int cartId) {
        if (cartId < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = cartRepository.findCartById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + cartId + ".");
        }
        List<SpecificGame> specificGames = cart.getSpecificGames();
        return specificGames;
    }

    public SpecificGame findSpecificGameByIdInCart(int sgId, int cartId) {
        if (cartId < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        if (sgId < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = cartRepository.findCartById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + cartId + ".");
        }
        List<SpecificGame> specificGames = cart.getSpecificGames();
        for (SpecificGame sg : specificGames) {
            if (sg.getId() == sgId) {
                return sg;
            }
        }
        throw new IllegalArgumentException("There are no specific games with the id " + sgId + ".");


    }

    
    /**
     * Method to retrieve Game by ID
     * @param name, the name of the game
     * @return Game, the game
     * @throws IllegalArgumentException if name is null or empty
     */
    public Game findGameByName(String name) {
        if (name == "" || name == null) {
            throw new IllegalArgumentException("Name cant be empty");
        }
        Game game = gameRepository.findGameByName(name);
        if (game == null) {
            throw new IllegalArgumentException("There are no game named " + name + ".");
        }
        return game;
    }

    /**
     * Method to retrieve Order by ID
     * @param id, the id of the order
     * @return order, the order
     * @throws IllegalArgumentException if id is negative
     */

    public Order findOrderById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Order order = orderRepository.findOrderById(id);
        if (order == null) {
            throw new IllegalArgumentException("There are no order with the ID: " + id + ".");
        }
        return order;
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

        Cart cart = cartRepository.findCartById(cartId);
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
        Cart cart = cartRepository.findCartById(cartId);
        SpecificGame specificGame = findSpecificGameByIdInCart(specificGameId, cartId);
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
     * Method to return the list of games from the cart
     * @param id
     * @throws IllegalArgumentException if input is invalid
     */
    @Transactional
    public List<SpecificGame> getListOfSpecificGamesById(int id) {
        if (id < 0 ) {
            throw new IllegalArgumentException("Cart ID is invalid.");
        }
        Cart cart = cartRepository.findCartById(id);
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + id + ".");
        }
        return cart.getSpecificGames();

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
        Cart cart = cartRepository.findCartById(id);
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
    public Order createOrderFromCart(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = cartRepository.findCartById(id);
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

        Order order = new Order(aPurchaseDate, cart, aCustomer);
        order = orderRepository.save(order);
        cart.removeAllGamesFromCart();
        cartRepository.save(cart);
        return order;
    }
}
