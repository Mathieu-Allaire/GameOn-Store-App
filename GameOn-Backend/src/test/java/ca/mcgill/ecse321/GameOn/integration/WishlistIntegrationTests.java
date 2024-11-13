package ca.mcgill.ecse321.GameOn.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.GameOn.repository.WishlistLinkRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;
import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.dto.WishlistRequestDto;

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

    @AfterAll
    public void clearDatabase() {
        
    }
    
    @Test
    @Order(1)
    public void TestAddValidGameToWishlist() {
        
    }
        
}
