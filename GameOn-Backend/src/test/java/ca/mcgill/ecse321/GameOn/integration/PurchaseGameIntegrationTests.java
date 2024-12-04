package ca.mcgill.ecse321.GameOn.integration;

import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.repository.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.TestInstance.Lifecycle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.GameOn.dto.AddToCartRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.CartToOrderDto;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.RemoveAllGamesFromCartRequestDto;
import ca.mcgill.ecse321.GameOn.dto.RemoveFromCartRequestDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameResponseDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;


@SuppressWarnings("null")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PurchaseGameIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SpecificGameRepository specificGameRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GameRepository gameRepository;


    private static  int ID_SG;
    private static  int ID_O;
    private static  String GAME_NAME;
    private static  int C_ID;
    private static final Date VALID_DATE = Date.valueOf("2025-09-02");

    private Cart cart;
    private Category category;
    private Game game;
    private SpecificGame specificGame;
    private Customer customer;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Game exampleGame(Category aCategory) {
        String aPicture = "url";
        String aName = "exGame";
        String aDescription = "fun in 321";
        int aPrice = 60;
        int aQuantity = 100;


        return new Game(aPicture, aName, aDescription, aPrice, aQuantity, aCategory);
    }


    @BeforeAll
    public void setup(){
        cart = new Cart();
        cartRepository.save(cart);
        C_ID = cart.getId();

        category = new Category("Open World");
        categoryRepository.save(category);
        game = exampleGame(category);
        gameRepository.save(game);
        specificGame = new SpecificGame(game);
        specificGameRepository.save(specificGame);
        ID_SG = specificGame.getId();
        cart.addSpecificGame(specificGame);
        customer = new Customer(ID_O, VALID_DATE, "245 main st", cart);
        customerRepository.save(customer);
        cartRepository.save(cart);
        OrderClass orderClass = new OrderClass(VALID_DATE , customer);
        orderRepository.save(orderClass);
        ID_O = orderClass.getId();
        GAME_NAME = game.getName();
    }

    @AfterAll
    public void clearDatabase() {
        // Start with the most dependent entities
        for (OrderClass order : orderRepository.findAll()) {
            order.setOrderGames(new ArrayList<>()); // Clear games associated with the order
            orderRepository.save(order);      // Save changes to break foreign key relationships
        }
        specificGameRepository.deleteAll();
        for (Customer customer : customerRepository.findAll()) {
            customer.setCart(null); // Break the foreign key relationship
            customer.setOrders(new ArrayList<>()); // Clear orders if needed
            customerRepository.save(customer); // Save changes to the database
        }
        orderRepository.deleteAll();
        gameRepository.deleteAll();
        categoryRepository.deleteAll();
        cartRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testFindCartByValidId() {
        String url = "/carts/" + C_ID;

        ResponseEntity<CartResponseDto> response = client.getForEntity(url, CartResponseDto.class);
    
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(C_ID, response.getBody().getId());
    }

    @Test
    @Order(2)
    public void testFindCartByInvalidId() {
        String url = "/carts/" + -1;
        ResponseEntity<?> response = client.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ID is invalid.",response.getBody());
    }

    @Test
    @Order(3)
    public void testFindSpecificGamebyValidID() {
        String url = "/specificGames/" + ID_SG;
        ResponseEntity<SpecificGameResponseDto> response = client.getForEntity(url, SpecificGameResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ID_SG, response.getBody().getId());
    }

    @Test
    @Order(4)
    public void testFindSpecificGameByInvalidID() {
        String url = "/specificGames/" + -1;
        ResponseEntity<?> response = client.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ID is invalid.",response.getBody());
    }

    /* 
    @Test
    @Order(5)
    public void testFindValidOrder() {
        String url = "/orders/" + ID_O;
        ResponseEntity<OrderResponseDto> response = client.getForEntity(url, OrderResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ID_O, response.getBody().getId());
    }*/

    @Test
    @Order(6)
    public void testFindInvalidOrder() {
        String url = "/orders/" + -1;
        ResponseEntity<?> response = client.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ID is invalid.",response.getBody());
    }

    @Test
    @Order(7)
    public void testAddGameToCart() {
        // Prepare request DTO
        AddToCartRequestDto requestDto = new AddToCartRequestDto();
        requestDto.setGameName(GAME_NAME);
        requestDto.setCustomerId(C_ID);

        // Send POST request
        ResponseEntity<CartResponseDto> response = client.postForEntity("/game-add", requestDto, CartResponseDto.class);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        CartResponseDto responseBody = response.getBody();

        // Verify that the game was added to the cart
        assertNotNull(responseBody.getSpecificGames());
        //assertTrue(responseBody.getSpecificGames().containsKey(GAME_NAME)); // Ensure the game is in the list
        assertTrue(
                responseBody.getSpecificGames().stream().anyMatch(game -> game.getId() == ID_SG)
        );
        assertEquals(C_ID, responseBody.getId()); // Check the Cart ID matches
    }

    @Test
    @Order(8)
    public void testAddInvalidGameNameToCart() {


        // Create the request DTO with an invalid game name
        AddToCartRequestDto requestDto = new AddToCartRequestDto();
        requestDto.setGameName("");
        requestDto.setCustomerId(C_ID);

        // Send the POST request with the DTO as the body
        ResponseEntity<String> response = client.postForEntity("/game-add", requestDto, String.class);

        // Validate the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Name cannot be empty.", response.getBody());
    }

    @Test
    @Order(9)
    public void testAddGameNametoInvalidCart(){
        AddToCartRequestDto requestDto = new AddToCartRequestDto();
        requestDto.setGameName("bruh");
        requestDto.setCustomerId(-1);
        ResponseEntity<?> response = client.postForEntity("/game-add", requestDto, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Cart ID is invalid.", response.getBody());
    }

    @Test
    @Order(10)
    public void testRemoveSpecificGameFromCart() {
        RemoveFromCartRequestDto removeFromCartRequestDto = new RemoveFromCartRequestDto();
        removeFromCartRequestDto.setCartId(C_ID);
        removeFromCartRequestDto.setSpecificGameId(ID_SG);
        ResponseEntity<CartResponseDto> response = client.postForEntity("/game-remove", removeFromCartRequestDto, CartResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        CartResponseDto responseBody = response.getBody();

        // Assert the cart ID matches
        assertEquals(C_ID, responseBody.getId());

        // Verify the specific game was removed
        assertNotNull(responseBody.getSpecificGames());
        assertFalse(
                responseBody.getSpecificGames().stream().anyMatch(game -> game.getId() == ID_SG)
        );
    }

    @Test
    @Order(11)
    public void testRemoveInvalidSpecifcGameFromCart() {
        RemoveFromCartRequestDto removeFromCartRequestDto = new RemoveFromCartRequestDto();
        removeFromCartRequestDto.setCartId(C_ID);
        removeFromCartRequestDto.setSpecificGameId(-1);
        String url = "/game-remove";
        ResponseEntity<String> response = client.postForEntity(url, removeFromCartRequestDto, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Specific Game ID is invalid.", response.getBody());
    }

    @Test
    @Order(12)
    public void testRemoveSpecifcGameFromInvalidCart() {
        RemoveFromCartRequestDto removeFromCartRequestDto = new RemoveFromCartRequestDto();
        removeFromCartRequestDto.setCartId(-1);
        removeFromCartRequestDto.setSpecificGameId(ID_SG);
        String url = "/game-remove";        
        ResponseEntity<String> response = client.postForEntity(url, removeFromCartRequestDto, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cart ID is invalid.", response.getBody());
    }

    @Test
    @Order(18)
    public void testRemoveSpecificGameNotInCart() {
        RemoveFromCartRequestDto removeFromCartRequestDto = new RemoveFromCartRequestDto();
        Cart cart = new Cart();
        removeFromCartRequestDto.setCartId(cart.getId());
        removeFromCartRequestDto.setSpecificGameId(ID_SG);
        String url = "/game-remove";
        ResponseEntity<String> response = client.postForEntity(url, removeFromCartRequestDto, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There are no cart with the ID: " + cart.getId() + ".", response.getBody());
    }

    @Test
    @Order(13)
    public void testRemoveAllGamesFromCart() {
        RemoveAllGamesFromCartRequestDto removeAllGamesFromCartRequestDto = new RemoveAllGamesFromCartRequestDto();
        removeAllGamesFromCartRequestDto.setCartId(C_ID);
        ResponseEntity<CartResponseDto> response = client.postForEntity("/remove-all", removeAllGamesFromCartRequestDto, CartResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).getSpecificGames().size());
        assertEquals(C_ID, response.getBody().getId());
    }

    @Test
    @Order(14)
    public void testRemoveAllGamesFromInvalidCart() {
        RemoveAllGamesFromCartRequestDto removeAllGamesFromCartRequestDto = new RemoveAllGamesFromCartRequestDto();
        removeAllGamesFromCartRequestDto.setCartId(-1);

        // Send the request
        String url = "/remove-all";
        ResponseEntity<String> response = client.postForEntity(url, removeAllGamesFromCartRequestDto, String.class);

        // Validate the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cart ID is invalid.", response.getBody());
    }
    /*
    @Test
    @Order(15) // Adjust order as needed
    public void testCartToOrder() {
        cart.addSpecificGame(specificGame);
        cart = cartRepository.save(cart);
        // Prepare request DTO
        CartToOrderDto requestDto = new CartToOrderDto(C_ID);
        // Send POST request
        ResponseEntity<OrderResponseDto> response = client.postForEntity("/createOrder", requestDto, OrderResponseDto.class);
    

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        OrderResponseDto responseBody = response.getBody();

        // Verify the order details
        assertNotNull(responseBody.getSpecificGameNames());
        assertFalse(responseBody.getSpecificGameIds().isEmpty()); // Ensure the order contains games
        assertNotNull(responseBody.getPurchaseDate()); // Ensure the purchase date is set
        assertNotNull(responseBody.getId());
    }*/
    

    @Test 
    @Order(16)
    public void testInvalidCartToOrderForPurchase() {
        String url = "/createOrder";
        CartToOrderDto request = new CartToOrderDto(-1); // set an illegal id
        ResponseEntity<String> response = client.postForEntity(url, request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ID is invalid.", response.getBody());
    }
}