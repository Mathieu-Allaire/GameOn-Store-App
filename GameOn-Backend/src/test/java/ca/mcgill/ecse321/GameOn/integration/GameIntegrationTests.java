package ca.mcgill.ecse321.GameOn.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import ca.mcgill.ecse321.GameOn.dto.CategoryRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CategoryResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameCreateDto;
import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import ca.mcgill.ecse321.GameOn.model.Category;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private static final String GAME_NAME = "Test Game";
    private static final String GAME_DESCRIPTION = "This is a test game";
    private static final String GAME_PICTURE = "https://www.example.com";
    private static final int GAME_PRICE = 10;
    private static final int GAME_QUANTITY = 10;

    private static final String CATEGORY_NAME = "Test Category";

    private Category aCategory;

    @AfterAll
    public void clearDatabase() {
        gameRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateCategory() {
        // Arrange
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(CATEGORY_NAME);

        // Act
        ResponseEntity<CategoryResponseDto> response = client.postForEntity("/categories", categoryRequestDto, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(CATEGORY_NAME, response.getBody().getName());
        Category aCategory = categoryRepository.findCategoryByName(CATEGORY_NAME);
        assertNotNull(aCategory);
        assertEquals(CATEGORY_NAME, aCategory.getName());
    }

    @Test
    @Order(2)
    public void testGetValidCategoryByName(){
        // Arrange
        String url = "/categories/" + CATEGORY_NAME;

        // Act
        ResponseEntity<CategoryResponseDto> response = client.getForEntity(url, CategoryResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CATEGORY_NAME, response.getBody().getName());
    }

    @Test
    @Order(3)
    public void testGetInvalidCategoryByName(){
        // Arrange
        String url = "/categories/InvalidCategory";

        // Act
        ResponseEntity<?> response = client.getForEntity(url, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(4)
    public void testCreateInvalidCategory() {
        // Arrange
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(CATEGORY_NAME);

        // Act
        ResponseEntity<?> response = client.postForEntity("/categories", categoryRequestDto, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.IM_USED, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testCreateGame() {
        // Arrange
        GameCreateDto gameCreateDto = new GameCreateDto(GAME_PICTURE, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, CATEGORY_NAME);
       
        // Act
        ResponseEntity<GameResponseDTO> response = client.postForEntity("/games", gameCreateDto, GameResponseDTO.class);
       
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(GAME_NAME, response.getBody().getName());
        assertEquals(GAME_DESCRIPTION, response.getBody().getDescription());
        assertEquals(GAME_PICTURE, response.getBody().getPicture());
        assertEquals(GAME_PRICE, response.getBody().getPrice());
        assertEquals(GAME_QUANTITY, response.getBody().getQuantity());
        assertEquals(CATEGORY_NAME, response.getBody().getCategory());
    }
}
