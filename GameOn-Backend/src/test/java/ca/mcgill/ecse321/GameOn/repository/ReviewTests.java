package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Review;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Manager;

@SpringBootTest
public class ReviewTests {
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ManagerRepository managerRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        reviewRepo.deleteAll();
        customerRepo.deleteAll();
        managerRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadReview() {
        // Arrange

        // Review Attributes
        String aDescription = "WOOOOOW";
        int aStars = 5;
        int aLikes = 0;
        int aDislikes = 0;
        String aReply = "THANKS";

        // Customer attributes
        int aCustomerCardNum = 1234;
        long millis = System.currentTimeMillis();
        Date aCustomerDate = new Date(millis);
        String aCustomerAddress = "123 main st";

        // Create Customer
        Customer aCustomer = new Customer(aCustomerCardNum, aCustomerDate, aCustomerAddress);

        // Create Manager
        Manager aManager = new Manager();

        // Create Review
        Review aReview = new Review(aDescription, aStars, aLikes, aDislikes, aCustomer, aManager);
        aReview.setReply(aReply);
        aReview = reviewRepo.save(aReview);

        int id = aReview.getId();

        // Act
        Review reviewDB = reviewRepo.findReviewById(id);

        // Assert
        assertNotNull(reviewDB);
        assertNotNull(reviewDB.getReviewAuthor());
        assertEquals(reviewDB.getDescription(), aDescription);
        assertEquals(reviewDB.getStars(), aStars);
        assertEquals(reviewDB.getLikes(), aLikes);
        assertEquals(reviewDB.getDislikes(), aDislikes);
        assertEquals(reviewDB.getReply(), aReply);
    }
}