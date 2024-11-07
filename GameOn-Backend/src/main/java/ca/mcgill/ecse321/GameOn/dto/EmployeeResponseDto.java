package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.model.Employee;

public class EmployeeResponseDto {
    private String email;
    private String name;
    private String isEmployed;

    public EmployeeResponseDto(Person person){
        this.email = person.getEmail();
        this.name = person.getName();
        
        Employee employeeRole = (Employee) person.getRole(0);
        this.isEmployed = employeeRole.getIsEmployed() ? "true" : "false";

    }

    public  String getEmail(){
        return email;
    }
    
    public  String getName(){
        return name;
    }

    public  String getIsEmployed(){
        return isEmployed;
    }
}
