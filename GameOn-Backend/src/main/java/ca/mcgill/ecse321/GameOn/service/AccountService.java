package ca.mcgill.ecse321.GameOn.service;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.GameOn.exception.GameOnException;
import ca.mcgill.ecse321.GameOn.model.*;
import ca.mcgill.ecse321.GameOn.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
/**
 * This is the Service for the customer and employee clasess
 *
 * @author Camilo Berdugo and Luis Jarquin
 */

@Service
public class AccountService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private CartRepository cartRepository;

    /**
     * This method will create a Person with role of customer
     * @param aEmail
     * @param aName
     * @param aPassword
     * @param aCardNum
     * @param aCardExpiryDate
     * @param BillingAddress
     * @throws IllegalArgumentException if the parameters are incorrect
     */
    @Transactional
    public Person createCustomer(String aEmail, String aName, String aPassword, int aCardNum, Date aCardExpiryDate, String BillingAddress){
        if (aEmail == null || aEmail.trim().length() == 0 || aEmail.contains(" ") || !aEmail.contains("@") || !aEmail.contains(".")) {
            throw new GameOnException(HttpStatus.BAD_REQUEST,"Email is invalid");
        }
        //email cannot be null  
        if (personRepository.findPersonByEmail(aEmail) != null) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }
        //verifies name
        if (aName == null || aName.trim().length() == 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Name is invalid");
        }
        //verifies password
        if (aPassword == null || aPassword.trim().length() == 0 || aPassword.length() < 8) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Password is invalid");
        }
        //verifies card number
        if (aCardNum < 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST,"Card number is invalid");
        }
        //verifies date
        Date currentDate = Date.valueOf(LocalDate.now());
        if (aCardExpiryDate == null || aCardExpiryDate.before(currentDate)) {
            throw new GameOnException(HttpStatus.BAD_REQUEST,"Card expiry date is invalid");
        }

        if (BillingAddress == null || BillingAddress.trim().length() == 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST,"Billing address is invalid");
        }

        // ENCRYPT THE PASSWORD OUR OWN WAY
        // flip the password chars
        String encryptedPassword = "";
        for (int i = aPassword.length() - 1; i >= 0; i--) {
            encryptedPassword += aPassword.charAt(i);
        }

        // encryptedPassword to ascii multiplied by two as a string seperated by commas
        String asciiEncryptedPassword = "";
        for (int i = 0; i < encryptedPassword.length(); i++) {
            asciiEncryptedPassword += (int) encryptedPassword.charAt(i) * 2;
            if (i != encryptedPassword.length() - 1) {
                asciiEncryptedPassword += ",";
            }
        }
    

        Cart cart = new Cart();
        cartRepository.save(cart);
        Customer customer = new Customer(aCardNum, aCardExpiryDate, BillingAddress,cart);
        customerRepository.save(customer);
        Person person = new Person(aEmail, aName, asciiEncryptedPassword, customer);
    
        return personRepository.save(person);

    }


    /**
     * This method will find a Person with role customer
     * @param email
     * @throws IllegalArgumentException if the email is incorrect, the customer is not found or the person found is not a customer
     */
    public Person findCustomerByEmail(String email){
        //Verify email
        if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }
        //search customer
        Person customer = personRepository.findPersonByEmail(email);
        if (customer == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        //if the person is not a customer
        if(personRepository.findPersonByEmail(email).getRole(0).getClass() != Customer.class){
            throw new GameOnException(HttpStatus.NOT_FOUND, "No customer with this email");
        }

        return customer;
    }
    /**
     * This method will create a person with role of an Employee
     * @param aEmail
     * @param aName
     * @throws IllegalArgumentException if the email or name are inccorect or if the employee email already exists in the system
     */
    @Transactional
    public Person createEmployee(String aEmail, String aName){
        //Make sure we got a correct email 
        if (aEmail == null || aEmail.trim().length() == 0 || aEmail.contains(" ") || aEmail.contains("@") == false || aEmail.contains(".") == false) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }
        //Make sure the name is not empty
        if (aName == null || aName.trim().length() == 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Name is invalid");
        }

        //Make sure no repeated emails
        if (personRepository.findPersonByEmail(aEmail) != null) {
            throw new GameOnException(HttpStatus.CONFLICT, "Email is already taken");
        }

        Employee employeeRole = new Employee(true);
        employeeRepository.save(employeeRole);
        
        String genericPassword = "GameOn123!";
        Person employee = new Person(aEmail, aName, genericPassword, employeeRole);
        String encryptedPassword = employee.getEncryptedPassword(genericPassword);
        employee.setPassword(encryptedPassword); // this sets the generic password into an encrypted password
        personRepository.save(employee);

        return employee;
    }
    @Transactional
    public Person createManager(String aEmail, String aName){
        //Make sure we got a correct email
        if (aEmail == null || aEmail.trim().length() == 0 || aEmail.contains(" ") || aEmail.contains("@") == false || aEmail.contains(".") == false) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }
        //Make sure the name is not empty
        if (aName == null || aName.trim().length() == 0) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Name is invalid");
        }

        //Make sure no repeated emails
        if (personRepository.findPersonByEmail(aEmail) != null) {
            throw new GameOnException(HttpStatus.CONFLICT, "Email is already taken");
        }

        Manager managerRole = new Manager();
        managerRepository.save(managerRole);

        String genericPassword = "GameOn123!";
        Person manager = new Person(aEmail, aName, genericPassword, managerRole);
        String encryptedPassword = manager.getEncryptedPassword(genericPassword);
        manager.setPassword(encryptedPassword); // this sets the generic password into an encrypted password
        personRepository.save(manager);

        return manager;
    }
    /**
     * This method will find a Person with role employee
     * @param email
     * @throws IllegalArgumentException if the email is incorrect, the employee is not found or the person found is not a employee
     */
    public Person findEmployeeByEmail(String email){
        if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }
        
        Person employee = personRepository.findPersonByEmail(email);
        if (employee == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "Employee not found");
        }

        if(personRepository.findPersonByEmail(email).getRole(0).getClass() != Employee.class){
            throw new GameOnException(HttpStatus.NOT_FOUND, "No employee with this email");
        }
    
        return employee;

    }
    public Person findManagerByEmail(String email){
        if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
            throw new GameOnException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }

        Person manager = personRepository.findPersonByEmail(email);
        if (manager == null) {
            throw new GameOnException(HttpStatus.NOT_FOUND, "Manager not found");
        }

        if(personRepository.findPersonByEmail(email).getRole(0).getClass() != Manager.class){
            throw new GameOnException(HttpStatus.NOT_FOUND, "No Manager with this email");
        }

        return manager;

    }
    /**
     * This method will deactivate an employee
     * @param email
     * @throws IllegalArgumentException if the email is incorrect, the employee is not found or the person found is not a employee
     */
    public boolean deactivateEmployee(String email){
        try{
            if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
                throw new GameOnException(HttpStatus.BAD_REQUEST, "Email is invalid");
            }

            Person employee = personRepository.findPersonByEmail(email);
            if (employee == null) {
                throw new GameOnException(HttpStatus.NOT_FOUND, "Employee not found");
            }

            if(personRepository.findPersonByEmail(email).getRole(0).getClass() != Employee.class){
                throw new GameOnException(HttpStatus.NOT_FOUND, "No employee with this email");
            }

            Employee employeeRole = (Employee) employee.getRole(0);
            employeeRole.setIsEmployed(false);

            employeeRepository.save(employeeRole);
            personRepository.save(employee);

            return true;
        } catch (Exception e){
            return false;
        }
    }

    /* Log in will be implemented later!
    public boolean logIn(String email, String password){

        if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
            throw new IllegalArgumentException("Email is invalid");
        }

        if (password == null || password.trim().length() == 0 || password.length() < 8) {
            throw new IllegalArgumentException("Password is invalid");
        }

        Person person = personRepository.findPersonByEmail(email);
        if (person == null) {
            throw new IllegalArgumentException("Person not found");
        }

        String encryptedPassword = "";
        for (int i = password.length() - 1; i >= 0; i--) {
            encryptedPassword += password.charAt(i);
        }

        String asciiEncryptedPassword = "";
        for (int i = 0; i < encryptedPassword.length(); i++) {
            asciiEncryptedPassword += (int) encryptedPassword.charAt(i) * 2;
            if (i != encryptedPassword.length() - 1) {
                asciiEncryptedPassword += ",";
            }
        }

        if (person.getPassword().equals(asciiEncryptedPassword)) {
            if (person.getRole(0).getClass() == Customer.class) {
                GameOnApplication.LoggedInAsCustomer = true;
            } else if (person.getRole(0).getClass() == Employee.class) {
                GameOnApplication.LoggedInAsEmployee = true;
            } else {
                GameOnApplication.LoggedInAsAdmin = true;
            }
            return true;
        }

        return false;
    }*/


}