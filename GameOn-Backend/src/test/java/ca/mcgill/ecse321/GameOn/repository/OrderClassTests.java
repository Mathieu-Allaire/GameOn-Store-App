package ca.mcgill.ecse321.GameOn.repository;

import ca.mcgill.ecse321.GameOn.model.OrderClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Cart;


@SpringBootTest
public class OrderClassTests {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CartRepository cartRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderRepo.deleteAll();
        customerRepo.deleteAll();
        cartRepo.deleteAll();
    }

    @Test
    public void testCreateandReadOrder(){
        // Arrange
        int aCardNumber = 1343;
        String anAddress = "123 main st";
        Date aCustomerDate = Date.valueOf("2024-11-02");

        // Create Customer
        Cart cart = new Cart();
        cartRepo.save(cart);
        Customer aCustomer = new Customer(aCardNumber, aCustomerDate, anAddress,cart);
        aCustomer = customerRepo.save(aCustomer);


        // Create Cart
        Cart aCart = new Cart();
        aCart = cartRepo.save(aCart);

        
        // Create Order
        OrderClass aOrderClass = new OrderClass(aCustomerDate, cart, aCustomer);
        aOrderClass = orderRepo.save(aOrderClass);
        
        int id = aOrderClass.getId();

        List<SpecificGame> orderGames = aOrderClass.getOrderGames();

        // Act
        OrderClass result = orderRepo.findOrderById(id);

        // Assert
        assertNotNull(result);
        assertEquals(result.getId(), aOrderClass.getId());
        assertNotNull(orderGames);
        assertEquals(result.getOrderCustomer().getBillingAddress(), aOrderClass.getOrderCustomer().getBillingAddress());
        assertEquals(result.getOrderCustomer().getCardExpiryDate(), aOrderClass.getOrderCustomer().getCardExpiryDate());
        assertEquals(result.getOrderCustomer().getCardNum(), aOrderClass.getOrderCustomer().getCardNum());
    }
    
}
