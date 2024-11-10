package ca.mcgill.ecse321.GameOn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GameOn.service.AccountService;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.dto.CustomerRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CustomerResponseDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.GameOn.dto.GameCreateDto;
import ca.mcgill.ecse321.GameOn.dto.GameResponseDTO;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Employee;
import ca.mcgill.ecse321.GameOn.model.Game;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.sql.Date;

@RestController
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
     *
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
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        try {
            Person customer = accountService.createCustomer(
                customerRequestDto.getEmail(),
                customerRequestDto.getName(),
                customerRequestDto.getPassword(),
                customerRequestDto.getCardNumber(),
                customerRequestDto.getExpiracyDate(),
                customerRequestDto.getBillingAddress()
            );
            CustomerResponseDto response = new CustomerResponseDto(customer);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
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

}
