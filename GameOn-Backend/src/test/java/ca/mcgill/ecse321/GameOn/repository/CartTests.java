package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.model.Customer;
@SpringBootTest
public class CartTests {
    @Autowired
    private CartRepository cartRepo;
   
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        cartRepo.deleteAll();
    }

    @Test
    public void testCreateCart(){
        //Arrange
        Date date = Date.valueOf("2024-02-09");
        Customer customer = new Customer(111, date, "111 mcgill street");
        Cart aCart = new Cart(customer);
        aCart = cartRepo.save(aCart);

        //Act
        Cart result = cartRepo.findCartByCustomer(aCart.getCustomer());
        //Assert
        assertNotNull(result);
        assertEquals(aCart.getCustomer(), result.getCustomer());

    }
}