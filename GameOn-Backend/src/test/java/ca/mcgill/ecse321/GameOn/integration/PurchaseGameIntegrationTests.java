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

import ca.mcgill.ecse321.GameOn.dto.CartResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.dto.OrderResponseDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameInCartDto;
import ca.mcgill.ecse321.GameOn.dto.SpecificGameResponseDto;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.service.AccountService;

@SuppressWarnings("null")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PurchaseGameIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SpecificGameRepository specificGameRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GameRepository gameRepository;


    private static final String EMAIL = "joseph@gmail.com";
    private static final int ID_SG = 1234;
    private static final int ID_O = 1234;
    private static final String GAME_NAME = "GAME 1";

    @AfterAll
    public void clearDatabase(){
        cartRepository.deleteAll();
        specificGameRepository.deleteAll();
        orderRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testFindCartByValidEmail() {
        String url = "/carts/" + EMAIL;

        ResponseEntity<CartResponseDto> response = client.getForEntity(url, CartResponseDto.class);
        Cart cart = ((Customer) accountService.findCustomerByEmail(EMAIL).getRole(0)).getCart();
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        //assertEquals(cart.getId(), response.getBody().getId());
    }

    @Test
    @Order(2)
    public void testFindCartByInvalidEmail() {
        String url = "/carts/InvalidEmail";
        ResponseEntity<?> response = client.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testFindSpecificGamebyValidID() {
        String url = "/specific_games/" + ID_SG;
        ResponseEntity<SpecificGameResponseDto> response = client.getForEntity(url, SpecificGameResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ID_SG, response.getBody().getId());
    }

    @Test
    @Order(4)
    public void testFindSpecificGameByInvalidID() {
        String url = "/specific_games/InvalidSpecificGameId";
        ResponseEntity<?> response = client.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

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
        String url = "/orders/InvalidOrderId";
        ResponseEntity<?> response = client.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    @Order(7)
    public void testFindValidGame() {
        String url = "/games/" + GAME_NAME;
        ResponseEntity<GameResponseDTO> response = client.getForEntity(url, GameResponseDTO.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(GAME_NAME, response.getBody().getName());
    }

    @Test
    @Order(8) 
    public void testFindInvalidGame() {
        String url = "/games/InvalidGameName";
        ResponseEntity<?> response = client.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    } 

    @Test
    @Order(9)
    public void testAddGameToCart() {
        String url = "/games/addGame?" + "name=" + GAME_NAME + "&email=" + EMAIL;

        ResponseEntity<GameResponseDTO> response = client.postForEntity(url, null, GameResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(GAME_NAME, response.getBody().getName());
        //assertEquals(EMAIL, response.getBody()); // no clue what to put here
    }

    @Test
    @Order(10)
    public void testAddInvalidGameNameToCart() {
        String url = "/games/addGame?" + "name=InvalidGame" + "&email=" + EMAIL;
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Game does not exist", response.getBody());
    }

    @Test
    @Order(11)
    public void testAddGameNametoInvalidCart(){
        String url = "/games/addGame?" + "name=" + GAME_NAME + "&email=InvalidEmail" ;
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email cannot be empty", response.getBody()); // ig?????? not sure at allllllll
    }


   
    @Test
    @Order(12)
    public void testRemoveSpecificGameFromCart() {
        String url = "/carts/removeGame?" + "specificGameId=" + ID_SG + "&email=" + EMAIL;
        ResponseEntity<SpecificGameInCartDto> response = client.postForEntity(url, null, SpecificGameInCartDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(((Customer) accountService.findCustomerByEmail(EMAIL).getRole(0)).getCart().getId(), response.getBody().getCartId());
        assertEquals(ID_SG, response.getBody().getSpecificGameId());
    }
 
    @Test
    @Order(13)
    public void testRemoveInvalidSpecificGameFromCart() {
        String url = "/carts/removeGame?" + "specifcGameId="+ -1 + "&email=" + EMAIL;
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Specific Game ID is invalid.", response.getBody());
    }

    @Test
    @Order(14)
    public void testRemoveSpecificGameFromInvalidCart() {
        String url =  "/carts/removeGame?" + "specifcGameId="+ ID_SG + "&email=InvalidEmail";
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        //assertEquals(,response.getBody());
    }

    @Test
    @Order(15)
    public void testRemoveAllGamesFromCart() {
        String url = "/carts/removeAllGames?" + "email=" + EMAIL;
        ResponseEntity<CartResponseDto> response = client.postForEntity(url, null, CartResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(((Customer) accountService.findCustomerByEmail(EMAIL).getRole(0)).getCart().getId(), response.getBody().getId(), response.getBody().getId());
    }

    @Test
    @Order(16)
    public void testRemoveAllGamesFromInvalidCart() {
        String url = "/carts/removeAllGames?" + "email=InvalidEmail";
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        //assertEquals(error message,response.getBody());
    }

    @Test
    @Order(17)
    public void testCartToOrderForPurchase() {

    }

    @Test 
    @Order(18)
    public void testInvalidCartToOrderForPurchase() {

    }
}
