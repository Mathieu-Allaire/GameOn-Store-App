package ca.mcgill.ecse321.GameOn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GameOn.service.AccountService;
import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.dto.CustomerRequestDto;
import ca.mcgill.ecse321.GameOn.dto.CustomerResponseDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.GameOn.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Employee;

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
    public CustomerResponseDto findCustomerByEmail(@PathVariable String email){
        Person customer = accountService.findCustomerByEmail(email);
        return new CustomerResponseDto(customer);
    }

    /**
     * Return the Employee with the given email.
     *
     * @param email The primary key of the Employee to find.
     * @return The employee with the given email.
     */
    @GetMapping("/employee/{email}")
    public EmployeeResponseDto findEmployeeByEmail(@PathVariable String email){
        Person employee = accountService.findEmployeeByEmail(email);
        return new EmployeeResponseDto(employee);
    }

     /**
     * Create a customer.
     *
     * @param customer The customer to create
     * @return The created customer 
     */
    @PostMapping("/customer")
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto customer){
        Person createdCustomer = accountService.createCustomer(customer.getEmail(), customer.getName(), customer.getPassword(), customer.getCardNumber(), customer.getExpiracyDate(), customer.getBillingAddress());
        return new CustomerResponseDto(createdCustomer);
    }

    /**
     * Create an employee.
     *
     * @param employee The employee to create
     * @return The created employee 
     */
    @PostMapping("/employee")
    public EmployeeResponseDto createEmployee(@RequestBody EmployeeRequestDto employee){
        Person createdEmployee = accountService.createEmployee(employee.getEmail(), employee.getName());
        return new EmployeeResponseDto(createdEmployee);
    }



    
}
