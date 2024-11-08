package ca.mcgill.ecse321.GameOn.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import ca.mcgill.ecse321.GameOn.repository.GameRequestRepository;
import ca.mcgill.ecse321.GameOn.model.Category;
import ca.mcgill.ecse321.GameOn.model.Game;
import ca.mcgill.ecse321.GameOn.model.RequestType;
import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.model.GameRequest;
import ca.mcgill.ecse321.GameOn.model.Game.GameStatus;

@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameServiceTests {
    @Mock
    private CategoryRepository categoryMockRepo;
    @Mock
    private GameRepository gameMockRepo;
    @Mock
    private GameRequestRepository gameRequestMockRepo;

    @InjectMocks
    private GameService service;

    private static final String VALID_URL = "testURL";
    private static final String VALID_GAME_NAME = "testGame";
    private static final String VALID_GAME_NAME2 = "testGame2";

    private static final String VALID_DESCRIPTION = "testDescription";
    private static final int VALID_PRICE = 10;
    private static final int VALID_QUANTITY = 5;
    private static final String VALID_CATEGORY_NAME = "testCategory";
    private static final Category VALID_CATEGORY = new Category(VALID_CATEGORY_NAME);
    private static final RequestType VALID_REQUEST_TYPE = RequestType.Create;

    private static final String INVALID_GAME_NAME = "";
    private static final String INVALID_CATEGORY_NAME = "";
    
    @Test
    public void testCreateValidCategory(){
        // Arrange
        when(categoryMockRepo.save(any(Category.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Category category = service.createCategory(VALID_CATEGORY_NAME);

        // Assert
        assertNotNull(category);
        assertEquals(VALID_CATEGORY_NAME, category.getName());
    }

    @Test
    public void testCreateInvalidCategory(){
        // Arrange
        when(categoryMockRepo.save(any(Category.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.createCategory(INVALID_CATEGORY_NAME);
        });

        assertEquals(ex.getMessage(), "Name is invalid");
    }

    @Test
    public void TestDeleteCategory(){
        // Arrange
        Category category = new Category(VALID_CATEGORY_NAME);
        Game game1 = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, category);
        Game game2 = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, category);
        category.addGame(game1);
        category.addGame(game2);
    
        when(categoryMockRepo.findCategoryByName(VALID_CATEGORY_NAME)).thenReturn(category);
        doNothing().when(categoryMockRepo).delete(any(Category.class));
        doNothing().when(gameMockRepo).delete(any(Game.class));

        // Act
        service.deleteCategory(VALID_CATEGORY_NAME);

        // Assert
        verify(gameMockRepo, times(1)).delete(game1);
        verify(gameMockRepo, times(1)).delete(game2);
        verify(categoryMockRepo, times(1)).delete(category);
    }

    @Test
    public void testGetAllCategories(){
        // Arrange
        when(categoryMockRepo.findAll()).thenReturn(null);

        // Act
        service.getAllCategories();

        // Assert
        verify(categoryMockRepo, times(1)).findAll();
    }

    @Test
    public void testCreateValidGame(){
        // Arrange
        when(gameMockRepo.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Game game = service.createGame(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
    
        // Assert
        assertNotNull(game);
        assertEquals(VALID_URL, game.getPicture());
        assertEquals(VALID_GAME_NAME, game.getName());
        assertEquals(VALID_DESCRIPTION, game.getDescription());
        assertEquals(VALID_PRICE, game.getPrice());
        assertEquals(VALID_QUANTITY, game.getQuantity());
        assertEquals(VALID_CATEGORY, game.getCategory());
    }

    @Test
    public void testCreateInvalidGame(){
        // Arrange
        when(gameMockRepo.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.createGame(VALID_URL, INVALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        });

        assertEquals(ex.getMessage(), "Name is invalid");

    }

    @Test
    public void testDeleteGame(){
        // Arrange
        Game game = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        when(gameMockRepo.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        doNothing().when(gameMockRepo).delete(any(Game.class));

        // Act
        service.deleteGame(VALID_GAME_NAME);

        // Assert
        verify(gameMockRepo, times(1)).delete(game);
    }

    @Test void testDeleteInvalidGame(){
        // Arrange
        when(gameMockRepo.findGameByName(INVALID_GAME_NAME)).thenReturn(null);

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.deleteGame(INVALID_GAME_NAME);
        });

        assertEquals(ex.getMessage(), "Name is invalid");
    }

    @Test
    public void testGetAllGames(){
        // Arrange
        when(gameMockRepo.findAll()).thenReturn(null);

        // Act
        service.getAllGames();

        // Assert
        verify(gameMockRepo, times(1)).findAll();
    }

    @Test
    public void testFindGameByName(){
        // Arrange
        Game game = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        when(gameMockRepo.findGameByName(VALID_GAME_NAME)).thenReturn(game);

        // Act
        Game foundGame = service.findGameByName(VALID_GAME_NAME);

        // Assert
        assertNotNull(foundGame);
        assertEquals(VALID_URL, foundGame.getPicture());
        assertEquals(VALID_GAME_NAME, foundGame.getName());
        assertEquals(VALID_DESCRIPTION, foundGame.getDescription());
        assertEquals(VALID_PRICE, foundGame.getPrice());
        assertEquals(VALID_QUANTITY, foundGame.getQuantity());
        assertEquals(VALID_CATEGORY, foundGame.getCategory());
    }

    @Test
    public void testGetGamesInCategory(){
        // Arrange
        Category category = new Category(VALID_CATEGORY_NAME);
        Game game1 = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, category);
        Game game2 = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, category);
        category.addGame(game1);
        category.addGame(game2);
        when(categoryMockRepo.findCategoryByName(VALID_CATEGORY_NAME)).thenReturn(VALID_CATEGORY);

        // Act
        service.getGamesInCategory(VALID_CATEGORY_NAME);

        // Assert
        verify(categoryMockRepo, times(1)).findCategoryByName(VALID_CATEGORY_NAME);
        assertEquals(2, category.getGames().size());
        assertEquals(game1, category.getGames().get(0));
        assertEquals(game2, category.getGames().get(1));
    }

    @Test
    public void testCreateGameRequest(){
        // Arrange
        Employee employee = new Employee(true);
        Game game = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        when(gameMockRepo.findGameByName(VALID_GAME_NAME)).thenReturn(game);
        when(gameMockRepo.save(any(Game.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(gameRequestMockRepo.save(any(GameRequest.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        GameRequest gameRequest = service.createGameRequest(employee, game, RequestType.Create);

        // Assert
        assertNotNull(gameRequest);
        assertEquals(employee, gameRequest.getRequestCreator());
        assertEquals(game, gameRequest.getRequestedGame());
        assertEquals(RequestType.Create, gameRequest.getRequestType());
    }

    @Test
    public void testCreateInvalidGameRequest(){
        // Arrange
        Employee employee = null;
        Game game = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.createGameRequest(employee, game, RequestType.Create);
        });

        assertEquals(ex.getMessage(), "Request creator is invalid");
    }

    @Test
    public void testApproveCreateGameRequest(){
        // Arrange
        Employee employee = new Employee(true);
        Game game = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        game.setQuantity(VALID_QUANTITY);
        
        GameRequest gameRequest = new GameRequest(RequestType.Create, employee, game);
        when(gameRequestMockRepo.findGameRequestById(gameRequest.getId())).thenReturn(gameRequest);
        when(gameRequestMockRepo.save(any(GameRequest.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        
        // Act
        service.approveGameRequest(gameRequest);

        // Assert
        assertEquals(GameStatus.Available, game.getGameStatus());
    }

    @Test
    public void testApproveArchiveGameRequest(){
        // Arrange
        Employee employee = new Employee(true);
        Game game = new Game(VALID_URL, VALID_GAME_NAME, VALID_DESCRIPTION, VALID_PRICE, VALID_QUANTITY, VALID_CATEGORY);
        game.setQuantity(VALID_QUANTITY);
        game.setGameStatus(GameStatus.Available);

        GameRequest gameRequest = new GameRequest(RequestType.Archive, employee, game);
        when(gameRequestMockRepo.findGameRequestById(gameRequest.getId())).thenReturn(gameRequest);
        when(gameRequestMockRepo.save(any(GameRequest.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        service.approveGameRequest(gameRequest);

        // Assert
        assertEquals(GameStatus.Unavailable, game.getGameStatus());
    }

    @Test
    public void testGetGameQuantity(){

    }

    @Test
    public void testUpdateGameQuantity(){

    }

    @Test
    public void testGetGamePrice(){

    }

    @Test
    public void testUpdateGamePrice(){

    }
}