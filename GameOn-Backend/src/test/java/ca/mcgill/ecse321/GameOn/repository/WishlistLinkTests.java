package ca.mcgill.ecse321.GameOn.repository;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.WishlistLink;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WishlistLinkTests {
    @Autowired
    private WishlistLinkRepository wishlistLinkRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        wishlistLinkRepo.deleteAll();
        customerRepo.deleteAll();
        gameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadWhishlistLink() {
        // Arrange
        //Create
        String picture = "url";
        String name = "Overwatch";
        String description = "Hero-based combat";
        int price = 5;
        int quantity = 1;

        //Create and Save Category
        Category category = new Category("Fight");
        category = categoryRepo.save(category);

        //Create and Save Game
        Game game = new Game( picture, name, description, price, quantity, category);
        game = gameRepo.save(game);

        int aCardNumber = 1234;
        long millis = System.currentTimeMillis();
        Date aCustomerDate = new Date(millis);
        String aCustomerAddress = "123 main st";

        // Create Customer with no wishlist
        Customer aCustomer = new Customer(aCardNumber, aCustomerDate, aCustomerAddress);

        aCustomer = customerRepo.save(aCustomer);

        // Create WishlistLink
        WishlistLink.Key key = new WishlistLink.Key(game, aCustomer);
        WishlistLink aWishlistLink = new WishlistLink(key);

        aWishlistLink = wishlistLinkRepo.save(aWishlistLink);

        // Act
        WishlistLink wishlistLinkDB = wishlistLinkRepo.findWishlistLinkByKey(key);

        // Assert
        assertNotNull(wishlistLinkDB);
		assertNotNull(wishlistLinkDB.getKey());
		assertNotNull(wishlistLinkDB.getKey().getCustomer());
		assertNotNull(wishlistLinkDB.getKey().getCustomer().getId() == aCustomer.getId());
		assertNotNull(wishlistLinkDB.getKey().getWishlistGames());
		assertNotNull(wishlistLinkDB.getKey().getWishlistGames().getName() == game.getName());
    }
}
