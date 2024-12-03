package com.integradora.matik.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder(setterPrefix = "with")
@Entity
@Table(name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor

public class vehicleEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    private String model;
    private String year;
    private String color;
    private String transmission;
    private double price_day;
    private Integer mileage;
    private String status;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String image;

}