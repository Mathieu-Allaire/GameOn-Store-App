package ca.mcgill.ecse321.GameOn.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import ca.mcgill.ecse321.GameOn.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameCreateDto;
import ca.mcgill.ecse321.GameOn.dto.GameReqRequestDto;
import ca.mcgill.ecse321.GameOn.dto.GameRequestResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRequestRepository;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import ca.mcgill.ecse321.GameOn.repository.EmployeeRepository;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.GameRequest;


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
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private GameRequestRepository gameRequestRepository;


    private static final String GAME_NAME2 = "Test Game 2";
    private static final String GAME_NAME = "Test Game";
    private static final String GAME_DESCRIPTION = "This is a test game";
    private static final String GAME_PICTURE = "https://www.example.com";
    private static final int GAME_PRICE = 10;
    private static final int GAME_QUANTITY = 10;
    private static final String CATEGORY_NAME = "Test Category";

    //Attributes for employee
    private static final Boolean VALID_IS_EMPLOYED = true;
    private static final String VALID_EMAIL_EMPLOYEE = "james@gmail.com"; // no spaces,contain @ and . 
    private static final String VALID_NAME_EMPLOYEE = "James"; // at least one letter

    @AfterAll
    public void clearDatabase() {
        gameRepository.deleteAll();
        categoryRepository.deleteAll();
        personRepo.deleteAll();
        gameRequestRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
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
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
        assertTrue(((String) response.getBody()).contains("Category does not exist"));
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
        assertTrue(((String) response.getBody()).contains("Price must be greater than 0"));
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

        assertTrue(((String) response.getBody()).contains("Quantity must be greater than 0"));
    }


    @SuppressWarnings("null")
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
        assertTrue(((String) response.getBody()).contains("Game already exists"));
        //assertEquals("Game already exists",response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    @Order(10)
    public void testFindAllGames(){
        // Arrange
        // Act
        ResponseEntity<GameResponseDTO[]> response = client.getForEntity("/games", GameResponseDTO[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().length);
    }

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
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
        assertNotNull(response.getBody().getName());
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

    @SuppressWarnings("null")
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
        
    @Test
    @Order(22)
    public void testCreateValidEmployee(){
        //Create the wanted customerRequest
        EmployeeRequestDto james = new EmployeeRequestDto(VALID_EMAIL_EMPLOYEE, VALID_NAME_EMPLOYEE);
        //ACT
        ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employee", james, EmployeeResponseDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        EmployeeResponseDto person = response.getBody();
        assertNotNull(person);
        assertEquals(VALID_EMAIL_EMPLOYEE, person.getEmail());
        assertEquals(VALID_NAME_EMPLOYEE, person.getName());
        assertEquals(VALID_IS_EMPLOYED, person.getIsEmployed());
    }

    @SuppressWarnings("null")
    @Test
    @Order(23) 
    public void testCreateGameRequest(){
        // Arrange
        String url = "/games/request";
        GameReqRequestDto gameReq = new GameReqRequestDto(VALID_EMAIL_EMPLOYEE, GAME_NAME2, "Create");

        // Act
        ResponseEntity<GameRequestResponseDto> response = client.postForEntity(url, gameReq, GameRequestResponseDto.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Create",response.getBody().getRequestType());
        assertEquals(GAME_NAME2,response.getBody().getRequestedGame());
        assertEquals("Unavailable",response.getBody().getRequestedGameStatus());
    }

    @Test
    @Order(24)
    public void testCreateGameRequestInvalidGame(){
        // Arrange
        String url = "/games/request";
        GameReqRequestDto gameReq = new GameReqRequestDto(VALID_EMAIL_EMPLOYEE, "InvalidGame", "Create");

        // Act
        ResponseEntity<?> response = client.postForEntity(url, gameReq, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Game does not exist",response.getBody());
    }

    @Test
    @Order(25)
    public void testCreateGameRequestInvalidEmployee(){
        // Arrange
        String url = "/games/request";
        GameReqRequestDto gameReq = new GameReqRequestDto("InvalidEmployee", GAME_NAME, "Create");

        // Act
        ResponseEntity<?> response = client.postForEntity(url, gameReq, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Request creator does not exist",response.getBody());
    }

    @Test
    @Order(26)
    public void testCreateGameRequestInvalidRequestType(){
        // Arrange
        String url = "/games/request";
        GameReqRequestDto gameReq = new GameReqRequestDto(VALID_EMAIL_EMPLOYEE, GAME_NAME, "InvalidRequest");

        // Act
        ResponseEntity<?> response = client.postForEntity(url, gameReq, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Request type is invalid",response.getBody());
    }

    @Test
    @Order(27)
    public void testDuplicateGameRequest(){
        // Arrange
        String url = "/games/request";
        GameReqRequestDto gameReq = new GameReqRequestDto(VALID_EMAIL_EMPLOYEE, GAME_NAME2, "Create");

        // Act
        ResponseEntity<?> response = client.postForEntity(url, gameReq, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Game request already exists",response.getBody());
    }
  
    @Test
    @Order(28)
    public void testApproveGameRequest(){
        // Arrange
        Game game = gameRepository.findGameByName(GAME_NAME2);
        GameRequest gameRequest = gameRequestRepository.findGameRequestByresquestedGame(game);
        Integer GAME_REQUEST_ID = gameRequest.getId();
        String url = "/games/request/approve?" + "gameRequestId=" + GAME_REQUEST_ID;

        // Act
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(29)
    public void testDeleteCategory(){
        // Arrange
        String url = "/categories/" + CATEGORY_NAME;

        // Act
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(30)
    public void testDeleteGame(){
        // Arrange
        String url = "/games/" + GAME_NAME;

        // Act
        ResponseEntity<?> response = client.postForEntity(url, null, String.class);
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
