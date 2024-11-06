package ca.mcgill.ecse321.GameOn.dto;

public class EmployeeRequestDto {
    
    private String email;
    private String name;
    private String password;
    private boolean isEmployed;

    public  String getEmail(){
        return email;
    }
    
    public  String getName(){
        return name;
    }

    public  String getPassword(){
        return password;
    }

    public  boolean getIsEmployed(){
        return isEmployed;
    }
}
