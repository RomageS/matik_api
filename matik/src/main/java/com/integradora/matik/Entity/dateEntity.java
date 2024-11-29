package com.integradora.matik.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "dates")
@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor

public class dateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", length = 255)
    private String description;

    public dateEntity(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }
}

