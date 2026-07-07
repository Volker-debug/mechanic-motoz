package com.mechanicmotoz.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorcycleDTO {
    private String id;
    
    @NotBlank(message = "La placa es obligatoria")
    private String plate;
    
    @NotBlank(message = "La marca es obligatoria")
    private String brand;
    
    @NotBlank(message = "El modelo es obligatoria")
    private String model;
    
    @NotNull(message = "El año es obligatorio")
    private Integer year;
    
    @NotBlank(message = "El cilindraje es obligatorio")
    private String engine;
    
    @NotBlank(message = "El propietario es obligatorio")
    private String owner;
    
    private String phone;
    private String email;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}