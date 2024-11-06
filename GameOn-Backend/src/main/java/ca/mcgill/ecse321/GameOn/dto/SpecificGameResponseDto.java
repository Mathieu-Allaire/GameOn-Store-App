package ca.mcgill.ecse321.GameOn.dto;

import ca.mcgill.ecse321.GameOn.model.SpecificGame;

public class SpecificGameResponseDto {

    private int id;

    @SuppressWarnings("unused")
    private SpecificGameResponseDto() {
    }

    public SpecificGameResponseDto(SpecificGame model) {
        this.id = model.getId();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
