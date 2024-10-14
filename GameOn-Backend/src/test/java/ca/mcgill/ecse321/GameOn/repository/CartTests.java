package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.*;



@SpringBootTest
public class CartTests {
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private WishlistRepository wishListRepo;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {

        cartRepo.deleteAll();
        orderRepo.deleteAll();
        customerRepo.deleteAll();
        wishListRepo.deleteAll();



    }

    @Test
    public void testCreateCart(){
        //Arrange
        long millis = System.currentTimeMillis();
        Date aDate = new Date(millis);
        int aId = 2;

        Wishlist aCustomerWishlist = new Wishlist();
        aCustomerWishlist = wishListRepo.save(aCustomerWishlist);


        //Create Customer
        int aCardNumber = 1111;
        String anAddress = "123 main street";
        Customer aCustomer = new Customer(aCardNumber, aDate, anAddress, aCustomerWishlist);
        aCustomer = customerRepo.save(aCustomer);

        //Create Order
        Order aOrder = new Order(aId, aDate, aCustomer);
        aOrder = orderRepo.save(aOrder);

        //Create Class
        Cart aCart = new Cart(aDate,aOrder);
        aCart = cartRepo.save(aCart);
        int id = aCart.getId();


        //Act
        Cart cartDB = cartRepo.findCartById(id);


        // Assert
        assertNotNull(cartDB);
        assertEquals(cartDB.getDateAdded().toString(), aDate.toString());
        assertEquals(cartDB.getId(), id);



    }
}