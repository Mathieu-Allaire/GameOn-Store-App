package ca.mcgill.ecse321.GameOn.service;

import ca.mcgill.ecse321.GameOn.exception.GameOnException;
import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.repository.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.invocation.InvocationOnMock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepoMock;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private GameRepository gameRepoMock;

    @InjectMocks
    private ReviewService reviewService;

    @InjectMocks
    private AccountService accountService;

    //Attributes for Game object
    private static final String VALID_PICTURE = "url";
    private static final String VALID_GAME_NAME = "testGame";
    private static final String VALID_GAME_DESCRIPTION = "a game about blablabla";
    private static final int VALID_PRICE = 60;
    private static final int VALID_QUANTITY = 100;
    //Also needs category as parameter

    //Attribute for Category constructor
    private static final String VALID_CATEGORY_NAME = "testCategory";

    //Attributes for Customer Object
    private static final String VALID_CUSTOMER_EMAIL = "bob@gmail.com";
    private static final String VALID_CUSTOMER_NAME = "Hel Lo";
    private static final String VALID_CUSTOMER_PASSWORD = "123456789";
    private static final int VALID_CARD_NUM = 123;
    private static final Date VALID_CARD_DATE = Date.valueOf("2025-09-02");
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr";

    //Attributes for Manager Object
    private static final String VALID_MANAGER_EMAIL = "yeeter@gmail.com";
    private static final String VALID_MANAGER_NAME = "Yeeter the Second";

    //Attributes for Review Object
    private static final String VALID_REVIEW_DESCRIPTION = "Great game!";
    private static final int VALID_STARS = 5;
    private static final int VALID_LIKES = 10;
    private static final int VALID_DISLIKES = 2;

    private static int VALID_CUSTOMER_ID = 10;
    private static int VALID_MANAGER_ID = 20;



    //Extra things to test
    private static final int VALID_REVIEW_ID = 1;
    private static final String VALID_REPLY = "Thank you for your review!";


    //Invalid attributes for review object
    private static final String INVALID_DESCRIPTION = "";
    private static final int INVALID_STARS = 6;
    private static final int INVALID_LIKES = -1;
    private static final int INVALID_DISLIKES = -1;
    private static final String INVALID_GAME_NAME_EMPTY = "";
    private static final String INVALID_GAME_NAME_WRONG = "wrongGame";



    /**
     * Tests retrieving all reviews for a valid game.
     *
     * @return The list of reviews associated with the specified game.
     * @throws IllegalArgumentException if the game does not exist.
     * @author Mathieu Allaire
     */
    // @Test
    // public void testGetAllReviewsForValidGame() {
    //     // Arrange
    //     Category category = new Category(VALID_CATEGORY_NAME);
    //     Game game = new Game(VALID_PICTURE,VALID_GAME_NAME,VALID_GAME_DESCRIPTION,VALID_PRICE,VALID_QUANTITY,category);
    //     Cart cart = new Cart();
    //     Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
    //     Manager manager = new Manager();
    //     Review review = new Review(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);
    //     List<Review> reviews = new ArrayList<>();
    //     reviews.add(review);

    //     for (Review reviewInList : reviews) {
    //         game.addReview(reviewInList);
    //     }

    //     when(gameRepoMock.findGameByName(VALID_GAME_NAME)).thenReturn(game);

    //     // Act
    //     List<Review> returnedReviews = reviewService.getAllReviewsforGame(VALID_GAME_NAME);

    //     // Assert
    //     assertNotNull(returnedReviews);
    //     assertEquals(1, returnedReviews.size());
    //     assertEquals(review, returnedReviews.get(0));
    // }

    /**
     * Tests error handling when trying to retrieve reviews for an invalid game.
     *
     * @throws GameOnException An exception indicating that the game does not exist.
     * @author Mathieu Allaire
     */
    // @Test
    // public void testGetAllReviewsForInvalidGame() {
    //     // Arrange

    //     when(gameRepoMock.findGameByName(INVALID_GAME_NAME_WRONG)).thenReturn(null);

    //     // Assert
    //     GameOnException ex = assertThrows(GameOnException.class, () -> {
    //         reviewService.getAllReviewsforGame(INVALID_GAME_NAME_WRONG);
    //     });
    //     assertEquals("Game does not exist", ex.getMessage());
    // }

    /**
     * Tests error handling when trying to retrieve reviews for an invalid name game.
     *
     * @throws GameOnException An exception indicating that the name of the game is invalid.
     * @author Mathieu Allaire
     */
    // @Test
    // public void testGetAllReviewsForInvalidNameofGame() {
    //     // Arrange

    //     when(gameRepoMock.findGameByName(INVALID_GAME_NAME_EMPTY)).thenReturn(null);

    //     // Assert
    //     GameOnException ex = assertThrows(GameOnException.class, () -> {
    //         reviewService.getAllReviewsforGame(INVALID_GAME_NAME_EMPTY);
    //     });
    //     assertEquals("The name is invalid", ex.getMessage());
    // }



    /**
     * Tests posting a valid review with all required fields.
     *
     * @return The newly created review with the specified details.
     * @throws IllegalArgumentException if the review data is invalid.
     * @author Mathieu Allaire
     */
    /*
    @Test
    public void testPostValidReview() {
        // Arrange
        when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(cartRepository.save(any(Cart.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(personRepository.findPersonByEmail(VALID_CUSTOMER_EMAIL)).thenReturn(null);

        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_CARD_DATE, VALID_BILLING_ADDRESS, cart);
        Person bob = new Person(VALID_CUSTOMER_EMAIL, VALID_CUSTOMER_NAME, VALID_CUSTOMER_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_CUSTOMER_PASSWORD);
        bob.setPassword(encryptedPassword); // this simulates the create customer

        Person createdCustomer = accountService.createCustomer(VALID_CUSTOMER_EMAIL,VALID_CUSTOMER_NAME,VALID_CUSTOMER_PASSWORD,VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS);
        Person createdManager = accountService.createManager(VALID_MANAGER_EMAIL,VALID_MANAGER_NAME);
        assertNotNull(createdCustomer);
        assertEquals(VALID_CUSTOMER_EMAIL, createdCustomer.getEmail());
        assertEquals(VALID_CUSTOMER_NAME, createdCustomer.getName());
        System.out.println(createdCustomer.getEmail());

        // Act
        Review review = reviewService.postReview(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, createdCustomer.getEmail(), createdManager.getEmail());

        // Assert
        assertNotNull(review);
        assertEquals(VALID_REVIEW_DESCRIPTION, review.getDescription());
        assertEquals(VALID_STARS, review.getStars());
        assertEquals(VALID_LIKES, review.getLikes());
        assertEquals(VALID_DISLIKES, review.getDislikes());
    }
    */


    /**
     * Tests posting a review with an empty description, expecting an exception.
     *
     * @throws GameOnException An exception indicating that the description is empty.
     * @author Mathieu Allaire
     */
    @Test
    public void testPostInvalidReviewWithEmptyDescription() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();

        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.postReview(INVALID_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, VALID_CUSTOMER_EMAIL, VALID_MANAGER_EMAIL);
        });
        assertEquals("The review has an empty description", ex.getMessage());
    }


    /**
     * Tests posting a review with an invalid number of stars, expecting an exception.
     *
     * @throws GameOnException An exception indicating that the number of stars is invalid.
     * @author Mathieu Allaire
     */
    @Test
    public void testPostInvalidReviewWithInvalidStars() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();

        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.postReview(VALID_REVIEW_DESCRIPTION, INVALID_STARS, VALID_LIKES, VALID_DISLIKES, VALID_CUSTOMER_EMAIL, VALID_MANAGER_EMAIL);
        });
        assertEquals("The number of stars must be between 0 and 5", ex.getMessage());

    }

    /**
     * Tests posting a review with an invalid number of likes, expecting an exception.
     *
     * @throws GameOnException An exception indicating that the number of likes must be non-negative.
     * @author Mathieu Allaire
     */
    @Test
    public void testPostInvalidReviewWithInvalidLikes() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();

        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.postReview(VALID_REVIEW_DESCRIPTION, VALID_STARS, INVALID_LIKES, VALID_DISLIKES, VALID_CUSTOMER_EMAIL, VALID_MANAGER_EMAIL);
        });
        assertEquals("The number of likes must be non-negative", ex.getMessage());
    }

    /**
     * Tests posting a review with an invalid number of dislikes, expecting an exception.
     *
     * @throws GameOnException An exception indicating that the number of dislikes must be non-negative.
     * @author Mathieu Allaire
     */
    @Test
    public void testPostInvalidReviewWithInvalidDislikes() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();

        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.postReview(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, INVALID_DISLIKES, VALID_CUSTOMER_EMAIL, VALID_MANAGER_EMAIL);
        });
        assertEquals("The number of dislikes must be non-negative", ex.getMessage());
    }

    /**
     * Tests posting a review with an invalid author, expecting an exception.
     *
     * @throws GameOnException An exception indicating that the author exist.
     * @author Mathieu Allaire
     */
    @Test
    public void testPostInvalidReviewWithInvalidAuthor() {
        // Arrange
        when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(cartRepository.save(any(Cart.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));


        Person createdCustomer = accountService.createCustomer(VALID_CUSTOMER_EMAIL,VALID_CUSTOMER_NAME,VALID_CUSTOMER_PASSWORD,VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS);
        Person createdManager = accountService.createManager(VALID_MANAGER_EMAIL,VALID_MANAGER_NAME);
        Customer customer = (Customer) createdCustomer.getRole(0);
        customer.setId(null);
        Manager manager = (Manager) createdManager.getRole(0);
        manager.setId((long)VALID_MANAGER_ID);

        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(customer);
        when(managerRepository.findManagerById(VALID_MANAGER_ID)).thenReturn(manager);


        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.postReview(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, "Hello", VALID_MANAGER_EMAIL);
        });
        assertEquals("Customer email is invalid", ex.getMessage());
    }


    /**
     * Tests posting a review with an invalid manager, expecting an exception.
     *
     * @throws GameOnException An exception indicating that the manager must exist.
     * @author Mathieu Allaire
     */
    /*
    @Test
    public void testPostInvalidReviewWithInvalidManager() {
        // Arrange
        when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(cartRepository.save(any(Cart.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));


        Person createdCustomer = accountService.createCustomer(VALID_CUSTOMER_EMAIL,VALID_CUSTOMER_NAME,VALID_CUSTOMER_PASSWORD,VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS);

        Person createdManager = accountService.createManager(VALID_MANAGER_EMAIL,VALID_MANAGER_NAME);
        Customer customer = (Customer) createdCustomer.getRole(0);
        customer.setId((long)VALID_CUSTOMER_ID);
        Manager manager = (Manager) createdManager.getRole(0);
        manager.setId(null);

        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(customer);


        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.postReview(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, VALID_CUSTOMER_EMAIL, "Bye");
        });
        assertEquals("Manager not found", ex.getMessage());
    }
    */
    /**
     * Tests finding a review by a valid ID.
     *
     * @return The review with the specified ID.
     * @author Mathieu Allaire
     */
    @Test
    public void testFindReviewById() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();
        Review review = new Review(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);
        review.setId(VALID_REVIEW_ID);
        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(review);

        // Act
        Review foundReview = reviewService.findReviewById(VALID_REVIEW_ID);

        // Assert
        assertNotNull(foundReview);
        assertEquals(VALID_REVIEW_ID, foundReview.getId());
    }

    /**
     * Tests finding a review with an invalid ID, expecting an exception.
     *
     * @return An exception indicating that the review does not exist.
     * @author Mathieu Allaire
     */
    @Test
    public void testFindReviewByInvalidId() {
        // Arrange
        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(null);

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.findReviewById(VALID_REVIEW_ID);
        });
        assertEquals("There is no review with ID " + VALID_REVIEW_ID + ".", ex.getMessage());
    }

    /**
     * Tests liking an existing review, incrementing the like count.
     *
     * @return The updated review with an incremented like count.
     * @author Mathieu Allaire
     */
    @Test
    public void testLikeReview() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();
        Review review = new Review(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);

        review.setId(VALID_REVIEW_ID);
        review.setLikes(VALID_LIKES);

        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(review);
        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        reviewService.likeReview(VALID_REVIEW_ID);

        // Assert
        assertEquals(VALID_LIKES + 1, review.getLikes());
    }

    /**
     * Tests liking a non-existent review, expecting an exception.
     *
     * @return An exception indicating that the review does not exist.
     * @author Mathieu Allaire
     */
    @Test
    public void testLikeNonExistentReview() {
        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(null);

        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.likeReview(VALID_REVIEW_ID);
        });
        assertEquals("There is no review with ID " + VALID_REVIEW_ID + ".", ex.getMessage());
    }

    /**
     * Tests adding a reply to an existing review.
     *
     * @return The updated review with the new reply added.
     * @author Mathieu Allaire
     */
    @Test
    public void testAddReplyToValidReview() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();
        Review review = new Review(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);
        review.setId(VALID_REVIEW_ID);

        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(review);
        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        reviewService.addReply(VALID_REVIEW_ID, VALID_REPLY);

        // Assert
        assertEquals(VALID_REPLY, review.getReply());
    }

    /**
     * Tests adding an empty reply description to a review, expecting an exception.
     *
     * @return An exception indicating that the reply description is empty.
     * @author Mathieu Allaire
     */
    @Test
    public void testAddReplyWithEmptyDescription() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();
        Review review = new Review(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);

        review.setId(VALID_REVIEW_ID);
        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(review);

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.addReply(VALID_REVIEW_ID, INVALID_DESCRIPTION);
        });
        assertEquals("The reply has an empty description", ex.getMessage());
    }

    @Test
    public void testAddReplyWithInvalidId() {

        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(null);

        // Assert
        GameOnException ex = assertThrows(GameOnException.class, () -> {
            reviewService.addReply(VALID_REVIEW_ID, VALID_REPLY);
        });
        assertEquals("There is no review with ID " + VALID_REVIEW_ID + ".", ex.getMessage());
    }
}
