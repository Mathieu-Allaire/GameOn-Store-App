package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Person;

public class CustomerResponseDto {
    private String email;
    private String name;

    // Jackson needs a default constructor
    protected CustomerResponseDto(){
    }

    public CustomerResponseDto(Person person){
        this.email = person.getEmail();
        this.name = person.getName();
    }

    public  String getEmail(){
        return email;
    }

    public  String getName(){
        return name;
    }    
}
