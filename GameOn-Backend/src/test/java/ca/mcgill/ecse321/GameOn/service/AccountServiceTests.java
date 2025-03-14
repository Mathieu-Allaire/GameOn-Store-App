package ca.mcgill.ecse321.GameOn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;

import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;

import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.repository.EmployeeRepository;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;

/**
 * This are the tests for the AccountService, which uses Customer and Employees classes
 *
 * @author Camilo Berdugo
 */

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AccountServiceTests {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private AccountService accountService;

    private static final String VALID_EMAIL = "bob@gmail.com"; // no spaces,contain @ and . 
    private static final String VALID_NAME = "Bob"; // at least one letter
    private static final String VALID_PASSWORD = "bob123456789"; // bigger than 8 characters

    private static final String INVALID_EMAIL = "bobgmail.com"; //Does not have @
    private static final String INVALID_PASSWORD = "hey"; //less than 8 characters

    //Attributes fot customer
    private static final int VALID_CARD_NUM = 123; // larger than 0
    private static final Date VALID_DATE = Date.valueOf("2025-09-02"); // needs to be a date after today's date
    private static final String VALID_BILLING_ADDRESS = "23 frjjrfngr"; // at least one character

    private static final int INVALID_CARD_NUM = 0; 
    private static final Date INVALID_DATE = Date.valueOf("2001-09-02"); 
    private static final String INVALID_BILLING_ADDRESS = ""; 


    //For creating employees, we only need the name and email, the password and isEmployed is automatically created for them 
    

    @Test
    public void testCreateValidCustomer(){
        //Arrange 
        // Whenever we save personRepositoy.save(person) -> it will return person, same for customer and cart
        when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(cartRepository.save(any(Cart.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(null);

        //Act
        Person createdCustomer = accountService.createCustomer(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);

        //Assert
        assertNotNull(createdCustomer);
        assertEquals(VALID_NAME, createdCustomer.getName());
        assertEquals(VALID_EMAIL, createdCustomer.getEmail());

        //The password saved is encrypted, so we need to compare the encrypted password
        String encryptedPassword = createdCustomer.getEncryptedPassword(VALID_PASSWORD);
        assertEquals(encryptedPassword, createdCustomer.getPassword()); 
        
        //Check the customer class atributes
        Customer customerRole = (Customer) createdCustomer.getRole(0);
        assertEquals(VALID_CARD_NUM, customerRole.getCardNum()); 
        assertEquals(VALID_DATE, customerRole.getCardExpiryDate()); 
        assertEquals(VALID_BILLING_ADDRESS, customerRole.getBillingAddress()); 
        //Verifiy we saved bob only once 
        verify(personRepository, times(1)).save(createdCustomer);
    }

    @Test
    public void testCreateInvalidCustomerEmail(){
        //Try creating a customer with invalid email
        try {
            accountService.createCustomer(INVALID_EMAIL, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerEmptyEmail(){
        //Try creating a customer with empty email
        try {
            accountService.createCustomer("", VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerContainSpacesEmail(){
        //Try creating a customer with invalid email (has spaces)
        try {
            accountService.createCustomer("casui@   feefnief.com", VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerEmailIsNull(){
        //Try creating a customer with null email
        try {
            accountService.createCustomer(null, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerEmailnoPoint(){
        //Try creating a customer with invalid email (has no point)
        try {
            accountService.createCustomer("camilo@mcgillcom", VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Email is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerCardNum(){
        //Try creating a customer with invalid CardNum
        try {
            accountService.createCustomer(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, -1, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Card number is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerName(){
        //Try creating a customer with empty Name
        try {
            accountService.createCustomer(VALID_EMAIL, "", VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Name is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerExpiracyDate(){
        //Try creating a customer with invalid ExpiracyDate
        try {
            accountService.createCustomer(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, INVALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Card expiry date is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerBillingAdress(){
        //Try creating a customer with invalid BillingAdress
        try {
            accountService.createCustomer(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, INVALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Billing address is invalid", e.getMessage());
        }
    }

    @Test
    public void testCreateCustomerAlreadyExistingEmail(){
        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // this simulates the create customer

        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);// Case when we want to create a customer, but there is already an account with that email
        
        try {
            accountService.createCustomer(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Email is already taken", e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidCustomerPassword(){
        //Try creating a customer with an invalid password
        try {
            accountService.createCustomer(VALID_EMAIL, VALID_NAME, INVALID_PASSWORD, VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS);
        } catch (Exception e) {
        assertEquals("Password is invalid", e.getMessage());
        }
    }

    @Test
    public void testReadCustomerByValidEmail(){
        //Arrange 
        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // this simulates the create customer
        

        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        //Act : find customer
        Person wantedCustomer = accountService.findCustomerByEmail(VALID_EMAIL);

        //Assert
        assertNotNull(wantedCustomer);
        assertEquals(bob.getName(), wantedCustomer.getName());
        assertEquals(bob.getEmail(), wantedCustomer.getEmail());

        //The password saved is encrypted, so we need to compare the encrypted password
        assertEquals(encryptedPassword, wantedCustomer.getPassword()); 
        
        //Check the customer class atributes
        Customer wantedCustomerRole = (Customer) wantedCustomer.getRole(0);

        assertEquals(customerRole.getCardNum(), wantedCustomerRole.getCardNum());  // customerRole is bob's role
        assertEquals(customerRole.getCardExpiryDate(), wantedCustomerRole.getCardExpiryDate()); 
        assertEquals(customerRole.getBillingAddress(), wantedCustomerRole.getBillingAddress()); 
        assertEquals(customerRole.getCart().getId(), wantedCustomerRole.getCart().getId()); 
    }

    @Test
    public void testReadCustomerByEmailNotFound(){
        //Arrange 
        Cart cart = new Cart();
        Customer customerRole = new Customer(INVALID_CARD_NUM, INVALID_DATE, INVALID_BILLING_ADDRESS, cart);
        customerRole.setBillingAddress(VALID_BILLING_ADDRESS);
        customerRole.setCardNum(VALID_CARD_NUM);
        customerRole.setCardExpiryDate(VALID_DATE);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // this simulates the create customer
        
        //Case when there is no person with that email
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(null);
        try {
           accountService.findCustomerByEmail(VALID_EMAIL);
        } catch (Exception e) {
        assertEquals("Customer not found", e.getMessage());
        }
    }

    @Test
    public void testReadCustomerByInvalidRole(){
        //Arrange 
        new Cart();
        Employee employeeRole = new Employee(true);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, employeeRole);// This simulates the creation of an employee
        
        //Case when the person found is an employee and not a customer
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        try {
           accountService.findCustomerByEmail(VALID_EMAIL);
        } catch (Exception e) {
        assertEquals("No customer with this email", e.getMessage());
        }
    }

    //EMPLOYEES TESTS

    @Test
    public void testCreateValidEmployee(){
        //Arrange 
        when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(employeeRepository.save(any(Employee.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        //Act
        Person createdEmployee = accountService.createEmployee(VALID_EMAIL, VALID_NAME);

        //Assert
        assertNotNull(createdEmployee);
        assertEquals(VALID_NAME, createdEmployee.getName());
        assertEquals(VALID_EMAIL, createdEmployee.getEmail());

        //The password saved is encrypted, so its need to be the same as the basePassword : GameOn123
        String preSetPassword = "GameOn123!";
        String encryptedPassword = createdEmployee.getEncryptedPassword(preSetPassword);
        assertEquals(encryptedPassword, createdEmployee.getPassword()); //Check if the password saved is the encrypted version
        
        //Check the employee class atributes
        Employee employeeRole = (Employee) createdEmployee.getRole(0);
        assertEquals(true, employeeRole.getIsEmployed()); //When we create an employee it is always true
        //Verifiy we saved once
        verify(personRepository, times(1)).save(createdEmployee);
    }

    @Test
    public void testCreateInvalidEmployeeAlreadyCreated(){
        //Arrange 
        //Create an existing employee
        new Cart();
        Employee employeeRole = new Employee(true);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, employeeRole);
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob); // Bob will be already created in the data base

        //Test if it's possible to create a new employee if there is an existing email in the data base.
        try {
            accountService.createEmployee(VALID_EMAIL, VALID_NAME);
         } catch (Exception e) {
         assertEquals("Email is already taken", e.getMessage());
         }

    }

    @Test
    public void testCreateInvalidEmployeeEmail(){

        //Test if it's possible to create a new employee with an invalid email
        try {
            accountService.createEmployee(INVALID_EMAIL, VALID_NAME);
         } catch (Exception e) {
         assertEquals("Email is invalid", e.getMessage());
         }

    }

    @Test
    public void testCreateInvalidEmployeeSpacesEmail(){

        //Test if it's possible to create a new employee with another type of invalid email
        try {
            accountService.createEmployee("bo b", VALID_NAME);
         } catch (Exception e) {
         assertEquals("Email is invalid", e.getMessage());
         }

    }

    @Test
    public void testCreateInvalidEmployeeEmptyEmail(){

        //Test if it's possible to create a new employee with an empty email
        try {
            accountService.createEmployee("", VALID_NAME);
         } catch (Exception e) {
         assertEquals("Email is invalid", e.getMessage());
         }

    }

    @Test
    public void testCreateInvalidEmployeeName(){

        //Test if it's possible to create a new employee with an null name
        try {
             accountService.createEmployee(VALID_EMAIL, null);
         } catch (Exception e) {
         assertEquals("Name is invalid", e.getMessage());
         }

    }

    @Test
    public void testReadEmployeeByValidEmail(){
        //Arrange 
        //Create an employee
        Employee employeeRole = new Employee(true);
        String preSetPassword = "GameOn123!";
        Person bob = new Person(VALID_EMAIL, VALID_NAME, preSetPassword, employeeRole);
        String encryptedPassword = bob.getEncryptedPassword(preSetPassword);
        bob.setPassword(encryptedPassword);
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        //Act : find employee
        Person wantedEmployee = accountService.findEmployeeByEmail(VALID_EMAIL);

        //Assert
        assertNotNull(wantedEmployee);
        assertEquals(bob.getName(), wantedEmployee.getName());
        assertEquals(bob.getEmail(), wantedEmployee.getEmail());

        //The password saved is encrypted, so we need to compare the encrypted password
        
        assertEquals(encryptedPassword, wantedEmployee.getPassword()); 
        
        //Check the customer class atributes
        Employee wantedEmployeeRole = (Employee) wantedEmployee.getRole(0);

        assertEquals(true, wantedEmployeeRole.getIsEmployed());  //Employee role attribute
    }

    @Test
    public void testReadEmployeeByInvalidEmail(){
        //Arrange 
        //Create an employee
        Employee employeeRole = new Employee(true);
        String preSetPassword = "GameOn123!";
        Person bob = new Person(VALID_EMAIL, VALID_NAME, preSetPassword, employeeRole);
        String encryptedPassword = bob.getEncryptedPassword(preSetPassword);
        bob.setPassword(encryptedPassword);
        
        //CASE when there is no employee with the given email
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(null);

        //Act : find employee
        try {
            accountService.findEmployeeByEmail(VALID_EMAIL);
         } catch (Exception e) {
         assertEquals("Employee not found", e.getMessage());
         }
        

    }

    @Test
    public void testDesactivateEmployee(){
        //Arrange 
        //Create an employee
        Employee employeeRole = new Employee(true);
        String preSetPassword = "GameOn123!";
        Person bob = new Person(VALID_EMAIL, VALID_NAME, preSetPassword, employeeRole);
        String encryptedPassword = bob.getEncryptedPassword(preSetPassword);
        bob.setPassword(encryptedPassword);
        
        //CASE where bob is an existing employee
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);
        when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        when(employeeRepository.save(any(Employee.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        //Act: fire employee
        accountService.deactivateEmployee(VALID_EMAIL);
        
        assertEquals(employeeRole.isIsEmployed(), false);
        verify(personRepository, times(1)).save(bob);
        verify(employeeRepository, times(1)).save(employeeRole);
    }

    @Test
    public void testDeactivateIncorrectEmployee(){
        
        //CASE when there is no employee with the given email
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(null);

        //Act : find employee
        try {
            accountService.deactivateEmployee(VALID_EMAIL);
         } catch (Exception e) {
         assertEquals("Employee not found", e.getMessage());
         }
        

    }

    @Test
    public void testDeactivateEmployeeWrongPerson(){
        
        //CASE we want to deactivate a customer
        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // this simulates the create customer
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        //Act : Deactivate the supposed employee
        try {
            accountService.deactivateEmployee(VALID_EMAIL);
         } catch (Exception e) {
         assertEquals("No employee with this email", e.getMessage());
         }
        

    }

    @Test
    public void ValidLogInEmployee(){
        //Arrange 
        //Create an employee
        Employee employeeRole = new Employee(true);
        String preSetPassword = "GameOn123!";
        Person bob = new Person(VALID_EMAIL, VALID_NAME, preSetPassword, employeeRole);
        String encryptedPassword = bob.getEncryptedPassword(preSetPassword);
        bob.setPassword(encryptedPassword);
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        //Act : find employee
        Integer response = accountService.logIn(VALID_EMAIL, preSetPassword);

        //Assert
        assertNotNull(response);
        assertEquals(2, response);
    }

    @Test
    public void ValidLogInCustomer(){
        //Arrange simulates creation customer
        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // this simulates the create customer
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        //Act : see if it logged in
        Integer response = accountService.logIn(VALID_EMAIL, VALID_PASSWORD);

        //Assert
        assertNotNull(response);
        assertEquals(1, response);
    }

    @Test
    public void InvalidLogInCustomer(){
        //Arrange simulates creation customer
        Cart cart = new Cart();
        Customer customerRole = new Customer(VALID_CARD_NUM, VALID_DATE, VALID_BILLING_ADDRESS, cart);
        Person bob = new Person(VALID_EMAIL, VALID_NAME, VALID_PASSWORD, customerRole);
        String encryptedPassword = bob.getEncryptedPassword(VALID_PASSWORD);
        bob.setPassword(encryptedPassword); // this simulates the create customer
        
        when(personRepository.findPersonByEmail(VALID_EMAIL)).thenReturn(bob);

        //Case when wrong password
        try {
            Integer response = accountService.logIn(VALID_EMAIL, "hell324243o");
         } catch (Exception e) {
         assertEquals("Incorrect password", e.getMessage());
         }
    }


    







    
}
