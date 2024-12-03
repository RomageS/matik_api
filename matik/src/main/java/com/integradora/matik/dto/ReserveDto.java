package com.integradora.matik.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
public class ReserveDto {
    private Integer id;
    private LocalDate fechaEntrega;
    private LocalDate fechaRegreso;
    @JsonProperty("usuario")
    private Integer userId; // ID del usuario que realiza la reserva
    @JsonProperty("vehiculoId")
    private Integer vehicleId; // ID del vehículo reservado
}
