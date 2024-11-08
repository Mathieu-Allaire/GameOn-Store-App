package ca.mcgill.ecse321.GameOn.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.mcgill.ecse321.GameOn.repository.GameRepository;
import ca.mcgill.ecse321.GameOn.repository.CategoryRepository;
import ca.mcgill.ecse321.GameOn.model.Category;

@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameServiceTests {
    @Mock
    private CategoryRepository mockRepository;
    @InjectMocks
    private GameService service;

    private static final String VALID_URL = "testURL";
    private static final String VALID_GAME_NAME = "testGame";
    private static final String VALID_DESCRIPTION = "testDescription";
    private static final int VALID_PRICE = 10;

    private static final String VALID_CATEGORY_NAME = "testCategory";
    private static final String INVALID_CATEGORY_NAME = "";
    
    @Test
    public void testCreateValidCategory(){
        // Arrange
        when(mockRepository.save(any(Category.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Act
        Category category = service.createCategory(VALID_CATEGORY_NAME);

        // Assert
        assertNotNull(category);
        assertEquals(VALID_CATEGORY_NAME, category.getName());
    }

    @Test
    public void testCreateInvalidCategory(){
        // Arrange
        when(mockRepository.save(any(Category.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        // Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.createCategory(INVALID_CATEGORY_NAME);
        });

        assertEquals(ex.getMessage(), "Name is invalid");
    }   
}
