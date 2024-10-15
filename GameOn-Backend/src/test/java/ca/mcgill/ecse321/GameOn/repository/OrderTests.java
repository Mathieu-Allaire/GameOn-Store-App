package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Order;
import ca.mcgill.ecse321.GameOn.model.Wishlist;
import ca.mcgill.ecse321.GameOn.model.Customer;

@SpringBootTest
public class OrderTests {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private WishlistRepository wishlistRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderRepo.deleteAll();
        customerRepo.deleteAll();
        wishlistRepo.deleteAll();
    }

    @Test
    public void testCreateandReadOrder(){
        // Arrange
        int aId = 1;
        int aCardNumber = 1343;
        long millis = System.currentTimeMillis();
        String anAddress = "123 main st";
        Date aCustomerDate = new Date(millis);

        // Create Wishlist
        Wishlist aWishlist = new Wishlist();
        aWishlist = wishlistRepo.save(aWishlist);
        // Create Customer
        Customer aCustomer = new Customer(aCardNumber, aCustomerDate, anAddress, aWishlist);
        aCustomer = customerRepo.save(aCustomer);
        
        // Create Order
        Order aOrder = new Order(aId, aCustomerDate, aCustomer);
        aOrder = orderRepo.save(aOrder);
        
        int id = aOrder.getId();

        // Act
        Order result = orderRepo.findOrderById(id);

        // Assert
        assertNotNull(result);
        assertEquals(result.getId(), aOrder.getId());
        assertEquals(result.getOrderCustomer().getBillingAddress(), aOrder.getOrderCustomer().getBillingAddress());
        assertEquals(result.getOrderCustomer().getCardExpiryDate(), aOrder.getOrderCustomer().getCardExpiryDate());
        assertEquals(result.getOrderCustomer().getCardNum(), aOrder.getOrderCustomer().getCardNum());
    }
    
}
