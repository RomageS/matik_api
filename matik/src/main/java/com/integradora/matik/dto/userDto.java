package com.integradora.matik.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder(setterPrefix = "with")
public class userDto {

    private Integer id;
    private String name;
    private String lastName;
    private String Password;
    private String email;
    private String address;
    private String image;
}