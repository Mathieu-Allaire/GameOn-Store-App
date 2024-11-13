package ca.mcgill.ecse321.GameOn.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

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

import ca.mcgill.ecse321.GameOn.dto.CustomerRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CustomerResponseDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.GameOn.dto.ReviewRequestDto;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;
import ca.mcgill.ecse321.GameOn.repository.ManagerRepository;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import ca.mcgill.ecse321.GameOn.repository.ReviewRepository;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private CustomerRepository  customerRepo;
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private PersonRepository personRepo;

    //Attributes for customer
    private static final String VALID_EMAIL = "bob@gmail.com"; // no spaces,contain @ and . 
    private static final String VALID_NAME = "Bob"; // at least one letter
    private static final String VALID_PASSWORD = "bob123456789"; // bigger than 8 characters
    private static final int VALID_CARD_NUM = 123; // larger than 0
    private static final Date VALID_DATE = Date.valueOf("2025-09-02"); // needs to be a date after today's date
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr"; // at least one character
    private Customer aCustomer;

    //Attributes for manager
    private static final String VALID_EMAIL_MANAGER = "manager@manager.com";
    private static final String VALID_NAME_MANAGER = "Manager";
    private Manager aManager;


    @AfterAll
    public void clearDatabase() {
        reviewRepo.deleteAll();
        personRepo.deleteAll();
        customerRepo.deleteAll();
        managerRepo.deleteAll();
    }


    @Test
    @Order(1)
    public void createValidManagerTest(){
        // Arrange
        String url = "/manager";
        EmployeeRequestDto manager = new EmployeeRequestDto(VALID_EMAIL_MANAGER, VALID_NAME_MANAGER);

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.postForEntity(url, manager, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    @Order(2)
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

/*
    @Test
    @Order(1)
    public void testCreateValidReview() {
        // Arrange
        ReviewRequestDto review = new ReviewRequestDto(VALID_DESCRIPTION, VALID_STARS, VALID_LIKES, VALID_DISLIKES, null , VALID_CUSTOMER, VALID_MANAGER);

        // Act
        ResponseEntity<ReviewRequestDto> response = client.postForEntity("/reviews", review, ReviewRequestDto.class);

        // Assert response is not null
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ReviewRequestDto createdReview = response.getBody();
        assertNotNull(createdReview);
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_STARS, createdReview.getStars());
        assertEquals(VALID_LIKES, createdReview.getLikes());
        assertEquals(VALID_DISLIKES, createdReview.getDislikes());
        assertEquals(VALID_CUSTOMER.getId(), createdReview.getCustomer().getId());
        assertEquals(VALID_MANAGER.getId(), createdReview.getManager().getId());
    }
/* 
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

*/

}
