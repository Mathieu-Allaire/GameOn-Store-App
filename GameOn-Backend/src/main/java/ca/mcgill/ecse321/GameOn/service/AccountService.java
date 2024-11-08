package ca.mcgill.ecse321.GameOn.service;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.GameOn.model.Cart;
import ca.mcgill.ecse321.GameOn.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;

import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;

import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.repository.EmployeeRepository;

import ca.mcgill.ecse321.GameOn.GameOnApplication;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CartRepository cartRepository;

    /**
     * 
     * @param aEmail
     * @param aName
     * @param aPassword
     * @param aCardNum
     * @param aCardExpiryDate
     * @param BillingAddress
     */
    @Transactional
    public Person createCustomer(String aEmail, String aName, String aPassword, int aCardNum, Date aCardExpiryDate, String BillingAddress){
        if (aEmail == null || aEmail.trim().length() == 0 || aEmail.contains(" ") || !aEmail.contains("@") || !aEmail.contains(".")) {
            throw new IllegalArgumentException("Email is invalid");
        }

        if (personRepository.findPersonByEmail(aEmail) != null) {
            throw new IllegalArgumentException("Email is already taken");
        }


        if (aName == null || aName.trim().length() == 0) {
            throw new IllegalArgumentException("Name is invalid");
        }

        if (aPassword == null || aPassword.trim().length() == 0 || aPassword.length() < 8) {
            throw new IllegalArgumentException("Password is invalid");
        }

        if (aCardNum < 0) {
            throw new IllegalArgumentException("Card number is invalid");
        }

        Date currentDate = Date.valueOf(LocalDate.now());
        if (aCardExpiryDate == null || aCardExpiryDate.before(currentDate)) {
            throw new IllegalArgumentException("Card expiry date is invalid");
        }

        if (BillingAddress == null || BillingAddress.trim().length() == 0) {
            throw new IllegalArgumentException("Billing address is invalid");
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
        // TODO: remove this comment
        // This is how we decrypt the password
        // String reverseDecryptedPassword = "";
        // String[] asciiEncryptedPasswordArray = asciiEncryptedPassword.split(",");
        // for (int i = 0; i < asciiEncryptedPasswordArray.length; i++) {
        //     reverseDecryptedPassword += (char) (Integer.parseInt(asciiEncryptedPasswordArray[i]) / 2);
        // }

        // String decryptedPassword = "";
        // for (int i = reverseDecryptedPassword.length() - 1; i >= 0; i--) {
        //     decryptedPassword += reverseDecryptedPassword.charAt(i);
        // }

        Cart cart = new Cart();
        cartRepository.save(cart);
        Customer customer = new Customer(aCardNum, aCardExpiryDate, BillingAddress,cart);
        customerRepository.save(customer);
        Person person = new Person(aEmail, aName, asciiEncryptedPassword, customer);
    
        return personRepository.save(person);

    }

    /**
     * 
     * @param email
     */
    public Person findCustomerByEmail(String email){
        if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
            throw new IllegalArgumentException("Email is invalid");
        }
        
        Person customer = personRepository.findPersonByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        if(personRepository.findPersonByEmail(email).getRole(0).getClass() != Customer.class){
            throw new IllegalArgumentException("No customer with this email");
        }

        return customer;
    }

    public Person createEmployee(String aEmail, String aName){
        //Make sure we got a correct email 
        if (aEmail == null || aEmail.trim().length() == 0 || aEmail.contains(" ") || aEmail.contains("@") == false || aEmail.contains(".") == false) {
            throw new IllegalArgumentException("Email is invalid");
        }
        //Make sure the name is not empty
        if (aName == null || aName.trim().length() == 0) {
            throw new IllegalArgumentException("Name is invalid");
        }

        //Make sure no repeated emails
        if (personRepository.findPersonByEmail(aEmail) != null) {
            throw new IllegalArgumentException("Email is already taken");
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

    public Person findEmployeeByEmail(String email){
        if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
            throw new IllegalArgumentException("Email is invalid");
        }
        
        Person employee = personRepository.findPersonByEmail(email);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        if(personRepository.findPersonByEmail(email).getRole(0).getClass() != Employee.class){
            throw new IllegalArgumentException("No employee with this email");
        }
    
        return employee;

    }

    public boolean deactivateEmployee(String email){
        try{
            if (email == null || email.trim().length() == 0 || email.contains(" ") || email.contains("@") == false || email.contains(".") == false) {
                throw new IllegalArgumentException("Email is invalid");
            }

            Person employee = personRepository.findPersonByEmail(email);
            if (employee == null) {
                throw new IllegalArgumentException("Employee not found");
            }

            if(personRepository.findPersonByEmail(email).getRole(0).getClass() != Employee.class){
                throw new IllegalArgumentException("No employee with this email");
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
    }

}