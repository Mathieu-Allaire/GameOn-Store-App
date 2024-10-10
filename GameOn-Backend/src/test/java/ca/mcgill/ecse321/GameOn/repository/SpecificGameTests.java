package test.java.ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.eventregistration.model.SpecificGame;
import ca.mcgill.ecse321.eventregistration.model.Game;
import ca.mcgill.ecse321.eventregistration.model.Category;
import main.java.ca.mcgill.ecse321.GameOn.repository.SpecificGameRepository;

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
	}

    @Test
    public void testCreateAndReadPerson(){
        //Arrange
        String aPicture = "url";
        String aName = "Overwatch";
        String aDescription = "Hero-based combat";
        int aPrice = 5;
        int aQuantity = 1;
        Category aCategory = Category("Fight", 1);
        Game gameTest = new Game( aPicture, aName, aDescription, aPrice, aQuantity, aCategory);
        SpecificGame specificGameTest = new SpecificGame(gameTest); // not sure if we need to put the id in the constructor

        aCategory = categoryRepo.save(aCategory);
        gameTest = gameRepo.save(gameTest);
        specificGameTest = specGamerepo.save(specificGameTest);
        int id = specificGameTest.getId();

        // Act
        SpecificGame specGameDB = specGamerepo.findSpecificGameById(id);

        //Assert
        assertNotNull(specGameDB);
        assertNotNull(specGameDB.getCategory);
        assertEquals(specGameDB.getCategory.getName == "Fight");
        assertEquals(specGameDB.getCategory.getId == 1);
        assertNotNull(specGameDB.getGame);
        assertEquals(specGameDB.getGame.getPicture == aPicture);
        assertEquals(specGameDB.getGame.getName == aName);
        assertEquals(specGameDB.getGame.getDescription == aDescription);
        assertEquals(specGameDB.getGame.getPrice == aPrice);
        assertEquals(specGameDB.getGame.getQuantity == aQuantity);
    }
    

}
