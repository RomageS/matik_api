package com.integradora.matik.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
public class dateDto {

    private Integer id;
    private LocalDate date;
    private String description;

    public dateDto() {
    }

    public dateDto(Integer id, LocalDate date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

}
