package ca.mcgill.ecse321.GameOn.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Manager;

@SpringBootTest
public class ManagerTest {
    @Autowired
    private ManagerRepository managerRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        managerRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadManager() {
        // Arrange
        Manager aManager = new Manager();
        aManager = managerRepo.save(aManager);

        Long LongId = aManager.getId();
        int id = LongId.intValue();

        // Act
        Manager managerDB = managerRepo.findManagerById(id);

        // Assert
        assertNotNull(managerDB);
    }
}
