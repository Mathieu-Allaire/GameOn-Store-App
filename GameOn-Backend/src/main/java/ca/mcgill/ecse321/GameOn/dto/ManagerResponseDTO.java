package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.Person;
import ca.mcgill.ecse321.GameOn.model.Manager;

public class ManagerResponseDTO {
    private String email;
    private String name;


    // Jackson needs a default constructor
    public ManagerResponseDTO(){

    }

    public ManagerResponseDTO(Person person){
        this.email = person.getEmail();
        this.name = person.getName();

        Manager managerRole = (Manager) person.getRole(0);

    }

    public  String getEmail(){
        return email;
    }

    public  String getName(){
        return name;
    }

}