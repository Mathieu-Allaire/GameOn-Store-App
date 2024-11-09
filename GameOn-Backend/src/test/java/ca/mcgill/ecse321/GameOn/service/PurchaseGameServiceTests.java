package ca.mcgill.ecse321.GameOn.service;


import ca.mcgill.ecse321.GameOn.repository.CartRepository;
import ca.mcgill.ecse321.GameOn.repository.OrderRepository;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void testFindValidCart() {}
    @Test
    public void testFindInvalidCart() {}

    //Find Specific Game by ID
    @Test
    public void testFindValidSpecificGame() {}
    @Test public void testFindInvalidSpecificGame() {}

    //Find Order by ID
    @Test
    public void testFindValidOrder() {}
    @Test
    public void testFindInvalidOrder() {}

    //Add specific game to cart
    @Test
    public void testAddValidSpecificGameToCart() {}
    @Test
    public void testAddInvalidSpecificGameToCart() {}
    @Test
    public void testAddSpecificToInvalidCart() {}

    //remove specific game from cart
    @Test
    public void testRemoveValidSpecificGameFromCart() {}
    @Test
    public void testRemoveInvalidSpecificGameFromCart() {}
    @Test
    public void testRemoveSpecificFromInvalidCart() {}

    //remove all games from cart
    @Test
    public void testRemoveAllGamesFromCart() {}
    @Test
    public void testRemoveAllGamesFromEmptyCart() {}
    @Test
    public void testRemoveAllGamesFromInvalidCart() {}

    //create order from cart
    @Test
    public void testCreateOrderFromCart() {}
    @Test
    public void testCreateOrderFromInvalidCart() {}
}
