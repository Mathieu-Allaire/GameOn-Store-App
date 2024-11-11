package ca.mcgill.ecse321.GameOn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.WishlistLinkRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.model.Category;


@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class WhishlistServiceTests {
    @Mock
    private WishlistLinkRepository wishlistLinkRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private WishlistService wishlistService;

    //Attributes for customer
    private static final String VALID_EMAIL = "bob@gmail.com"; // no spaces,contain @ and . 
    private static final String VALID_NAME = "Bob"; // at least one letter
    private static final String VALID_PASSWORD = "bob123456789"; // bigger than 8 characters
    private static final int VALID_CARD_NUM = 123; // larger than 0
    private static final Date VALID_DATE = Date.valueOf("2025-09-02"); // needs to be a date after today's date
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr"; // at least one character

    //Attributes game1
    private static final String VALID_GAME_NAME = "COD";
    private static final String VALID_PICTURE = "url=fwbyuevefiuefueb";
    private static final String VALID_DESCRIPTION = "Gun game";
    private static final Integer VALID_PRICE = 59;
    private static final Integer VALID_QUANTITY = 12;
    private static final Category VALID_CATEGORY = new Category("WAR");

    //Attributes game2
    private static final String VALID_GAME_NAME2 = "FIFA";
    private static final String VALID_PICTURE2 = "url=fwbyuevefiuefueb";
    private static final String VALID_DESCRIPTION2 = "Soccer game";
    private static final Integer VALID_PRICE2 = 69;
    private static final Integer VALID_QUANTITY2 = 10;
    private static final Category VALID_CATEGORY2 = new Category("SPORTS");

    @SuppressWarnings("null")
    @Test
    public void testAddGame(){
        //Create a customer
        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // the password save into the system is encrypted

        //Create a game
        Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        when(wishlistLinkRepository.save(any(WishlistLink.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(null);
        
        //Act 
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, VALID_GAME_NAME);

        //Assert
        //Verify the customer
        assertNotNull(createdWishlist);
        assertEquals(createdWishlist.getCustomerWish().getBillingAddress(), VALID_BILLING_ADDRESS);
        assertEquals(createdWishlist.getCustomerWish().getCardExpiryDate(), VALID_DATE);
        assertEquals(createdWishlist.getCustomerWish().getCardNum(), VALID_CARD_NUM);
        //Verify the game
        assertEquals(createdWishlist.getWishlistGames().getName(), VALID_GAME_NAME);
        assertEquals(createdWishlist.getWishlistGames().getDescription(), VALID_DESCRIPTION);
        assertEquals(createdWishlist.getWishlistGames().getPicture(), VALID_PICTURE);
        assertEquals(createdWishlist.getWishlistGames().getPrice(), VALID_PRICE);
        assertEquals(createdWishlist.getWishlistGames().getQuantity(), VALID_QUANTITY);
        assertEquals(createdWishlist.getWishlistGames().getCategory().getName(), "WAR");
        //Verify that a game was added to the customer role
        assertEquals(customerRole.getCustomerWish().size(), 1);
        
    }

    public void testInvalidCustomerAddGame(){
        //Create a customer
        Cart cart = new Cart();
        Employee employeeRole = new Employee(true) ;
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, employeeRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // the password save into the system is encrypted

        //Create a game
        Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        try {
            //Act 
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("No customer with this email", e.getMessage());
        }
        
    }

    public void testAlreadyExistingAddGame(){
         //Create a customer
         Cart cart = new Cart();
         Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
         Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
         String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
         bob.setPassword(encryptedPassword); // the password save into the system is encrypted

        //Create a game
        Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

        WishlistLink existingWishlist = new WishlistLink(game, customerRole);
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        when(wishlistLinkRepository.save(any(WishlistLink.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(existingWishlist);

        try {
        //Act 
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("The Game is already at the customer wishlist", e.getMessage());
        }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testGetAllWishList(){
         //Create a customer
         Cart cart = new Cart();
         Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
         Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
         String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
         bob.setPassword(encryptedPassword); // the password save into the system is encrypted
         
         //Create a game
         Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
         
         when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
         when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
         when(wishlistLinkRepository.save(any(WishlistLink.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
         when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
         when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(null);
         
         //Act 
         WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, VALID_GAME_NAME);
        //Act 
        List<Game> allGames = wishlistService.getAllGamesFromWishlist(VALID_EMAIL);

        //Assert
        //Verify the customer
        assertNotNull(allGames);
    }


    @SuppressWarnings("null")
    @Test
    public void testRemoveGameWishList(){
        //Create a game
        Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

        //Create a cart and customer
        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);

        //Create Wishlist
        WishlistLink existingWishlist = new WishlistLink(game, customerRole);

        //Create a customer
        customerRole.addCustomerWish(existingWishlist); // the customer has 1 game in his wishlist
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // the password save into the system is encrypted


        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(existingWishlist);
        
        //Act 
        Boolean deleted = wishlistService.removeGameFromWishlist(VALID_EMAIL, VALID_GAME_NAME);

        //Assert
        //Verify that the game was removed from the wishlist
        assertEquals(deleted, true);
        verify(wishlistLinkRepository, times(1)).delete(existingWishlist);
        assertEquals(customerRole.getCustomerWish().size(), 0);
        
    }
    /** FOR CAMILO 
    @SuppressWarnings("null")
    @Test
    public void testRemoveAllGamesFromWishList(){
                //Create a game
                Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

                //Create a cart and customer
                Cart cart = new Cart();
                Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
        
                //Create Wishlist
                WishlistLink existingWishlist = new WishlistLink(game, customerRole);
        
                //Create a customer
                customerRole.addCustomerWish(existingWishlist); // the customer has 1 game in his wishlist
                Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
                String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
                bob.setPassword(encryptedPassword); // the password save into the system is encrypted
        
        
                when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
                when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
                when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(existingWishlist);
                List<WishlistLink> existingWishlistList = new ArrayList<>();
                existingWishlistList.add(existingWishlist);
                Iterable<WishlistLink> existingWishlistIterator = existingWishlistList;
                lenient().when(wishlistLinkRepository.findAll()).thenReturn(existingWishlistIterator);
                //Act 
                Boolean deleted = wishlistService.removeAllGamesFromWishlist(VALID_EMAIL);
        
                //Assert
                //Verify that the game was removed from the wishlist
                assertEquals(deleted, true);
                verify(wishlistLinkRepository, times(1)).delete(existingWishlist);
                assertEquals(customerRole.getCustomerWish().size(), 0);

    }
                */

    @SuppressWarnings("null")
    @Test
    public void testInvalidRemoveGameWishList(){
       //Create a game
       Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

       //Create a cart and customer
       Cart cart = new Cart();
       Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);

       //Create Wishlist
       WishlistLink existingWishlist = new WishlistLink(game, customerRole);

       //Create a customer
       customerRole.addCustomerWish(existingWishlist); // the customer has 1 game in his wishlist
       Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
       String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
       bob.setPassword(encryptedPassword); // the password save into the system is encrypted


        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(null);
        

        try {
            //Act 
        //Case when there is no game in the wishlist to remove
        Boolean deleted = wishlistService.removeGameFromWishlist(VALID_EMAIL, VALID_GAME_NAME);
            } catch (Exception e) {
            assertEquals("The Game is not in the customer's wishlist", e.getMessage());
            }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testFindWishList(){
       //Create a game
       Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

       //Create a cart and customer
       Cart cart = new Cart();
       Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);

       //Create Wishlist
       WishlistLink existingWishlist = new WishlistLink(game, customerRole);

       //Create a customer
       customerRole.addCustomerWish(existingWishlist); // the customer has 1 game in his wishlist
       Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
       String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
       bob.setPassword(encryptedPassword); // the password save into the system is encrypted
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(existingWishlist);
        
        //Act 
        WishlistLink createdWishlist = wishlistService.findWishlistLink(VALID_EMAIL, VALID_GAME_NAME);

        //Assert
        //Verify the customer
        assertNotNull(createdWishlist);
        assertEquals(createdWishlist.getCustomerWish().getBillingAddress(), VALID_BILLING_ADDRESS);
        assertEquals(createdWishlist.getCustomerWish().getCardExpiryDate(), VALID_DATE);
        assertEquals(createdWishlist.getCustomerWish().getCardNum(), VALID_CARD_NUM);
        //Verify the game
        assertEquals(createdWishlist.getWishlistGames().getName(), VALID_GAME_NAME);
        assertEquals(createdWishlist.getWishlistGames().getDescription(), VALID_DESCRIPTION);
        assertEquals(createdWishlist.getWishlistGames().getPicture(), VALID_PICTURE);
        assertEquals(createdWishlist.getWishlistGames().getPrice(), VALID_PRICE);
        assertEquals(createdWishlist.getWishlistGames().getQuantity(), VALID_QUANTITY);
        assertEquals(createdWishlist.getWishlistGames().getCategory().getName(), "WAR");
    }

    @SuppressWarnings("null")
    @Test
    public void testInvalidFindWishList(){
       //Create a game
       Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

       //Create a cart and customer
       Cart cart = new Cart();
       Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);

       //Create a customer
       Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
       String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
       bob.setPassword(encryptedPassword); // the password save into the system is encrypted
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(null);
        
    
        try {

            //Act 
            //Case when there is not the wanted game in the wishlist 
            WishlistLink createdWishlist = wishlistService.findWishlistLink(VALID_EMAIL, VALID_GAME_NAME);
            } catch (Exception e) {
            assertEquals("The game is not in the wishlist of the client", e.getMessage());
            }
    
    }



    



}

