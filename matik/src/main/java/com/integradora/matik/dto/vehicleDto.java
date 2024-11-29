package com.integradora.matik.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class vehicleDto {

    private Integer id;
    private String brand;
    private String model;
    private String year;
    private String color;
    private String transmission;
    private double price_day;
    private Integer mileage;
    private String status;
    private String image;


}