package ca.mcgill.ecse321.GameOn.integration;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.GameOn.dto.AddToCartRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameInCartDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameResponseDto;

import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.service.PurchaseGameService;


@SuppressWarnings("null")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PurchaseGameIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PurchaseGameService purchaseGameService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SpecificGameRepository specificGameRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GameRepository gameRepository;


    private static final int ID_SG = 1234;
    private static final int ID_O = 12345;
    private static final String GAME_NAME = "GAME 1";
    private static final int C_ID = 12;

    @AfterAll
    public void clearDatabase(){
        cartRepository.deleteAll();
        specificGameRepository.deleteAll();
        orderRepository.deleteAll();
        gameRepository.deleteAll();
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


    @Test
    @Order(5)
    public void testFindValidOrder() {
        String url = "/orders/" + ID_O;
        ResponseEntity<OrderResponseDto> response = client.getForEntity(url, OrderResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ID_O, response.getBody().getId());
    }

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
        
        AddToCartRequestDto requestDto = new AddToCartRequestDto();
        requestDto.setGameName(GAME_NAME);
        requestDto.setCustomerId(C_ID);
        ResponseEntity<AddToCartRequestDto> response = client.postForEntity("/game-add", requestDto, AddToCartRequestDto.class);
        

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(GAME_NAME, response.getBody().getGameName());
        assertEquals(C_ID, response.getBody().getCustomerId()); // no clue what to put here
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
        SpecificGameInCartDto specificGameInCartDto = new SpecificGameInCartDto(ID_SG, C_ID);
        ResponseEntity<SpecificGameInCartDto> response = client.postForEntity("/game-remove/" + 1 + "/" + 2, specificGameInCartDto, SpecificGameInCartDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(C_ID, response.getBody().getCartId());
        assertEquals(ID_SG, response.getBody().getSpecificGameId());
    }

    @Test
    @Order(12)
    public void testRemoveInvalidSpecifcGameFromCart() {
        String url = "/game-remove/" + -1 + "/" + -2;
        ResponseEntity<String> response = client.postForEntity(url, null, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Specific Game ID is invalid.", response.getBody());
    }

    @Test
    @Order(13)
    public void testRemoveSpecifcGameFromInvalidCart() {
        String url = "/game-remove/" + 1 + "/" + -2;
        ResponseEntity<String> response = client.postForEntity(url, null, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cart ID is invalid.", response.getBody());
    }


    @Test
    @Order(14)
    public void testRemoveAllGamesFromCart() {
        ResponseEntity<SpecificGameInCartDto[]> response = client.postForEntity("/remove-all", null, SpecificGameInCartDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().length);
        assertEquals(C_ID, response.getBody()[0].getCartId());
        assertEquals(ID_SG, response.getBody()[0].getSpecificGameId());
    }

    @Test
    @Order(15)
    public void testRemoveAllGamesFromCart_Exception() {
        int invalidCartId = -1; // An invalid cart ID

        // Send the request
        String url = "/remove-all/" + invalidCartId;
        ResponseEntity<String> response = client.postForEntity(url, null, String.class);

        // Validate the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cart ID is invalid.", response.getBody());
    }

    @Test
    @Order(16)
    public void testValidCartToOrderForPurchase() {
        
        String url = "/createOrder/" + C_ID;
        ResponseEntity<OrderResponseDto> response = client.postForEntity(url, null, OrderResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(C_ID, response.getBody().getId());
    }

    @Test 
    @Order(17)
    public void testInvalidCartToOrderForPurchase() {
        String url = "/createOrder/" + -1;
        ResponseEntity<String> response = client.postForEntity(url, null, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ID is invalid.", response.getBody());
    }
}