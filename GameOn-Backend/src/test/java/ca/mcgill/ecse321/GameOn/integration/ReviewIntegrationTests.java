package ca.mcgill.ecse321.GameOn.integration;
import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.repository.*;
import ca.mcgill.ecse321.GameOn.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.GameOn.service.AccountService;
import ca.mcgill.ecse321.GameOn.dto.ReviewDto;
import ca.mcgill.ecse321.GameOn.model.Customer;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ReviewService reviewService;

    //Attributes for Customer Object
    private static final String VALID_CUSTOMER_EMAIL = "hello@gmail.com";
    private static final String VALID_CUSTOMER_NAME = "Hel Lo";
    private static final String VALID_CUSTOMER_PASSWORD = "123456789";
    private static final int VALID_CARD_NUM = 123;
    private static final Date VALID_CARD_DATE = Date.valueOf("2025-09-02");
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr";

    //Attributes for Manager Object
    private static final String VALID_MANAGER_EMAIL = "yeeter@gmail.com";
    private static final String VALID_MANAGER_NAME = "Yeeter the Second";

    //Attributes for Review DTO
    private static final String VALID_DESCRIPTION = "Great game!";
    private static final int VALID_STARS = 5;
    private static final int VALID_LIKES = 10;
    private static final int VALID_DISLIKES = 2;

    private static final String VALID_REPLY = "Thanks!";
    public static int REVIEW_ID = 1;


    @AfterAll
    public void clearDatabase() {
        for (Customer customer : customerRepo.findAll()) {
            customer.setCart(null); // Break the relationship by setting the cart reference to null
            customerRepo.save(customer); // Save changes to persist the update
        }
        reviewRepo.deleteAll();
        personRepo.deleteAll();
        cartRepo.deleteAll();
        customerRepo.deleteAll();
        managerRepo.deleteAll();
    }


    @Test
    @Order(1)
    public void testCreateValidReview() throws JsonProcessingException {
        Person createdCustomer = accountService.createCustomer(VALID_CUSTOMER_EMAIL,VALID_CUSTOMER_NAME,VALID_CUSTOMER_PASSWORD,VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS);
        Person createdManager = accountService.createManager(VALID_MANAGER_EMAIL,VALID_MANAGER_NAME);
        Customer customer = (Customer) createdCustomer.getRole(0);
        Manager manager = (Manager) createdManager.getRole(0);

        // Arrange
        ReviewDto review = new ReviewDto(REVIEW_ID,VALID_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, null , VALID_CUSTOMER_EMAIL,VALID_MANAGER_EMAIL);

        // Act
        ResponseEntity<ReviewDto> response = client.postForEntity("/reviews", review, ReviewDto.class);


        // Assert response is not null
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ReviewDto createdReview = response.getBody();
        assertNotNull(createdReview);
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_STARS, createdReview.getStars());
        assertEquals(VALID_LIKES, createdReview.getLikes());
        assertEquals(VALID_DISLIKES, createdReview.getDislikes());
    }

    @Test
    @Order(2)
    public void testReadValidReview() {
        Person createdCustomer = accountService.findCustomerByEmail(VALID_CUSTOMER_EMAIL);
        Person createdManager = accountService.findManagerByEmail(VALID_MANAGER_EMAIL);
        Customer customer = (Customer) createdCustomer.getRole(0);
        Manager manager = (Manager) createdManager.getRole(0);

        Review review = reviewService.postReview(VALID_DESCRIPTION,VALID_STARS,VALID_LIKES,VALID_DISLIKES,VALID_CUSTOMER_EMAIL,VALID_MANAGER_EMAIL);
        REVIEW_ID = review.getId();


        // Act
        ResponseEntity<ReviewDto> response = client.getForEntity("/reviews/" + REVIEW_ID, ReviewDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto createdReview = response.getBody();
        assertNotNull(createdReview);
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_STARS, createdReview.getStars());
        assertEquals(VALID_LIKES, createdReview.getLikes());
        assertEquals(VALID_DISLIKES, createdReview.getDislikes());
        assertNull(createdReview.getReply());
    }


    @Test
    @Order(3)
    public void testUpdateReviewLikes() {
        // Arrange
        int reviewId = REVIEW_ID; // Assume review with ID exists
        int updateLikes = 20;

        //Act
        ResponseEntity<ReviewDto> response = client.postForEntity(
                "/reviews/" + reviewId + "/updateDislikes",
                updateLikes,
                ReviewDto.class
        );
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(updateLikes, updatedReview.getDislikes());
    }


    @Test
    @Order(4)
    public void testUpdateReviewDislikes() {
        // Arrange
        int reviewId = REVIEW_ID; // Assume review with ID 1 exists
        int updateDislikes = 40;

        //Act
        ResponseEntity<ReviewDto> response = client.postForEntity(
                "/reviews/" + reviewId + "/updateDislikes",
                updateDislikes,
                ReviewDto.class
        );
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(updateDislikes, updatedReview.getDislikes());
    }

    @Test
    @Order(5)
    public void testUpdateReviewStars() {
        // Arrange
        int reviewId = REVIEW_ID; // Assume review with ID 1 exists
        int updatedStars = 2;

        //Act
        ResponseEntity<ReviewDto> response = client.postForEntity(
                "/reviews/" + reviewId + "/updateStars",
                updatedStars,
                ReviewDto.class
        );
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(updatedStars, updatedReview.getStars());
    }

    @Test
    @Order(6)
    public void testUpdateReviewReply() {
        // Arrange
        int reviewId = REVIEW_ID; // Assume review with ID 1 exists

        // Act
        ResponseEntity<ReviewDto> response = client.postForEntity(
                "/reviews/" + reviewId + "/reply",
                VALID_REPLY, // Correct request body
                ReviewDto.class
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(VALID_REPLY, updatedReview.getReply());

    }



}
