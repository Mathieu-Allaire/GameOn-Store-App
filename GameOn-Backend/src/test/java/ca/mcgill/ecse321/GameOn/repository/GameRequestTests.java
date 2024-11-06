package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.model.GameRequest;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.Manager;
import ca.mcgill.ecse321.GameOn.model.RequestType;

@SpringBootTest
public class GameRequestTests {
    
    @Autowired
    private GameRequestRepository gameRequestRepo;
    @Autowired
    private  EmployeeRepository employeeRepo;
    @Autowired
    private  GameRepository gameRepo;
    @Autowired
    private  CategoryRepository categoryRepo;
    @Autowired
    private  ManagerRepository managerRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        gameRequestRepo.deleteAll();
        employeeRepo.deleteAll();
        gameRepo.deleteAll();
        categoryRepo.deleteAll();
        managerRepo.deleteAll();
    }

    @Test
    public void testCreateGameRequest(){
        //Arrange

        //Create employee
        Employee aEmployee = new Employee(true);
        aEmployee = employeeRepo.save(aEmployee);

        //Create Game
        String picture = "url";
        String name = "Overwatch";
        String description = "Hero-based combat";
        int price = 5;
        int quantity = 1;
        Category category = new Category("Fight");
        category = categoryRepo.save(category);
        Game game = new Game( picture, name, description, price, quantity, category);
        game = gameRepo.save(game);


        //Create manager
        Manager manager = new Manager();
        manager = managerRepo.save(manager);

        //Create GameRequest
        GameRequest gameRequest = new GameRequest(RequestType.Create, aEmployee, game);
        gameRequest = gameRequestRepo.save(gameRequest);

        int id = gameRequest.getId();

        // Act
        gameRequest = gameRequestRepo.findGameRequestById(id);

        //Assert
        assertNotNull(gameRequest);
        assertEquals(gameRequest.getRequestType(), RequestType.Create);
        assertEquals(gameRequest.getRequestCreator().getEmployeeStatus(), aEmployee.getEmployeeStatus());
        assertEquals(gameRequest.getResquestedGame().getName(), game.getName());
        assertEquals(gameRequest.getResquestedGame().getPicture(), game.getPicture());
        assertEquals(gameRequest.getResquestedGame().getCategory().getName(), game.getCategory().getName());
        assertEquals(gameRequest.getResquestedGame().getPrice(), game.getPrice());
        assertEquals(gameRequest.getResquestedGame().getQuantity(), game.getQuantity());
    }
}
