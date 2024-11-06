package ca.mcgill.ecse321.GameOn.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.GameOn.model.Category;

@SpringBootTest
public class CategoryTests {

    @Autowired
    private CategoryRepository categoryRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        categoryRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadCategory(){
        //Arrange
        String aName = "war";
        Category war = new Category(aName);
        war = categoryRepo.save(war);
        //Act
        Category categoryDB = categoryRepo.findCategoryByName(aName);

        //Assert
        assertNotNull(categoryDB);
        assertEquals(categoryDB.getName(), aName, "Category constructor's 'name' could not be saved and loaded from database.");
    }

}
