package ca.mcgill.ecse321.GameOn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.boot.test.context.SpringBootTest;


import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.WishlistLinkRepository;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.model.WishlistLink.Key;
import ca.mcgill.ecse321.GameOn.model.Category;

/**
 * This are the tests for the WishListLink
 *
 * @author Camilo Berdugo
 */


@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class WishlistServiceTests {
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

    //Attributes game
    private static final String VALID_GAME_NAME = "COD";
    private static final String VALID_PICTURE = "url=fwbyuevefiuefueb";
    private static final String VALID_DESCRIPTION = "Gun game";
    private static final Integer VALID_PRICE = 59;
    private static final Integer VALID_QUANTITY = 12;
    private static final Category VALID_CATEGORY = new Category("WAR");



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

        //Verify the game
        assertEquals(createdWishlist.getKey().getWishlistGames().getName(), VALID_GAME_NAME);
        assertEquals(createdWishlist.getKey().getWishlistGames().getDescription(), VALID_DESCRIPTION);
        assertEquals(createdWishlist.getKey().getWishlistGames().getPicture(), VALID_PICTURE);
        assertEquals(createdWishlist.getKey().getWishlistGames().getPrice(), VALID_PRICE);
        assertEquals(createdWishlist.getKey().getWishlistGames().getQuantity(), VALID_QUANTITY);
        assertEquals(createdWishlist.getKey().getWishlistGames().getCategory().getName(), "WAR");
        //Verify that a game was added to the customer role
        assertEquals(customerRole.getCustomerWish().size(), 1);
        
    }
    @SuppressWarnings("null")
    @Test
    public void testInvalidCustomerAddGame(){
        
        //Case where there is no customer with that email
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(null);
        
        try {
            //Act 
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("Customer not found", e.getMessage()); //Make sure that the system outputs an error message
        }
        
    }

    @SuppressWarnings("null")
    @Test
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
        when(wishlistLinkRepository.findWishlistLinkByKey(new WishlistLink.Key(game, customerRole))).thenReturn(existingWishlist);
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(game);

        //Case when the game is already in the wishlist of a customer 
        try {
        //Act 
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("The Game is already at the customer wishlist", e.getMessage()); //Make sure that the system outputs an error message
        }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testAddGameInvalidGame(){
         //Create a customer
         Cart cart = new Cart();
         Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
         Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
         String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
         bob.setPassword(encryptedPassword); // the password save into the system is encrypted

        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(gameRepository.findGameByName(VALID_GAME_NAME)).thenReturn(null);

        //Case when the game does not exist in the system , so it cannot be saved in the wishlist of a customer
        try {
        //Act 
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("The game does not exist", e.getMessage());
        }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testAddGameNull(){
        try {
        //Act : Case when the gameName is null
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(VALID_EMAIL, null);
        } catch (Exception e) {
        assertEquals("Game name cannot be null", e.getMessage()); //Make sure that the system outputs an error message
        }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testAddGameEmailNull(){
        try {
        //Act : Case when the customerEmail is null
        WishlistLink createdWishlist = wishlistService.addGameToWishlist(null, VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());//Make sure that the system outputs an error message
        }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testAddGameEmailSpaces(){
        try {
        //Act : the given email is invalid (has spaces)
        WishlistLink createdWishlist = wishlistService.addGameToWishlist("camilo@mcgill.co  m", VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());//Make sure that the system outputs an error message
        }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testAddGameEmailNoPoint(){
        try {
        //Act : the email is invalid (has no point)
        WishlistLink createdWishlist = wishlistService.addGameToWishlist("camilo@mcgillcom", VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());//Make sure that the system outputs an error message
        }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testAddGameEmailEmpty(){
        try {
        //Act : the email is empty
        WishlistLink createdWishlist = wishlistService.addGameToWishlist("", VALID_GAME_NAME);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());//Make sure that the system outputs an error message
        }
        
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
    public void testInvalidRemoveGameWishListEmailNull(){
        try {
            //Act 
        //Case when the email is null
        Boolean deleted = wishlistService.removeGameFromWishlist(null, VALID_GAME_NAME);
            } catch (Exception e) {
            assertEquals("Customer email cannot be null", e.getMessage());
            }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testInvalidRemoveGameWishListGameNameNull(){
        try {
            //Act 
        //Case when the gameName is null
        Boolean deleted = wishlistService.removeGameFromWishlist(VALID_EMAIL, null);
            } catch (Exception e) {
            assertEquals("Game name cannot be null", e.getMessage());
            }
        
    }

    @SuppressWarnings("null")
    @Test
    public void testInvalidRemoveGameWishListNotFoundCustomer(){
        try {
            //Act 
        //Case when there is no customer with that email
        Boolean deleted = wishlistService.removeGameFromWishlist(VALID_EMAIL, VALID_GAME_NAME);
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(null);
            } catch (Exception e) {
            assertEquals("Customer not found", e.getMessage());
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

    @SuppressWarnings("null")
    @Test
    public void testInvalidFindWishListEmailNull(){
        try {

            //Act 
            //Case when email is null
            WishlistLink createdWishlist = wishlistService.findWishlistLink(null, VALID_GAME_NAME);
            } catch (Exception e) {
            assertEquals("Customer email cannot be null", e.getMessage());
            }
    
    }

    @SuppressWarnings("null")
    @Test
    public void testInvalidFindWishListGameNameNull(){
        try {

            //Act 
            //Case when gameName is null
            WishlistLink createdWishlist = wishlistService.findWishlistLink(VALID_EMAIL, null);
            } catch (Exception e) {
            assertEquals("Game name cannot be null", e.getMessage());
            }
    
    }

    @SuppressWarnings("null")
    @Test
    public void testInvalidFindWishListNoPersonFound(){
        try {
            //Act 
            //Case when person is not found with the given email
            when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(null);
            WishlistLink createdWishlist = wishlistService.findWishlistLink(VALID_EMAIL, VALID_GAME_NAME);
            } catch (Exception e) {
            assertEquals("Person not found", e.getMessage());
            }
    
    }

    @SuppressWarnings("null")
    @Test
    public void TestGetAllGamesFromWishListLink(){
       //Create a game
       Game game = new Game(VALID_PICTURE, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
       Game secondGame = new Game(VALID_PICTURE, "Game2", VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

       //Create a cart and customer
       Cart cart = new Cart();
       Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);

       //Create Wishlist
       WishlistLink existingWishlist = new WishlistLink(new Key(game, customerRole));
       WishlistLink secondExistingWishlist = new WishlistLink(new Key(secondGame, customerRole));
       when(wishlistLinkRepository.findAll()).thenReturn(List.of(existingWishlist, secondExistingWishlist));
       
    
       //In this case Bob has 2 games in his wishlist
       Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
       String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
       bob.setPassword(encryptedPassword); // the password save into the system is encrypted
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        
        //Act 
        List<WishlistLink> gamesWishList = wishlistService.getAllGamesFromWishlist(VALID_EMAIL);

        //Assert
        //Verify the customer
        assertNotNull(gamesWishList);
        assertEquals(gamesWishList.size(), 2); // Verify that bob has 2 games
        assertEquals(gamesWishList.get(0).getKey().getWishlistGames().getName(), VALID_GAME_NAME); // Verify the name of game 1 
        assertEquals(gamesWishList.get(1).getKey().getWishlistGames().getName(), "Game2"); // verify the name of game 2
        
    }



    



}

