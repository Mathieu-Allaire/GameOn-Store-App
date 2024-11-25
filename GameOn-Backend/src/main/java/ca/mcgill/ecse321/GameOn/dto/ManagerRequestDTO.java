package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ManagerRequestDTO {

    @NotBlank(message = "Email address is required.")
    @Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message = "Name is required.")
    private String name;


    public ManagerRequestDTO(String email, String name){
        this.email = email;
        this.name = name;
    }

    public  String getEmail(){
        return email;
    }

    public  String getName(){
        return name;
    }



}