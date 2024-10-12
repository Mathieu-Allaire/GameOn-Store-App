package ca.mcgill.ecse321.GameOn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Calendar;
import java.util.Date;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.WishList;
import ca.mcgill.ecse321.GameOn.repository.Customer;
import ca.mcgill.ecse321.GameOn.repository.WishList;

@SpringBootTest
public class WishlistTests {
    
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private WishlistRepository wishlistRepo;
    @BeforeEach
    @AfterEach

    public void clearDatabase() {
        customerRepo.deleteAll();
        wishlistRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadWishlist(){
        int aid = 1234
        int aCardNumforWishlist
    }
}