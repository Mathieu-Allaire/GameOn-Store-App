package ca.mcgill.ecse321.GameOn.integration;

import ca.mcgill.ecse321.GameOn.dto.CategoryRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CategoryResponseDto;
import ca.mcgill.ecse321.GameOn.dto.CustomerRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CustomerResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameCreateDto;
import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.dto.WishlistResponseDto;
import ca.mcgill.ecse321.GameOn.repository.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
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
    private GameRepository gameRepository;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PersonRepository personRepo;
    
    //Attributes for game
    private static final String GAME_NAME2 = "Test Game 2";
    private static final String GAME_NAME = "Test Game";
    private static final String GAME_DESCRIPTION = "This is a test game";
    private static final String GAME_PICTURE = "https://www.example.com";
    private static final int GAME_PRICE = 10;
    private static final int GAME_QUANTITY = 10;
    private static final String CATEGORY_NAME = "Test Category";

    private Game game1;
    private Game game2;

    //Attributes for customer
    private static final String VALID_EMAIL = "bob@gmail.com"; // no spaces,contain @ and . 
    private static final String VALID_NAME = "Bob"; // at least one letter
    private static final String VALID_PASSWORD = "bob123456789"; // bigger than 8 characters
    private static final int VALID_CARD_NUM = 123; // larger than 0
    private static final Date VALID_DATE = Date.valueOf("2025-09-02"); // needs to be a date after today's date
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr"; // at least one character

    private Customer aCustomer;


    @AfterAll
    public void clearDatabase() {
        wishlistLinkRepo.deleteAll();
        gameRepository.deleteAll();
        categoryRepository.deleteAll();
        personRepo.deleteAll();
        customerRepo.deleteAll();
    }

  
    @Test
    @Order(1)
    public void testCreateCategory() {
        // Arrange
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(CATEGORY_NAME);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.postForEntity("/categories", categoryRequestDto, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(CATEGORY_NAME, response.getBody().getName());
        Category aCategory = categoryRepository.findCategoryByName(CATEGORY_NAME);
        assertNotNull(aCategory);
        assertEquals(CATEGORY_NAME, aCategory.getName());
    }

     @Test
    @Order(2)
    public void testCreateGame() {
        // Arrange
        GameCreateDto gameCreateDto = new GameCreateDto(GAME_PICTURE, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, CATEGORY_NAME);
        GameCreateDto gameCreateDto2 = new GameCreateDto(GAME_PICTURE, GAME_NAME2, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, CATEGORY_NAME);
        // Act
        ResponseEntity<GameResponseDTO> response = client.postForEntity("/games", gameCreateDto, GameResponseDTO.class);
        ResponseEntity<GameResponseDTO> response2 = client.postForEntity("/games", gameCreateDto2, GameResponseDTO.class);
        game1 = gameRepository.findGameByName(GAME_NAME);
        game2 = gameRepository.findGameByName(GAME_NAME2);

        // Assert
        assertNotNull(response);
        assertNotNull(response2);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());

        assertEquals(GAME_NAME, response.getBody().getName());
        assertEquals(GAME_NAME2, response2.getBody().getName());

        assertEquals(GAME_DESCRIPTION, response.getBody().getDescription());
        assertEquals(GAME_DESCRIPTION, response2.getBody().getDescription());

        assertEquals(GAME_PICTURE, response.getBody().getPicture());
        assertEquals(GAME_PICTURE, response2.getBody().getPicture());

        assertEquals(GAME_PRICE, response.getBody().getPrice());
        assertEquals(GAME_PRICE, response2.getBody().getPrice());

        assertEquals(GAME_QUANTITY, response.getBody().getQuantity());
        assertEquals(GAME_QUANTITY, response2.getBody().getQuantity());

        assertEquals(CATEGORY_NAME, response.getBody().getCategory());
        assertEquals(CATEGORY_NAME, response2.getBody().getCategory());
    }
     
    @Test
    @Order(3)
    public void testCreateValidCustomer(){
        //Create the wanted customerRequest
        CustomerRequestDto bob = new CustomerRequestDto(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);

        //ACT
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customer", bob, CustomerResponseDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CustomerResponseDto person = response.getBody();
        assertNotNull(person);
        assertEquals(VALID_NAME, person.getName());
        assertEquals(VALID_EMAIL, person.getEmail());
        Integer aPerson = personRepo.findRoleIdsByPersonEmail(VALID_EMAIL);
        assertNotNull(aPerson);
        aCustomer = customerRepo.findCustomerById(aPerson);
        assertNotNull(aCustomer);
    }

    @Test
    @Order(4)
    public void testAddGameToWishlist() {
        // Arrange
        WishlistRequestDto request = new WishlistRequestDto(GAME_NAME, VALID_EMAIL);

        // Act
        ResponseEntity<WishlistResponseDto> response = client.postForEntity("/wishlist-add", request, WishlistResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(GAME_NAME, response.getBody().getGameName());
        assertEquals(GAME_PRICE, response.getBody().getGamePrice());
        assertEquals(GAME_QUANTITY, response.getBody().getGameQuantity());
        assertEquals(GAME_PICTURE, response.getBody().getGamePicture());
    }

    @Test
    @Order(5)
    public void testAddDuplicateGameToWishList(){
        // Arrange
        WishlistRequestDto request = new WishlistRequestDto(GAME_NAME, VALID_EMAIL);

        // Act
        ResponseEntity<String> response = client.postForEntity("/wishlist-add", request, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The Game is already at the customer wishlist", response.getBody());
    }

    @Test
    @Order(6)
    public void testAddInvalidGameToWishlist() {
        // Arrange
        WishlistRequestDto request = new WishlistRequestDto("Bruh", VALID_EMAIL);

        // Act
        ResponseEntity<String> response = client.postForEntity("/wishlist-add", request, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The game does not exist", response.getBody());
    }

    @Test
    @Order(7)
    public void testAddGameToInvalidWishlist() {
        // Arrange
        WishlistRequestDto request = new WishlistRequestDto(GAME_NAME, "Ghost@fantome.com");

        // Act
        ResponseEntity<String> response = client.postForEntity("/wishlist-add", request, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }

    @Test
    @Order(8)
    public void testgetAllGamesFromWishlist() {
        // Arrange
        String url = "/wishlist-get-all/" + VALID_EMAIL;

        // Act
        ResponseEntity<WishlistResponseDto[]> response = client.getForEntity(url, WishlistResponseDto[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().length);
        assertEquals(GAME_NAME, response.getBody()[0].getGameName());
        assertEquals(GAME_PRICE, response.getBody()[0].getGamePrice());
        assertEquals(GAME_QUANTITY, response.getBody()[0].getGameQuantity());
        assertEquals(GAME_PICTURE, response.getBody()[0].getGamePicture());
    }

    @Test
    @Order(9)
    public void testRemoveGameFromWishlist(){
        // Arrnage
        String url = "/wishlist-remove";
        WishlistRequestDto request = new WishlistRequestDto(GAME_NAME, VALID_EMAIL);

        // Act
        ResponseEntity<String> response = client.exchange(url, HttpMethod.DELETE, new HttpEntity<>(request), String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        //assertNull(wishlistLinkRepo.f)
    }

    @Test
    @Order(10)
    public void testGetAllGamesFromEmptyWishlist(){
        // Arrange
        String url = "/wishlist-get-all/" + VALID_EMAIL;

        // Act
        ResponseEntity<WishlistResponseDto[]> response = client.getForEntity(url, WishlistResponseDto[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().length);
    }



 
        
}
