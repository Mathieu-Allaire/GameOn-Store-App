package ca.mcgill.ecse321.GameOn.integration;

import ca.mcgill.ecse321.GameOn.dto.WishlistResponseDto;
import ca.mcgill.ecse321.GameOn.repository.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.dto.WishlistRequestDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class WishlistIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private WishlistLinkRepository wishlistLinkRepo;
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PersonRepository personRepo;

    //Attributes for game
    private static final String VALID_GAME_NAME = "Overwatch";
    private static final String VALID_GAME_DESCRIPTION = "Hero-based combat";
    private static final int VALID_GAME_PRICE = 5;
    private static final int VALID_GAME_QUANTITY = 1;
    private static final String VALID_GAME_PICTURE = "url";

    private static final String VALID_CATEGORY_NAME = "Fight";

    //Attributes for customer
    private static final int VALID_CARD_NUM = 123;
    private static final Date VALID_DATE = Date.valueOf("2025-09-02");
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr";

    //Attributes for person
    private static final String VALID_EMAIL = "email@gmail.com";
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_PASSWORD = "password";

    //Helper functions
    Game exampleGame(){
        Category category = new Category(VALID_CATEGORY_NAME);
        return new Game(
                VALID_GAME_PICTURE,
                VALID_GAME_NAME,
                VALID_GAME_DESCRIPTION,
                VALID_GAME_PRICE,
                VALID_GAME_QUANTITY,
                category);
    }
    Customer exampleCustomer(){
        Cart cart = new Cart();
        return new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
    }
    Person examplePerson(){
        return new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD);
    }

    @BeforeAll
    public void setup(){

    }
    @AfterAll
    public void clearDatabase() {
        wishlistLinkRepo.deleteAll();
        gameRepo.deleteAll();
        customerRepo.deleteAll();
        categoryRepo.deleteAll();
        cartRepository.deleteAll();
        personRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void TestRemoveFromEmptyWishlist() {
        //Arrange
        String res = "/wishlist-remove";
        WishlistRequestDto request = new WishlistRequestDto(VALID_GAME_NAME, VALID_EMAIL);
        HttpEntity<WishlistRequestDto> entity = new HttpEntity<>(request);

        //Act
        ResponseEntity<?> response = client.exchange(res, HttpMethod.DELETE, entity, WishlistResponseDto.class);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals("The Game is not in the customer's wishlist", response.getBody().toString() );
    }

    @Test
    @Order(1)
    public void TestGetFromEmptyWishlist() {
        //Arrange
        String res = "/wishlist-get-all/"+ VALID_EMAIL;

        //Act
        ResponseEntity<List> response = client.getForEntity(res, List.class);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<WishlistResponseDto> wishlist = response.getBody();
        assertNotNull(wishlist);
        assertTrue(wishlist.isEmpty());
    }


    @Test
    @Order(2)
    public void TestAddValidGameToWishlist() {
        String res = "/wishlist-add";
    }
    @Test
    @Order(2)
    public void TestAddInvalidGameToWishlist() {
        String res = "/wishlist-add";
    }
    @Test
    @Order(2)
    public void TestAddGameToInvalidWishlist() {
        String res = "/wishlist-add";
    }

    @Test
    @Order(3)
    public void TestGetFromValidWishlist() {
        //Arrange
        String res = "/wishlist-get-all/"+ VALID_EMAIL;

        //Act
        ResponseEntity<List> response = client.getForEntity(res, List.class);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<WishlistResponseDto> wishlist = response.getBody();
        assertNotNull(wishlist);
        assertEquals(1, wishlist.size());

        WishlistResponseDto game = wishlist.getFirst();
        assertEquals(VALID_GAME_NAME, game.getGameName());
    }
    @Test
    @Order(3)
    public void TestGetFromInvalidCustomerWishlist() {
        //Arrange
        String res = "/wishlist-get-all/"+ "I don't exist";

        //Act
        ResponseEntity<List> response = client.getForEntity(res, List.class);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals("Person not found", response.getBody().toString() );
    }

    @Test
    @Order(4)
    public void TestRemoveFromInvalidWishlist() {
        //Arrange
        String res = "/wishlist-remove";
        WishlistRequestDto request = new WishlistRequestDto("No name", "No game");
        HttpEntity<WishlistRequestDto> entity = new HttpEntity<>(request);

        //Act
        ResponseEntity<WishlistResponseDto> response = client.exchange(res, HttpMethod.DELETE, entity, WishlistResponseDto.class);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals("No customer with this email", response.getBody().toString() );

    }
    @Test
    @Order(4)
    public void TestRemoveFromValidWishlist() {
        //Arrange
        String res = "/wishlist-remove";
        WishlistRequestDto request = new WishlistRequestDto(VALID_GAME_NAME, VALID_NAME);
        HttpEntity<WishlistRequestDto> entity = new HttpEntity<>(request);

        //Act
        ResponseEntity<WishlistResponseDto> response = client.exchange(res, HttpMethod.DELETE, entity, WishlistResponseDto.class);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

        
}
