package ca.mcgill.ecse321.GameOn.service;

import ca.mcgill.ecse321.GameOn.model.Review;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Manager;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.repository.ReviewRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepoMock;

    @Mock
    private GameRepository gameRepoMock;

    @InjectMocks
    private ReviewService reviewService;

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
    private static final int VALID_CARD_NUM = 123;
    private static final Date VALID_CARD_DATE = Date.valueOf("2025-09-02");
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr";
    //Also needs cart as parameter, cart does not need parameters

    //Attributes for Review Object
    private static final String VALID_REVIEW_DESCRIPTION = "Great game!";
    private static final int VALID_STARS = 5;
    private static final int VALID_LIKES = 10;
    private static final int VALID_DISLIKES = 2;
    //Needs customer as parameter
    //Needs manager as parameter

    //Extra things to test
    private static final int VALID_REVIEW_ID = 1;
    private static final String VALID_REPLY = "Thank you for your review!";


    //Invalid attributes for review object
    private static final String INVALID_DESCRIPTION = "";
    private static final int INVALID_STARS = 6;
    private static final int INVALID_LIKES = -1;
    private static final int INVALID_DISLIKES = -1;
    private static final String INVALID_GAME_NAME = "wrongGame";



    /**
     * Tests retrieving all reviews for a valid game.
     *
     * @return The list of reviews associated with the specified game.
     * @author Mathieu Allaire
     */
    @Test
    public void testGetAllReviewsForValidGame() {
        // Arrange
        Category category = new Category(VALID_CATEGORY_NAME);
        Game game = new Game(VALID_PICTURE,VALID_GAME_NAME,VALID_GAME_DESCRIPTION,VALID_PRICE,VALID_QUANTITY,category);
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();
        Review review = new Review(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        for (Review review1 : reviews) {
            game.addReview(review1);
        }


        when(gameRepoMock.findGameByName(VALID_GAME_NAME)).thenReturn(game);

        // Act
        List<Review> returnedReviews = reviewService.getAllReviewsforGame(VALID_GAME_NAME);

        // Assert
        assertNotNull(returnedReviews);
        assertEquals(1, returnedReviews.size());
        assertEquals(review, returnedReviews.get(0));
    }

    /**
     * Tests error handling when trying to retrieve reviews for an invalid game.
     *
     * @return An exception indicating that the game does not exist.
     * @author Mathieu Allaire
     */
    @Test
    public void testGetAllReviewsForInvalidGame() {
        // Arrange
        when(gameRepoMock.findGameByName(INVALID_GAME_NAME)).thenReturn(null);

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.getAllReviewsforGame(INVALID_GAME_NAME);
        });
        assertEquals("Game does not exist", ex.getMessage());
    }

    /**
     * Tests posting a valid review with all required fields.
     *
     * @return The newly created review with the specified details.
     * @author Mathieu Allaire
     */
    @Test
    public void testPostValidReview() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();
        when(reviewRepoMock.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Review review = reviewService.postReview(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);

        // Assert
        assertNotNull(review);
        assertEquals(VALID_REVIEW_DESCRIPTION, review.getDescription());
        assertEquals(VALID_STARS, review.getStars());
        assertEquals(VALID_LIKES, review.getLikes());
        assertEquals(VALID_DISLIKES, review.getDislikes());
        assertEquals(customer, review.getReviewAuthor());
        assertEquals(manager, review.getManager());
    }

    /**
     * Tests posting a review with an empty description, expecting an exception.
     *
     * @return An exception indicating that the description is empty.
     * @author Mathieu Allaire
     */
    @Test
    public void testPostInvalidReviewWithEmptyDescription() {
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.postReview(INVALID_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);
        });
        assertEquals("The review has an empty description", ex.getMessage());
    }

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
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
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
        // Arrange
        Cart cart = new Cart();
        Customer customer = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,cart);
        Manager manager = new Manager();
        Review review = new Review(VALID_REVIEW_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, customer, manager);

        review.setId(VALID_REVIEW_ID);
        when(reviewRepoMock.findReviewById(VALID_REVIEW_ID)).thenReturn(null);

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
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
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.addReply(VALID_REVIEW_ID, INVALID_DESCRIPTION);
        });
        assertEquals("The reply has an empty description", ex.getMessage());
    }
}
