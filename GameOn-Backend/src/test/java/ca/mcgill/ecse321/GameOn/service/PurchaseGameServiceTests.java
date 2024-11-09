package ca.mcgill.ecse321.GameOn.service;



import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PurchaseGameServiceTests {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private SpecificGameRepository specificGameRepository;
    @Mock
    private OrderRepository orderRepository;


    @InjectMocks
    private PurchaseGameService purchaseGameService;

    @SuppressWarnings("null")

    //Find Cart by ID
    @Test
    public void testFindValidCart() {
        //Arrange
        int id = 420;
        Cart aCart = new Cart();
        when(cartRepository.findCartById(id)).thenReturn(aCart);

        //Act
        Cart cart = purchaseGameService.findCartByID(id);

        //Assert
        assertNotNull(cart);
    }
    @Test
    public void testFindInvalidCart() {
        //Arrange
        int id = 420;
        when(cartRepository.findCartById(id)).thenReturn(null);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.findCartByID(id));
        assertEquals("There are no cart with the ID: " + id + ".", e.getMessage());
    }

    //Find Specific Game by ID
    @Test
    public void testFindValidSpecificGame() {
        //Arrange
        int id = 420;

        String aPicture = "url";
        String aName = "Elden Ring";
        String aDescription = "Open World Dark Souls";
        int aPrice = 60;
        int aQuantity = 100;
        Category aCategory = new Category("Open World");

        Game aGame = new Game(aPicture, aName, aDescription, aPrice, aQuantity, aCategory);
        SpecificGame aSpecificGame = new SpecificGame(aGame);

        when(specificGameRepository.findSpecificGameById(id)).thenReturn(aSpecificGame);

        //Act
        SpecificGame specificGame = purchaseGameService.findSpecificGameById(id);

        //Assert
        assertNotNull(specificGame);
        assertEquals(aSpecificGame.getGame(), specificGame.getGame());
    }
    @Test public void testFindInvalidSpecificGame() {
        //Arrange
        int id = 420;
        when(specificGameRepository.findSpecificGameById(id)).thenReturn(null);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.findSpecificGameById(id));
        assertEquals("There are no specific game with the ID: " + id + ".", e.getMessage());

    }

    //Find Order by ID
    @Test
    public void testFindValidOrder() {
        //Arrange
        int id = 420;

        Date aDate = new Date(System.currentTimeMillis());
        Cart aCart = new Cart();

        int aCardNum = 21432141;
        Date aCardExpiryDate = new Date(System.currentTimeMillis());
        String aBillingAddress = "434 Waterloo Street";
        Customer aCustomer = new Customer(aCardNum, aCardExpiryDate, aBillingAddress, aCart);

        Order aOrder = new Order(aDate, aCart, aCustomer);

        when(orderRepository.findOrderById(id)).thenReturn(aOrder);

        //Act
        Order order = purchaseGameService.findOrderById(id);

        //Assert
        assertNotNull(order);
        assertEquals(aOrder.getPurchaseDate(), order.getPurchaseDate());
        assertEquals(aOrder.getCart(), order.getCart());
        assertEquals(aOrder.getOrderCustomer(), order.getOrderCustomer());
    }
    @Test
    public void testFindInvalidOrder() {
        //Arrange
        int id = 420;
        when(orderRepository.findOrderById(id)).thenReturn(null);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.findOrderById(id));
        assertEquals("There are no order with the ID: " + id + ".", e.getMessage());
    }

    //Add specific game to cart
    @Test
    public void testAddValidSpecificGameToCart() {
        //Arrange
        //Act
        //Assert
    }
    @Test
    public void testAddInvalidSpecificGameToCart() {
        //Arrange
        //Act
        //Assert
    }
    @Test
    public void testAddSpecificToInvalidCart() {
        //Arrange
        //Act
        //Assert
    }

    //remove specific game from cart
    @Test
    public void testRemoveValidSpecificGameFromCart() {
        //Arrange
        //Act
        //Assert
    }
    @Test
    public void testRemoveInvalidSpecificGameFromCart() {
        //Arrange
        //Act
        //Assert
    }
    @Test
    public void testRemoveSpecificFromInvalidCart() {
        //Arrange
        //Act
        //Assert
    }

    //remove all games from cart
    @Test
    public void testRemoveAllGamesFromCart() {
        //Arrange
        //Act
        //Assert
    }
    @Test
    public void testRemoveAllGamesFromEmptyCart() {
        //Arrange
        //Act
        //Assert
    }
    @Test
    public void testRemoveAllGamesFromInvalidCart() {
        //Arrange
        //Act
        //Assert
    }

    //create order from cart
    @Test
    public void testCreateOrderFromCart() {
        //Arrange
        //Act
        //Assert
    }
    @Test
    public void testCreateOrderFromInvalidCart() {
        //Arrange
        //Act
        //Assert
    }
}
