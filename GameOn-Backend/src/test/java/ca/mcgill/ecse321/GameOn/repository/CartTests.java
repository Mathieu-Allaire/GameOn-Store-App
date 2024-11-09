package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Cart;

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

        Cart aCart = new Cart();
        aCart = cartRepo.save(aCart);

        //Act
        Cart result = cartRepo.findCartById(aCart.getId());

        //Assert
        assertNotNull(result);
        assertEquals(aCart.getId(), result.getId());


    }
}