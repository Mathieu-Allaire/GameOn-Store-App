package ca.mcgill.ecse321.GameOn.service;



import ca.mcgill.ecse321.GameOn.model.OrderClass;

import ca.mcgill.ecse321.GameOn.exception.GameOnException;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;

import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.model.Game;

import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.ArrayList;
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
     * @param id id of cart
     * @throws IllegalArgumentException if id is negative
     */

    public Cart findCartByID(int id) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "ID is invalid.");
        }
        Cart cart = cartRepository.findCartById(id);
        if (cart == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There are no cart with the ID: " + id + ".");
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
            throw new GameOnException(HttpStatus.BAD_REQUEST, "ID is invalid.");
        }
        SpecificGame specificGame = specificGameRepository.findSpecificGameById(id);
        if (specificGame == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There are no specific game with the ID: " + id + ".");
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
            throw new GameOnException(HttpStatus.BAD_REQUEST, "ID is invalid.");
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
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Cart ID is invalid.");
        }
        if (aGameName == null || aGameName.trim().length() == 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Name cannot be empty.");
        }

        Cart cart = findCartByID(cartId);
        if (cart == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There are no cart with the ID: " + cartId + ".");
        }

        Game game = gameRepository.findGameByName(aGameName);
        if (game == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There are no game with the name: " + aGameName + ".");
        }
        if (game.getQuantity() == 0) {
            throw new GameOnException(HttpStatus.CONFLICT, "This game is out of stock.");
        }

        SpecificGame specificGame = new SpecificGame(game);
        specificGame = specificGameRepository.save(specificGame);

        cart.addSpecificGame(specificGame);
        return cartRepository.save(cart);
    }

    /**
     * Method to remove a Specific Game from the cart
     * @param specificGameId
     * @param cartId
     * @throws IllegalArgumentException if inputs are invalid
     */
    @Transactional
    public Cart removeSpecificGameFromCart(int specificGameId, int cartId) {
        if (cartId < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Cart ID is invalid.");
        }
        if (specificGameId < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Specific Game ID is invalid.");
        }
        Cart cart = findCartByID(cartId);
        SpecificGame specificGame = findSpecificGameById(specificGameId);
        if (cart == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "There are no cart with the ID: " + cartId + ".");
        }
        if (!cart.removeSpecificGame(specificGame)) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "This game is not in the cart.");
        }
        cart.removeSpecificGame(specificGame);
        return cartRepository.save(cart);
    }

     /**
     * Method to remove all games from the cart
     * @param id
     * @throws IllegalArgumentException if id is negative
     */
    @Transactional
    public Cart removeAllGamesFromCart(int id) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Cart ID is invalid.");
        }
        Cart cart = findCartByID(id);
        if (cart == null) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Cart cannot be null.");
        }
        
        if (cart.getSpecificGames().isEmpty()) {
            throw new GameOnException(HttpStatus.CONFLICT, "Cart is empty.");
        }

        cart.removeAllGamesFromCart();
        return cartRepository.save(cart);
    }
    /**
     * Method to create an order from a cart after a transaction
     * @param id
     * @throws IllegalArgumentException if id is negative
     */
    @Transactional
    public OrderClass createOrderFromCart(int id) {
        if (id < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "ID is invalid.");
        }
        Cart cart = findCartByID(id);
        if (cart == null) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Cart cannot be null.");
        }
        long millis = System.currentTimeMillis();
        Date aPurchaseDate = new Date(millis);
        Customer aCustomer = cart.getCustomer();
        for (SpecificGame specificGame : cart.getSpecificGames()) {
            Game game = specificGame.getGame();
            game.setQuantity(game.getQuantity() - 1);
            gameRepository.save(game);
        }

        OrderClass orderClass = new OrderClass(aPurchaseDate, aCustomer);
        List<SpecificGame> copiedGames = new ArrayList<>(cart.getSpecificGames());
        double price = 0;
        for (SpecificGame specificGame : copiedGames) {
            price += specificGame.getGame().getPrice();
        }
        orderClass.setOrderGames(copiedGames);
        orderClass.setPrice(price);
        orderClass = orderRepository.save(orderClass);
        cart.removeAllGamesFromCart();
        cartRepository.save(cart);
        return orderClass;
    }
}
