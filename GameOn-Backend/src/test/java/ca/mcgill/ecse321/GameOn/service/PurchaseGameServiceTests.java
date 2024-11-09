package ca.mcgill.ecse321.GameOn.service;



import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Date;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PurchaseGameServiceTests {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private SpecificGameRepository specificGameRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GameRepository gameRepository;


    @InjectMocks
    private PurchaseGameService purchaseGameService;

    @SuppressWarnings("null")

    //Helper Test Functions
    private SpecificGame exampleSpecificGame() {
        String aPicture = "url";
        String aName = "Elden Ring";
        String aDescription = "Open World Dark Souls";
        int aPrice = 60;
        int aQuantity = 100;
        Category aCategory = new Category("Open World");

        Game aGame = new Game(aPicture, aName, aDescription, aPrice, aQuantity, aCategory);

        return new SpecificGame(aGame);
    }

    private boolean specificGameEqual(SpecificGame specificGame1, SpecificGame specificGame2) {

        return specificGame1.getGame().getPicture().equals(specificGame2.getGame().getPicture())
                && specificGame1.getGame().getName().equals(specificGame2.getGame().getName())
                && specificGame1.getGame().getDescription().equals(specificGame2.getGame().getDescription())
                && specificGame1.getGame().getPrice() == specificGame2.getGame().getPrice()
                && specificGame1.getGame().getQuantity() == specificGame2.getGame().getQuantity()
                && specificGame1.getGame().getCategory().equals(specificGame2.getGame().getCategory());
    }


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
        assertEquals(aCart.getId(), cart.getId());
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

        SpecificGame aSpecificGame = exampleSpecificGame();

        when(specificGameRepository.findSpecificGameById(id)).thenReturn(aSpecificGame);

        //Act
        SpecificGame specificGame = purchaseGameService.findSpecificGameById(id);

        //Assert
        assertNotNull(specificGame);
        assertTrue( specificGameEqual(aSpecificGame, specificGame) );
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
        assertEquals(aOrder.getCart().getId(), order.getCart().getId());
        assertEquals(aOrder.getOrderCustomer().getCardNum(), order.getOrderCustomer().getCardNum());
        assertEquals(aOrder.getOrderCustomer().getCardExpiryDate(), order.getOrderCustomer().getCardExpiryDate());
        assertEquals(aOrder.getOrderCustomer().getBillingAddress(), order.getOrderCustomer().getBillingAddress());
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
        int cartId = 420;
        int specificGameId = 123;

        Cart aCart = new Cart();
        SpecificGame aSpecificGame = exampleSpecificGame();

        when(cartRepository.findCartById(cartId)).thenReturn(aCart);
        when(specificGameRepository.findSpecificGameById(specificGameId)).thenReturn(aSpecificGame);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        Cart cart = purchaseGameService.addSpecificGameToCart(specificGameId, cartId);

        //Assert
        assertNotNull(aCart);
        assertEquals(1, cart.getSpecificGames().size());
        assertEquals(aCart.getId(), cart.getId());
        assertTrue( specificGameEqual( aCart.getSpecificGame(0) , cart.getSpecificGame(0)) );
    }
    @Test
    public void testAddInvalidSpecificGameToCart() {
        int cartId = 420;
        int specificGameId = 123;

        Cart aCart = new Cart();

        when(cartRepository.findCartById(cartId)).thenReturn(aCart);
        when(specificGameRepository.findSpecificGameById(specificGameId)).thenReturn(null);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.addSpecificGameToCart(specificGameId, cartId));
        assertEquals("There are no specific game with the ID: " + specificGameId + ".", e.getMessage());
    }
    @Test
    public void testAddInvalidSpecificGameIdToCart() {
        int cartId = 420;
        int specificGameId = -1;

        Cart aCart = new Cart();
        SpecificGame aSpecificGame = exampleSpecificGame();

        when(cartRepository.findCartById(cartId)).thenReturn(aCart);
        when(specificGameRepository.findSpecificGameById(specificGameId)).thenReturn(aSpecificGame);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.addSpecificGameToCart(specificGameId, cartId));
        assertEquals("SpecificGame ID is invalid.", e.getMessage());
    }
    @Test
    public void testAddSpecificGameToInvalidCart() {
        int cartId = 420;
        int specificGameId = 123;

        SpecificGame aSpecificGame = exampleSpecificGame();

        when(cartRepository.findCartById(cartId)).thenReturn(null);
        when(specificGameRepository.findSpecificGameById(specificGameId)).thenReturn(aSpecificGame);
        when(cartRepository.save(any(Cart.class))).thenReturn(null);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.addSpecificGameToCart(specificGameId, cartId));
        assertEquals("There are no cart with the ID: " + cartId + ".", e.getMessage());
    }

    @Test
    public void testAddSpecificGameToInvalidCartId() {
        int cartId = -1;
        int specificGameId = 123;

        Cart aCart = new Cart();
        SpecificGame aSpecificGame = exampleSpecificGame();

        when(cartRepository.findCartById(cartId)).thenReturn(aCart);
        when(specificGameRepository.findSpecificGameById(specificGameId)).thenReturn(aSpecificGame);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.addSpecificGameToCart(specificGameId, cartId));
        assertEquals("Cart ID is invalid.", e.getMessage());
    }

    //remove specific game from cart
    @Test
    public void testRemoveValidSpecificGameFromCart() {
        //Arrange
        Cart aCart = new Cart();
        SpecificGame aSpecificGame = exampleSpecificGame();

        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(specificGameRepository.findSpecificGameById(aSpecificGame.getId())).thenReturn(aSpecificGame);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        purchaseGameService.addSpecificGameToCart(aSpecificGame.getId(), aCart.getId());

        //Assert
        assertEquals(1, aCart.getSpecificGames().size());

        //Act
        purchaseGameService.removeSpecificGameFromCart(aSpecificGame.getId(), aCart.getId());

        //Assert
        assertEquals(0, aCart.getSpecificGames().size());
    }

    @Test
    public void testRemoveInvalidSpecificGameFromCart() {
        //Arrange
        Cart aCart = new Cart();
        SpecificGame aSpecificGame = exampleSpecificGame();
        int invalidSpecificGameId = -1;

        when(specificGameRepository.findSpecificGameById(aSpecificGame.getId())).thenReturn(aSpecificGame);
        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        purchaseGameService.addSpecificGameToCart(aSpecificGame.getId(), aCart.getId());

        //Assert
        assertEquals(1, aCart.getSpecificGames().size());

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.removeSpecificGameFromCart(invalidSpecificGameId, aCart.getId()));

        assertEquals("Specific Game ID is invalid.", e.getMessage());
    }
    @Test
    public void testRemoveSpecificFromInvalidCart() {
        //Arrange
        Cart aCart = new Cart();
        SpecificGame aSpecificGame = exampleSpecificGame();
        int invalidCartId = -1;

        when(specificGameRepository.findSpecificGameById(aSpecificGame.getId())).thenReturn(aSpecificGame);
        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        purchaseGameService.addSpecificGameToCart(aSpecificGame.getId(), aCart.getId());

        //Assert
        assertEquals(1, aCart.getSpecificGames().size());

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.removeSpecificGameFromCart(aSpecificGame.getId(), invalidCartId));

        assertEquals("Cart ID is invalid.", e.getMessage());
    }

    //remove all games from cart
    @Test
    public void testRemoveAllGamesFromCart() {
        //Arrange
        Cart aCart = new Cart();
        SpecificGame aSpecificGame = exampleSpecificGame();
        SpecificGame aSpecificGame2 = exampleSpecificGame();
        aSpecificGame2.setId(2);

        when(specificGameRepository.findSpecificGameById(aSpecificGame.getId())).thenReturn(aSpecificGame);
        when(specificGameRepository.findSpecificGameById(aSpecificGame2.getId())).thenReturn(aSpecificGame2);
        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        purchaseGameService.addSpecificGameToCart(aSpecificGame.getId(), aCart.getId());
        purchaseGameService.addSpecificGameToCart(aSpecificGame2.getId(), aCart.getId());

        //Assert
        assertEquals(2, aCart.getSpecificGames().size());

        //Act
        purchaseGameService.removeAllGamesFromCart(aCart.getId());
        
        //Assert
        assertEquals(0, aCart.getSpecificGames().size());
    }
    @Test
    public void testRemoveAllGamesFromEmptyCart() {
        //Arrange
        Cart aCart = new Cart();

        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.removeAllGamesFromCart(aCart.getId()));
        assertEquals("Cart is empty.", e.getMessage());
    }
    @Test
    public void testRemoveAllGamesFromInvalidCart() {
        //Arrange
        Cart aCart = new Cart();
        int invalidCartId = -1;

        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.removeAllGamesFromCart(invalidCartId));
        assertEquals("Cart ID is invalid.", e.getMessage());
    }

    //create order from cart
    @Test
    public void testCreateOrderFromCart() {
        //Arrange
        Cart aCart = new Cart();
        Date aDate = java.sql.Date.valueOf("2025-03-03");
        Customer aCustomer = new Customer(12342134, aDate, "1234 Waterloo Street", aCart);
        aCart.setCustomer(aCustomer);
        SpecificGame aSpecificGame = exampleSpecificGame();
        SpecificGame aSpecificGame2 = exampleSpecificGame();
        Game aGame = aSpecificGame.getGame();
        aGame.setQuantity(10);

        aSpecificGame2.setId(2);

        when(specificGameRepository.findSpecificGameById(aSpecificGame.getId())).thenReturn(aSpecificGame);
        // when(specificGameRepository.findSpecificGameById(aSpecificGame2.getId())).thenReturn(aSpecificGame2);
        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);
        when(gameRepository.save(any(Game.class))).thenReturn(aSpecificGame.getGame());
        when(specificGameRepository.save(any(SpecificGame.class))).thenReturn(aSpecificGame);
        // when(specificGameRepository.save(any(SpecificGame.class))).thenReturn(aSpecificGame2);

        //Act
        purchaseGameService.addSpecificGameToCart(aSpecificGame.getId(), aCart.getId());
        // purchaseGameService.addSpecificGameToCart(aSpecificGame2.getId(), aCart.getId());

        //Assert
        assertEquals(1, aCart.getSpecificGames().size());

        //Act
        Order order = purchaseGameService.createOrderFromCart(aCart.getId());

        //Assert
        assertNotNull(order);
    }
    @Test
    public void testCreateOrderFromInvalidCart() {
        //Arrange
        Cart aCart = new Cart();
        int invalidCartId = -1;

        when(cartRepository.findCartById(aCart.getId())).thenReturn(aCart);
        when(cartRepository.save(any(Cart.class))).thenReturn(aCart);

        //Act
        //Assert
        Exception e = assertThrows(Exception.class, () -> purchaseGameService.createOrderFromCart(invalidCartId));
        assertEquals("ID is invalid.", e.getMessage());
    }
}
