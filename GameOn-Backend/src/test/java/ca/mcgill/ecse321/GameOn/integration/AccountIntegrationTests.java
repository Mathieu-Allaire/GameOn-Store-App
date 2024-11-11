package ca.mcgill.ecse321.GameOn.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;
import ca.mcgill.ecse321.GameOn.repository.WishlistLinkRepository;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;


import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.repository.EmployeeRepository;
import ca.mcgill.ecse321.GameOn.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeResponseDto;

import ca.mcgill.ecse321.GameOn.dto.CustomerRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CustomerResponseDto;
import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    //Attributes fot customer
    private static final String VALID_EMAIL = "bob@gmail.com"; // no spaces,contain @ and . 
    private static final String VALID_NAME = "Bob"; // at least one letter
    private static final String VALID_PASSWORD = "bob123456789"; // bigger than 8 characters
    private static final int VALID_CARD_NUM = 123; // larger than 0
    private static final Date VALID_DATE = Date.valueOf("2025-09-02"); // needs to be a date after today's date
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr"; // at least one character

    //Attributes for employee
    private static final Boolean VALID_IS_EMPLOYED = true;
    private static final String VALID_EMAIL_EMPLOYEE = "james@gmail.com"; // no spaces,contain @ and . 
    private static final String VALID_NAME_EMPLOYEE = "James"; // at least one letter

    @AfterAll
    public void clearDatabase() {
        personRepo.deleteAll();
        employeeRepository.deleteAll();
        customerRepo.deleteAll();
        cartRepository.deleteAll();
        
    }
    @Test
    @Order(1)
    public void testCreateValidCustomer(){
        //Create the wanted customerRequest
        CustomerRequestDto bob = new CustomerRequestDto(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);

        //ACT
        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customer", bob, CustomerResponseDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
        assertEquals(VALID_NAME, response.getBody().getName());
   
    }

    @Test
    @Order(2)
    public void testReadValidCustomer(){
        // Arrange
        String url = "/customer/" + VALID_EMAIL;

        // Act
        ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerResponseDto person = response.getBody();
        assertNotNull(person);
        assertEquals(VALID_NAME, person.getName());
        assertEquals(VALID_EMAIL, person.getEmail());

    }

    
    @Test
    @Order(3)
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

    @Test
    @Order(4)
    public void testReadValidEmployee(){
        // Arrange
        String url = "/employee/" + VALID_EMAIL_EMPLOYEE;

        // Act
        ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url, EmployeeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        EmployeeResponseDto person = response.getBody();
        assertNotNull(person);
        assertEquals(VALID_NAME_EMPLOYEE, person.getName());
        assertEquals(VALID_EMAIL_EMPLOYEE, person.getEmail());
        assertEquals(VALID_IS_EMPLOYED, person.getIsEmployed());

    }

    

    


    
}

