package ru.shtamov.project3.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Name shouldn't be empty.")
    @Size(min = 3, max = 30, message = "Name size should be between 3 and 30 symbols")
    private String name;

    public SensorDTO(String name) {
        this.name = name;
    }

    public SensorDTO(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
