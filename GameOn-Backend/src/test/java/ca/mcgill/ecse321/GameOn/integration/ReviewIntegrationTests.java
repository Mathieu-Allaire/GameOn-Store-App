package ca.mcgill.ecse321.GameOn.integration;

import ca.mcgill.ecse321.GameOn.model.*;
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


import ca.mcgill.ecse321.GameOn.dto.ReviewDto;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.repository.ReviewRepository;

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

    //Attributes for Customer Object
    private static final int VALID_CARD_NUM = 123;
    private static final Date VALID_CARD_DATE = Date.valueOf("2025-09-02");
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr";
    private static final Cart VALID_CART = new Cart();
    public Customer VALID_CUSTOMER = new Customer(VALID_CARD_NUM,VALID_CARD_DATE,VALID_BILLING_ADDRESS,VALID_CART);


    //Manager
    private static final Manager VALID_MANAGER = new Manager();

    //Attributes for Review DTO
    private static final String VALID_DESCRIPTION = "Great game!";
    private static final int VALID_STARS = 5;
    private static final int VALID_LIKES = 10;
    private static final int VALID_DISLIKES = 2;

    //Extra things to test
    private static final String VALID_REPLY = "Thank you for your review!";


    @AfterAll
    public void clearDatabase() {
        reviewRepo.deleteAll();
    }


    @Test
    @Order(1)
    public void testCreateValidReview() {
        // Arrange
        ReviewDto review = new ReviewDto(VALID_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, null , VALID_CUSTOMER, VALID_MANAGER);

        // Act
        ResponseEntity<ReviewDto> response = client.postForEntity("/reviews", review, ReviewDto.class);

        // Assert response is not null
        assertNull(response);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ReviewDto createdReview = response.getBody();
        assertNotNull(createdReview);
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_STARS, createdReview.getStars());
        assertEquals(VALID_LIKES, createdReview.getLikes());
        assertEquals(VALID_DISLIKES, createdReview.getDislikes());
        assertEquals(VALID_CUSTOMER.getId(), createdReview.getCustomer().getId());
        assertEquals(VALID_MANAGER.getId(), createdReview.getManager().getId());
    }

    @Test
    @Order(2)
    public void testReadValidReview() {
        // Arrange
        int reviewId = 1; // Assume review with ID 1 exists after creation

        // Act
        ResponseEntity<ReviewDto> response = client.getForEntity("/reviews/" + reviewId, ReviewDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ReviewDto createdReview = response.getBody();
        assertNotNull(createdReview);
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_STARS, createdReview.getStars());
        assertEquals(VALID_LIKES, createdReview.getLikes());
        assertEquals(VALID_DISLIKES, createdReview.getDislikes());
        assertEquals(VALID_REPLY, createdReview.getReply());
        assertEquals(VALID_CUSTOMER.getId(), createdReview.getCustomer().getId());
        assertEquals(VALID_MANAGER.getId(), createdReview.getManager().getId());
    }

    @Test
    @Order(3)
    public void testUpdateReviewLikes() {
        // Arrange
        int reviewId = 1; // Assume review with ID 1 exists
        ReviewDto updateLikes = new ReviewDto();
        updateLikes.setLikes(20);

        // Act
        client.put("/reviews/" + reviewId, updateLikes);
        ResponseEntity<ReviewDto> response = client.getForEntity("/reviews/" + reviewId, ReviewDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(20, updatedReview.getLikes());
    }

    @Test
    @Order(4)
    public void testUpdateReviewDislikes() {
        // Arrange
        int reviewId = 1; // Assume review with ID 1 exists
        ReviewDto updateDislikes = new ReviewDto();
        updateDislikes.setDislikes(10);

        // Act
        client.put("/reviews/" + reviewId, updateDislikes);
        ResponseEntity<ReviewDto> response = client.getForEntity("/reviews/" + reviewId, ReviewDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(10, updatedReview.getDislikes());
    }

    @Test
    @Order(5)
    public void testUpdateReviewStars() {
        // Arrange
        int reviewId = 1; // Assume review with ID 1 exists
        ReviewDto updateStars = new ReviewDto();
        updateStars.setStars(0);

        // Act
        client.put("/reviews/" + reviewId, updateStars);
        ResponseEntity<ReviewDto> response = client.getForEntity("/reviews/" + reviewId, ReviewDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(0, updatedReview.getStars());
    }

    @Test
    @Order(6)
    public void testUpdateReviewReply() {
        // Arrange
        int reviewId = 1; // Assume review with ID 1 exists
        ReviewDto updateReply = new ReviewDto();
        updateReply.setReply(VALID_REPLY);

        // Act
        client.put("/reviews/" + reviewId, updateReply);
        ResponseEntity<ReviewDto> response = client.getForEntity("/reviews/" + reviewId, ReviewDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(VALID_REPLY, updatedReview.getReply());

    }



}
