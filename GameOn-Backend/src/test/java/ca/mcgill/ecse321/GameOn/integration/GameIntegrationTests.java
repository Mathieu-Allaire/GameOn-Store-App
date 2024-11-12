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

    private static final String GAME_NAME2 = "Test Game 2";
    private static final String GAME_NAME = "Test Game";
    private static final String GAME_DESCRIPTION = "This is a test game";
    private static final String GAME_PICTURE = "https://www.example.com";
    private static final int GAME_PRICE = 10;
    private static final int GAME_QUANTITY = 10;
    private static final String CATEGORY_NAME = "Test Category";

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
    public void testCreateGameWithInvalidCategory(){
        // Arrange
        GameCreateDto gameCreateDto = new GameCreateDto(GAME_PICTURE, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, "InvalidCategory");

        // Act
        ResponseEntity<?> response = client.postForEntity("/games", gameCreateDto, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Category does not exist",response.getBody());
    }

    @Test
    @Order(6)
    public void testCreateGameWithInvalidPrice(){
        // Arrange
        GameCreateDto gameCreateDto = new GameCreateDto(GAME_PICTURE, GAME_NAME, GAME_DESCRIPTION, -1, GAME_QUANTITY, CATEGORY_NAME);

        // Act
        ResponseEntity<?> response = client.postForEntity("/games", gameCreateDto, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Price must be greater than 0",response.getBody());
    }

    @Test
    @Order(7)
    public void testCreateGameWithInvalidQuantity(){
        // Arrange
        GameCreateDto gameCreateDto = new GameCreateDto(GAME_PICTURE, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, -1, CATEGORY_NAME);

        // Act
        ResponseEntity<?> response = client.postForEntity("/games", gameCreateDto, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Quantity must be greater than 0",response.getBody());
    }


    @Test
    @Order(8)
    public void testCreateGame() {
        // Arrange
        GameCreateDto gameCreateDto = new GameCreateDto(GAME_PICTURE, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, CATEGORY_NAME);
        GameCreateDto gameCreateDto2 = new GameCreateDto(GAME_PICTURE, GAME_NAME2, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, CATEGORY_NAME);
        // Act
        ResponseEntity<GameResponseDTO> response = client.postForEntity("/games", gameCreateDto, GameResponseDTO.class);
        ResponseEntity<GameResponseDTO> response2 = client.postForEntity("/games", gameCreateDto2, GameResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertNotNull(response2);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());

        assertEquals(GAME_NAME, response.getBody().getName());
        assertEquals(GAME_NAME2, response2.getBody().getName());

        assertEquals(GAME_DESCRIPTION, response.getBody().getDescription());
        assertEquals(GAME_DESCRIPTION, response2.getBody().getDescription());

        assertEquals(GAME_PICTURE, response.getBody().getPicture());
        assertEquals(GAME_PICTURE, response2.getBody().getPicture());

        assertEquals(GAME_PRICE, response.getBody().getPrice());
        assertEquals(GAME_PRICE, response2.getBody().getPrice());

        assertEquals(GAME_QUANTITY, response.getBody().getQuantity());
        assertEquals(GAME_QUANTITY, response2.getBody().getQuantity());

        assertEquals(CATEGORY_NAME, response.getBody().getCategory());
        assertEquals(CATEGORY_NAME, response2.getBody().getCategory());
    }

    @Test
    @Order(9)
    public void testCreateDuplicateGameName(){
        // Arrange
        GameCreateDto gameCreateDto = new GameCreateDto(GAME_PICTURE, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_QUANTITY, CATEGORY_NAME);

        // Act
        ResponseEntity<?> response = client.postForEntity("/games", gameCreateDto, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Game already exists",response.getBody());
    }

    @Test
    @Order(10)
    public void testFindAllGames(){
        // Arrange
        // Act
        ResponseEntity<GameResponseDTO[]> response = client.getForEntity("/games", GameResponseDTO[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(GAME_NAME, response.getBody()[0].getName());
        assertEquals(GAME_NAME2, response.getBody()[1].getName());
    }

    @Test
    @Order(11)
    public void testFindGameByName(){
        // Arrange
        String url = "/games/" + GAME_NAME;

        // Act
        ResponseEntity<GameResponseDTO> response = client.getForEntity(url, GameResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(GAME_NAME, response.getBody().getName());
    }

    @Test
    @Order(12)
    public void testFindGameByInvalidName(){
        // Arrange
        String url = "/games/InvalidGame";

        // Act
        ResponseEntity<?> response = client.getForEntity(url, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(13)
    public void testFindGameByCategory(){
        // Arrange
        String url = "/games/category/" + CATEGORY_NAME;

        // Act
        ResponseEntity<GameResponseDTO[]> response = client.getForEntity(url, GameResponseDTO[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(GAME_NAME, response.getBody()[0].getName());
        assertEquals(GAME_NAME2, response.getBody()[1].getName());
    }

    @Test
    @Order(14)
    public void testFindGameByInvalidCategory(){
        // Arrange
        String url = "/games/category/InvalidCategory";

        // Act
        ResponseEntity<?> response = client.getForEntity(url, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(15)
    public void testUpdateGameQuantity(){
        // Arrange Request Param name and quantity
        String url = "/games/updateQuantity?" + "name=" + GAME_NAME + "&quantity=" + 5;

        // Act
        ResponseEntity<GameResponseDTO> response = client.postForEntity(url, null, GameResponseDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(GAME_NAME, response.getBody().getName());
        assertEquals(5, response.getBody().getQuantity());
    }

    @Test
    @Order(16)
    public void testUpdateGameQuantityInvalidName(){
        // Arrange Request Param name and quantity
        String url = "/games/updateQuantity?" + "name=InvalidGame" + "&quantity=" + 5;

        // Act
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Game does not exist", response.getBody());
    }

    @Test
    @Order(17)
    public void testUpdateGameQuantityInvalidQuantity(){
        // Arrange Request Param name and quantity
        String url = "/games/updateQuantity?" + "name=" + GAME_NAME + "&quantity=" + -1;

        // Act
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Quantity must be greater than 0",response.getBody());
    }

    @Test
    @Order(18)
    public void testUpdateGamePrice(){
        // Arrange Request Param name and price
        String url = "/games/updatePrice?" + "name=" + GAME_NAME + "&price=" + 15;

        // Act
        ResponseEntity<GameResponseDTO> response = client.postForEntity(url, null, GameResponseDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(GAME_NAME, response.getBody().getName());
        assertEquals(15, response.getBody().getPrice());
    }

    @Test
    @Order(19)
    public void testUpdateGamePriceInvalidName(){
        // Arrange Request Param name and price
        String url = "/games/updatePrice?" + "name=InvalidGame" + "&price=" + 15;

        // Act
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Game does not exist", response.getBody());
    }

    @Test
    @Order(20)
    public void testUpdateGamePriceInvalidPrice(){
        // Arrange Request Param name and price
        String url = "/games/updatePrice?" + "name=" + GAME_NAME + "&price=" + -1;

        // Act
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Price must be greater than 0",response.getBody());
    }

    @Test
    @Order(21)
    public void testGetAllCategories(){
        // Arrange
        // Act
        ResponseEntity<CategoryResponseDto[]> response = client.getForEntity("/categories", CategoryResponseDto[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().length);
        assertEquals(CATEGORY_NAME, response.getBody()[0].getName());
    }
}
