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
        long millis = System.currentTimeMillis();
        Date aDate = new Date(millis);


        //Create Class
        Cart aCart = new Cart(aDate);
        aCart = cartRepo.save(aCart);
        int id = aCart.getId();


        //Act
        Cart cartDB = cartRepo.findCartById(id);


        // Assert
        assertNotNull(cartDB);
        assertEquals(cartDB.getDateAdded(), aDate);
        assertEquals(cartDB.getId(), id);



    }
}