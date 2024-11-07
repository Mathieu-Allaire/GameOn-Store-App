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
        // Step 1: Create and save Customer
        Customer aCustomer = new Customer(1343, Date.valueOf("2024-11-02"), "123 main st", null);
        aCustomer = customerRepo.save(aCustomer);

        // Step 2: Create Cart, link to Customer, and save Cart
        Cart cart = new Cart();
        cart.setCustomer(aCustomer);
        aCustomer.setCart(cart);
        cart = cartRepo.save(cart); // Ensure Cart has an ID

        // Step 3: Use the saved Cart to create Order, then save Order
        Order aOrder = new Order(Date.valueOf("2024-11-02"), cart, aCustomer);
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
