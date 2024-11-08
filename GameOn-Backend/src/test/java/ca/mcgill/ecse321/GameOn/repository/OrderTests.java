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
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Cart;


@SpringBootTest
public class OrderTests {
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
        Order aOrder = new Order(aCustomerDate, cart, aCustomer);
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
