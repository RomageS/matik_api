package com.integradora.matik.service;

import com.integradora.matik.Entity.dateEntity;
import com.integradora.matik.dto.dateDto;
import com.integradora.matik.repository.dateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class dateService {

    private final dateRepo DateRepo;
    @Autowired
    public dateService(dateRepo DateRepo) {
        this.DateRepo = DateRepo;
    }


    // Guardar una nueva fecha
    public Integer saveDate(dateDto dateDto) {
        try {
            dateEntity dateEntity = dtoToEntity(dateDto);
            return DateRepo.save(dateEntity).getId();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error al guardar la fecha. Datos duplicados.");
        } catch (Exception e) {
            throw new IllegalStateException("Ocurrió un error al guardar la fecha.");
        }
    }

    // Obtener todas las fechas y convertirlas a DTO
    public List<dateDto> findAllDates() {
        return DateRepo.findAll().stream()
                .map(this::entityToDto) // Convierte cada entidad a DTO
                .collect(Collectors.toList());
    }

    // Buscar una fecha por ID y convertirla a DTO
    public dateDto findDateById(Integer id) {
        Optional<dateEntity> optionalDate = DateRepo.findById(id);
        return optionalDate.map(this::entityToDto).orElse(null); // Devuelve DTO o null si no se encuentra
    }

    // Eliminar una fecha por ID
    public boolean deleteDateById(Integer id) {
        Optional<dateEntity> optionalDate = DateRepo.findById(id);

        if (optionalDate.isPresent()) {
            DateRepo.deleteById(id);
            return true; // Indica que la fecha fue eliminada exitosamente
        }

        return false; // Indica que no se encontró la fecha con el ID especificado
    }

    // Actualizar una fecha existente
    public dateDto updateDate(Integer id, dateDto dateDto) {
        Optional<dateEntity> optionalDate = DateRepo.findById(id);

        if (optionalDate.isPresent()) {
            dateEntity dateEntity = optionalDate.get();

            // Actualiza los campos de la entidad
            dateEntity.setDate(dateDto.getDate());
            dateEntity.setDescription(dateDto.getDescription());

            // Guarda los cambios en la base de datos
            dateEntity updatedEntity = DateRepo.save(dateEntity);

            // Convierte la entidad actualizada a DTO y la retorna
            return entityToDto(updatedEntity);
        }

        return null; // Si la fecha no se encuentra, retorna null
    }

    // Conversión de DTO a Entidad
    private dateEntity dtoToEntity(dateDto dateDto) {
        return new dateEntity(
                dateDto.getDate(),
                dateDto.getDescription()
        );
    }

    // Conversión de Entidad a DTO
    private dateDto entityToDto(dateEntity dateEntity) {
        return new dateDto(
                dateEntity.getId(),
                dateEntity.getDate(),
                dateEntity.getDescription()
        );
    }
}

