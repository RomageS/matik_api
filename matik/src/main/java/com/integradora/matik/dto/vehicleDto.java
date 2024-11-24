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
    private String priceDay;
    private String mileage;
    private String status;


}