package com.integradora.matik.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder(setterPrefix = "with")
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor

public class userEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastName;
    private String Password;
    @Column(name="email_address", unique = true)
    private String email;
    private String address;
    private String image;

}