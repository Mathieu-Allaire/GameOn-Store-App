package ca.mcgill.ecse321.GameOn.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GameOn.service.AccountService;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.dto.CustomerRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CustomerResponseDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeResponseDto;



import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is the controller for the account related endpoints.
 * @author Camilo Berdugo
 */
@RestController
@CrossOrigin(origins = "http://localhost:8087")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Return the Customer with the given email.
     *
     * @param email The primary key of the customer to find.
     * @return The customer with the given email.
     */
    @GetMapping("/customer/{email}")
    public ResponseEntity<?> findCustomerByEmail(@PathVariable String email){
        try {
            Person customer = accountService.findCustomerByEmail(email);
            return new ResponseEntity<>(new CustomerResponseDto(customer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Return the Employee with the given email.
     * @param email The primary key of the Employee to find.
     * @return The employee with the given email.
     */
    @GetMapping("/employee/{email}")
    public ResponseEntity<?> findEmployeeByEmail(@PathVariable String email){
        try {
            Person employee = accountService.findEmployeeByEmail(email);
            return new ResponseEntity<>(new EmployeeResponseDto(employee), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

     /**
     * Create a customer.
     *
     * @param customer The customer to create
     * @return The created customer 
     */
    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequestDto customer){
        try {
            //int cardNumber = Integer.parseInt(customer.getCardNumber());
           // Date expiryDate = Date.valueOf(customer.getExpiracyDate());
            Person createdCustomer = accountService.createCustomer(customer.getEmail(), customer.getName(), customer.getPassword(), customer.getCardNumber(), customer.getExpiracyDate(), customer.getBillingAddress());
            return new ResponseEntity<>(new CustomerResponseDto(createdCustomer), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create an employee.
     *
     * @param employee The employee to create
     * @return The created employee 
     */
    @PostMapping("/employee")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequestDto employee){
        try {
            Person createdEmployee = accountService.createEmployee(employee.getEmail(), employee.getName());
            return new ResponseEntity<>(new EmployeeResponseDto(createdEmployee), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * deactivate the account of an employee
     *
     * @param email The primary key of the Employee to deactivate.
     * @return true if the employee was deactivated
     */
    @PutMapping("/employee/deactivate/{email}")
    public ResponseEntity<?> deactivateEmployeeByEmail(@PathVariable String email){
        try {
            Boolean deactivated = accountService.deactivateEmployee(email);
            return new ResponseEntity<>(deactivated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Return the email and if the user is logged in
     * @param email The primary key of the Employee to find.
     * @return The employee with the given email.
     */
    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<?> logIn(@PathVariable String email, @PathVariable String password){
        try {
            Integer response = accountService.logIn(email, password);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employee")
    public ResponseEntity<?> findAllEmployees(){
        List<EmployeeResponseDto> dtos = new ArrayList<>();
        try {
            for (Person employee : accountService.getAllEmployees()) {
                dtos.add(new EmployeeResponseDto(employee));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
