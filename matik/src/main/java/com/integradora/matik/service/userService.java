package com.integradora.matik.service;

import org.springframework.dao.DataIntegrityViolationException;
import com.integradora.matik.Entity.userEntity;
import com.integradora.matik.dto.userDto;
import com.integradora.matik.repository.userRepo;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userService {

    public userDto login(String email, String password) {
        // Buscar usuario por correo
        Optional<userEntity> optionalUser = UserRepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            userEntity user = optionalUser.get();

            // Validar contraseña
            if (user.getPassword().equals(password)) {
                // Retorna el usuario como DTO si las credenciales son correctas
                return entityToDto(user);
            } else {
                throw new IllegalArgumentException("Contraseña incorrecta");
            }
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    private final userRepo UserRepo;

    public userService(userRepo UserRepo) {
        this.UserRepo = UserRepo;
    }

    public Integer save(userDto UserDto) {
        try {
            // Verificar si el correo ya existe
            if (UserRepo.existsByEmail(UserDto.getEmail())) {
                throw new IllegalStateException("El correo ya está en uso");
            }

            // Convertir DTO a entidad
            userEntity UserEntity = dtoToEntity(UserDto);

            // Asignar el rol "USER" por defecto si no se especifica
            if (UserEntity.getRole() == null || UserEntity.getRole().isEmpty()) {
                UserEntity.setRole("USER");
            }
            return UserRepo.save(UserEntity).getId();
        }

        catch (DataIntegrityViolationException e) {
            // Capturar la excepción de unicidad y lanzar un mensaje claro
            throw new IllegalStateException("Error: El correo ya está registrado.");
        } catch (Exception e) {
            // Manejar otros errores
            throw new IllegalStateException("Ocurrió un error al registrar el usuario.");
        }
    }

    // Método para obtener todos los usuarios y convertirlos a DTO
    public List<userDto> findAllUsers() {
        return UserRepo.findAll().stream()
                .map(this::entityToDto) // Convierte cada entidad a DTO
                .collect(Collectors.toList());
    }

    // Método para buscar un usuario por ID y convertirlo a DTO
    public userDto findUserById(Integer id) {
        Optional<userEntity> optionalUser = UserRepo.findById(id);
        return optionalUser.map(this::entityToDto).orElse(null); // Devuelve DTO o null si no se encuentra
    }
    // Conversión de Dto a Entidad
    private userEntity dtoToEntity(userDto UserDto) {
        return userEntity.builder()
                .withId(UserDto.getId())
                .withName(UserDto.getName())
                .withLastName(UserDto.getLastName())
                .withPassword(UserDto.getPassword())
                .withEmail(UserDto.getEmail())
                .withAddress(UserDto.getAddress())
                .withImage(UserDto.getImage())
                .withRole(UserDto.getRole())
                .build();
    }

    // Conversión de entidad a DTO
    private userDto entityToDto(userEntity UserEntity) {
        return userDto.builder()
                .withId(UserEntity.getId())
                .withName(UserEntity.getName())
                .withLastName(UserEntity.getLastName())
                .withPassword(UserEntity.getPassword())
                .withEmail(UserEntity.getEmail())
                .withAddress(UserEntity.getAddress())
                .withImage(UserEntity.getImage())
                .withRole(UserEntity.getRole())
                .build();
    }

    public boolean deleteUserById(Integer id) {
        Optional<userEntity> optionalUser = UserRepo.findById(id);

        if (optionalUser.isPresent()) {
            UserRepo.deleteById(id);
            return true; // Indica que el usuario fue eliminado exitosamente
        }

        return false; // Indica que no se encontró el usuario con el ID especificado
    }

    public userDto updateUser(Integer id, userDto UserDto) {
        // Busca al usuario por su ID
        Optional<userEntity> optionalUser = UserRepo.findById(id);

        if (optionalUser.isPresent()) {
            // Recupera la entidad existente
            userEntity userEntity = optionalUser.get();

            // Actualiza los campos con los valores del DTO
            userEntity.setName(UserDto.getName());
            userEntity.setLastName(UserDto.getLastName());
            userEntity.setPassword(UserDto.getPassword());
            userEntity.setEmail(UserDto.getEmail());
            userEntity.setAddress(UserDto.getAddress());
            userEntity.setImage(UserDto.getImage());
            userEntity.setRole(UserDto.getRole());

            // Guarda los cambios en la base de datos
            userEntity updatedEntity = UserRepo.save(userEntity);

            // Convierte la entidad actualizada a DTO y la retorna
            return entityToDto(updatedEntity);
        }

        // Si el usuario no se encuentra, retorna null
        return null;
    }


}