package ca.mcgill.ecse321.GameOn.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.GameRequest;

import ca.mcgill.ecse321.GameOn.model.Employee.EmployeeStatus;
import ca.mcgill.ecse321.GameOn.model.RequestType;

@SpringBootTest
public class EmployeeTests {
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private GameRequestRepository gameRequestRepo;
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private GameRepository gameRepo;

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
    public void testCreateEmployee(){
        //Create Employee
        boolean aIsEmployed = true;
        Employee aEmployee = new Employee(aIsEmployed);
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

        //Create GameRequest
        GameRequest gameRequest = new GameRequest(RequestType.Create, aEmployee, game);

        aEmployee.addGameRequest(gameRequest);
        gameRequest = gameRequestRepo.save(gameRequest);

        //Save object
        int id =  aEmployee.getId().intValue();

        //Act
        Employee employeeDB = employeeRepo.findEmployeeById(id);

        //Assert
        assertNotNull(employeeDB);
        assertEquals(employeeDB.getIsEmployed(),aIsEmployed);
        assertEquals(employeeDB.getEmployeeStatus(),EmployeeStatus.Employed);

    }
}