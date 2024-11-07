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
import ca.mcgill.ecse321.GameOn.model.Cart;

@SpringBootTest
public class ReviewTests {
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private CartRepository cartRepository;

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
        Cart aCart = new Cart();

        // Create and save Customer first
        long millis = System.currentTimeMillis();
        Date aDate = new Date(millis);
        Customer aCustomer = new Customer(111, aDate, "111 mcgill street", null); // Set cart as null initially
        aCustomer = customerRepo.save(aCustomer); // Save Customer first to assign an ID

        // Now link Customer and Cart
        aCart.setCustomer(aCustomer); // Set the Customer in Cart
        aCustomer.setCart(aCart); // Link Cart back to Customer

        // Save the Cart now that Customer ID is set
        aCart = cartRepository.save(aCart);
        customerRepo.save(aCustomer); // Optional: Save Customer again if bidirectional

        // Create Manager
        Manager aManager = new Manager();
        aManager = managerRepo.save(aManager);

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