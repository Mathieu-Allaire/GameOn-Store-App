package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Category;


@SpringBootTest
public class GameTests {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        gameRepo.deleteAll();
        categoryRepo.deleteAll();

    }

    @Test
    public void testCreateAndReadGame(){
        //Create
        String picture = "url";
        String name = "Overwatch";
        String description = "Hero-based combat";
        int price = 5;
        int quantity = 1;
        Category category = new Category("Fight");
        Game game = new Game( picture, name, description, price, quantity, category);

        //Save
        category = categoryRepo.save(category);
        game = gameRepo.save(game);

        //Read
        Game game_from_DB = gameRepo.findGameByName(name);

        //Assert
        assertNotNull(game_from_DB, "Game could not be saved and loaded from database.");
        assertEquals(game_from_DB.getPicture(), picture, "Game constructor's 'picture' could not be saved and loaded from database.");
        assertEquals(game_from_DB.getDescription(), description, "Game constructor's 'description' cdoes not match database.");
        assertEquals(game_from_DB.getPrice(), price, "Game constructor's 'price' does not match database.");
        assertEquals(game_from_DB.getQuantity(), quantity, "Game constructor's 'quantity' does not match database.");
        assertNotNull(game_from_DB.getCategory(), "Game's category could not be saved and loaded from database.");
        assertEquals(game_from_DB.getCategory().getName(),"Fight", "Game constructor's 'category' does not match database.");
    }


}

