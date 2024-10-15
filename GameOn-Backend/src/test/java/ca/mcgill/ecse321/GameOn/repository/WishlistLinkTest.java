package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.validateMockitoUsage;

import java.sql.Date;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.WishlistLink;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Wishlist;

@SpringBootTest
public class WishlistLinkTest {
    @Autowired
    private WishlistLinkRepository wishlistLinkRepo;
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private WishlistRepository wishlistRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        gameRepo.deleteAll();
        wishlistRepo.deleteAll();
        categoryRepo.deleteAll();
        wishlistLinkRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadWishlistLink() {
        // Arrange
        //Create
        String picture = "url";
        String name = "Overwatch";
        String description = "Hero-based combat";
        int price = 5;
        int quantity = 1;
        Category category = new Category("Fight");
        Game game = new Game( picture, name, description, price, quantity, category);
        category = categoryRepo.save(category);
        game = gameRepo.save(game);

        // Arrange
        Wishlist wishlist = new Wishlist();
        wishlist = wishlistRepo.save(wishlist);

        WishlistLink.Key key = new WishlistLink.Key(game, wishlist);
        WishlistLink wishlistLink = new WishlistLink(key);

        wishlistLink = wishlistLinkRepo.save(wishlistLink);

        // Act
        WishlistLink wishlistLinkDB = wishlistLinkRepo.findWishlistLinkByKey(key);

        // Assert
        assertNotNull(wishlistLinkDB);
        assertNotNull(wishlistLinkDB.getKey());
        assertNotNull(wishlistLinkDB.getKey().getWishList());
        assertNotNull(wishlistLinkDB.getKey().getWishList().getId() == wishlist.getId());
        assertNotNull(wishlistLinkDB.getKey().getWishlistGames());
        assertNotNull(wishlistLinkDB.getKey().getWishlistGames().getName() == game.getName());

    }
    
}
