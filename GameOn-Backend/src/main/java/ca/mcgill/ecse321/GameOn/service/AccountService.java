package ca.mcgill.ecse321.GameOn.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.repository.PersonRepository;

import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.repository.CustomerRepository;

import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;

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
        if (aEmail == null || aEmail.trim().length() == 0 || aEmail.contains(" ") || aEmail.contains("@") == false || aEmail.contains(".") == false) {
            throw new IllegalArgumentException("Email is invalid");
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

        Customer customer = new Customer(aCardNum, aCardExpiryDate, BillingAddress);
        Person person = new Person(aEmail, aName, asciiEncryptedPassword, customer);

        customerRepository.save(customer);
        personRepository.save(person);
        return person;

    }

    /**
     * 
     * @param id
     */
    public Customer findCustomerByEmail(String email){
        if (id < 0) {
            throw new IllegalArgumentException("Id is invalid");
        }
        
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        return customer;
    }

    // public Employee createEmployee(){}




}