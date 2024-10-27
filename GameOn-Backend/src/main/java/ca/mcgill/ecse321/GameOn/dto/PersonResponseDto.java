package ca.mcgill.ecse321.GameOn.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321.GameOn.model.Person;

public class PersonResponseDto {
    private int id;
    private String name;
    private String email;
    private LocalDate creationDate;

    public PersonResponseDto(Person person){
        this.id = person.getId();
        this.name = person.getName();
        this.email = person.getEmail();
        this.creationDate = person.getCreationDate().toLocalDate(); // this is a java.sql.Date, not a java.time.LocalDate
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
