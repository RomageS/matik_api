package com.integradora.matik.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
@Entity
@Table(name = "reservations")
@AllArgsConstructor
@NoArgsConstructor
public class ReserveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "fecha_regreso", nullable = false)
    private LocalDate fechaRegreso;

    @ManyToOne(fetch = FetchType.LAZY) // Muchas reservas pueden estar asociadas a un usuario
    @JoinColumn(name = "user_id", nullable = false) // Columna de clave foránea en la tabla `reservations`
    private userEntity user;

    @ManyToOne(fetch = FetchType.LAZY) // Muchas reservas pueden estar asociadas a un vehículo
    @JoinColumn(name = "vehicle_id", nullable = false) // Columna de clave foránea en la tabla `reservations`
    private vehicleEntity vehicle;
}
