package ca.mcgill.ecse321.GameOn.service;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Order;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
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

    /**
     * Method to retrieve Cart by ID
     * @param id
     * @throws IllegalArgumentException if id is negative
     */
    @Transactional
    public Cart findCartByID(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = cartRepository.findCartById(id);
        if (cart == null) {
            throw new IllegalArgumentException("There are no cards with the ID: " + id + ".");
        }
        return cart;
    }

    /**
     * Method to retrieve Specific Game by ID
     * @param id
     * @throws IllegalArgumentException if id is negative
     */
    @Transactional
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
    @Transactional
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
     * @param specifcGame
     * @param id
     * @throws IllegalArgumentException if inputs are invalid
     */
    @Transactional
    public void addSpecificGameToCart(SpecificGame specificGame, int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = findCartByID(id);
        if (specificGame == null) {
            throw new IllegalArgumentException("Specific Game cannot be null.");
        }    
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + id + ".");
        }
        cart.addSpecificGame(specificGame);

    }

    /**
     * Method to remove a Specific Game from the cart
     * @param specifcGame
     * @param id
     * @throws IllegalArgumentException if inputs are invalid
     */
    @Transactional
    public void removeSpecificGameFromCart(SpecificGame specificGame, int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = findCartByID(id);
        if (specificGame == null) {
            throw new IllegalArgumentException("Specific Game cannot be null.");
        }
        if (cart == null) {
            throw new IllegalArgumentException("There are no cart with the ID: " + id + ".");
        }
        if (!cart.removeSpecificGame(specificGame)) {
            throw new IllegalArgumentException("This game is not in the cart.");
        }
        
    }

     /**
     * Method to remove all games from the cart
     * @param id
     * @throws IllegalArgumentException if id is negative
     */
    @Transactional
    public void removeAllGamesFromCart(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID is invalid.");
        }
        Cart cart = findCartByID(id);
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }
        
        cart.removeAllGamesFromCart();
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
        Cart cart = findCartByID(id);
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }
        long millis = System.currentTimeMillis();
        Date aPurchaseDate = new Date(millis);
        Customer aCustomer = cart.getCustomer();

        Order order = new Order(aPurchaseDate, cart, aCustomer);
        return order;
    }
}
