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
    private SpecificGameRepository specGamerepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private GameRepository gameRepo;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        cartRepo.deleteAll();
        orderRepo.deleteAll();
        customerRepo.deleteAll();

        specGamerepo.deleteAll();
        gameRepo.deleteAll();
        categoryRepo.deleteAll();

    }

    @Test
    public void testCreateCart(){
        //Arrange
        long millis = System.currentTimeMillis();
        Date aDate = new Date(millis);

        //Create Customer
        int aCardNumber = 1111;
        String anAddress = "123 main street";
        Customer aCustomer = new Customer(aCardNumber, aDate, anAddress);
        Cart aCart = new Cart(aDate);
        aCustomer = customerRepo.save(aCustomer);
        aCart = cartRepo.save(aCart);

        //Create Order
        Order aOrder = new Order(aDate, aCart, aCustomer);
        aOrder = orderRepo.save(aOrder);


        //Create Game and Add Game to Cart
        String aPicture = "url";
        String aName = "Overwatch";
        String aDescription = "Hero-based combat";
        int aPrice = 5;
        int aQuantity = 1;
        Category aCategory = new Category("Fight");
        Game gameTest = new Game( aPicture, aName, aDescription, aPrice, aQuantity, aCategory);
        SpecificGame specificGameTest = new SpecificGame(gameTest); // not sure if we need to put the id in the constructor

        aCart.addSpecificGame(specificGameTest);

        aCategory = categoryRepo.save(aCategory);
        gameTest = gameRepo.save(gameTest);
        specificGameTest = specGamerepo.save(specificGameTest);


        //Save Object
        aCart = cartRepo.save(aCart);
        int id = aCart.getId();


        //Act
        Cart cartDB = cartRepo.findCartById(id);


        // Assert
        assertNotNull(cartDB);
        assertEquals(cartDB.getDateAdded().toString(), aDate.toString());
        assertEquals(cartDB.getId(), id);
        assertEquals(cartDB.getOrder().getPurchaseDate().toString(), aDate.toString());
    }
}