package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.GameOn.model.SpecificGame;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;
import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;

@SpringBootTest
public class SpecificGameTests {
    @Autowired
    private SpecificGameRepository specGamerepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private GameRepository gameRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {


        specGamerepo.deleteAll();
        gameRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadSpecificGame(){
        //Arrange
        String aPicture = "url";
        String aName = "Overwatch";
        String aDescription = "Hero-based combat";
        int aPrice = 5;
        int aQuantity = 1;
        Category aCategory = new Category("Fight");
        Game gameTest = new Game( aPicture, aName, aDescription, aPrice, aQuantity, aCategory);
        SpecificGame specificGameTest = new SpecificGame(gameTest); // not sure if we need to put the id in the constructor

        aCategory = categoryRepo.save(aCategory);
        gameTest = gameRepo.save(gameTest);
        specificGameTest = specGamerepo.save(specificGameTest);
        int id = specificGameTest.getId();

        // Act
        SpecificGame specGameDB = specGamerepo.findSpecificGameById(id);

        //Assert
        assertNotNull(specGameDB, "SpecificGame could not be saved and loaded from database.");
        assertNotNull(specGameDB.getGame(), "SpecificGame's game could not be saved and loaded from database.");
        assertEquals(specGameDB.getGame().getPicture(), aPicture, "SpecificGame constructor's 'picture' could not be saved and loaded from database.");
        assertEquals(specGameDB.getGame().getName(), aName, "SpecificGame constructor's 'name' could not be saved and loaded from database.");
        assertEquals(specGameDB.getGame().getDescription(), aDescription, "SpecificGame constructor's 'description' could not be saved and loaded from database.");
        assertEquals(specGameDB.getGame().getPrice(), aPrice, "SpecificGame constructor's 'price' could not be saved and loaded from database.");
        assertEquals(specGameDB.getGame().getQuantity(), aQuantity, "SpecificGame constructor's 'quantity' could not be saved and loaded from database.");
        assertNotNull(specGameDB.getGame().getCategory(), "SpecificGame's category could not be saved and loaded from database.");
        assertEquals(specGameDB.getGame().getCategory().getName(),"Fight", "SpecificGame constructor's 'category' could not be saved and loaded from database.");
    }
    

}

