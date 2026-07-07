package com.mechanicmotoz.backend.service;

import com.mechanicmotoz.backend.dto.MotorcycleDTO;
import com.mechanicmotoz.backend.entity.Motorcycle;
import com.mechanicmotoz.backend.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MotorcycleService {
    
    private final MotorcycleRepository motorcycleRepository;
    
    public List<MotorcycleDTO> findAll() {
        return motorcycleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public MotorcycleDTO findById(String id) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada con ID: " + id));
        return toDTO(motorcycle);
    }
    
    public List<MotorcycleDTO> search(String query) {
        return motorcycleRepository.search(query).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MotorcycleDTO create(MotorcycleDTO dto) {
        if (motorcycleRepository.existsByPlate(dto.getPlate())) {
            throw new RuntimeException("Ya existe una moto con la placa: " + dto.getPlate());
        }
        
        Motorcycle motorcycle = toEntity(dto);
        motorcycle = motorcycleRepository.save(motorcycle);
        return toDTO(motorcycle);
    }
    
    @Transactional
    public MotorcycleDTO update(String id, MotorcycleDTO dto) {
        Motorcycle motorcycle = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto no encontrada con ID: " + id));
        
        // Verificar que la placa no esté duplicada
        if (!motorcycle.getPlate().equals(dto.getPlate()) && 
            motorcycleRepository.existsByPlate(dto.getPlate())) {
            throw new RuntimeException("Ya existe una moto con la placa: " + dto.getPlate());
        }
        
        motorcycle.setPlate(dto.getPlate());
        motorcycle.setBrand(dto.getBrand());
        motorcycle.setModel(dto.getModel());
        motorcycle.setYear(dto.getYear());
        motorcycle.setEngine(dto.getEngine());
        motorcycle.setOwner(dto.getOwner());
        motorcycle.setPhone(dto.getPhone());
        motorcycle.setEmail(dto.getEmail());
        motorcycle.setObservations(dto.getObservations());
        
        motorcycle = motorcycleRepository.save(motorcycle);
        return toDTO(motorcycle);
    }
    
    @Transactional
    public void delete(String id) {
        if (!motorcycleRepository.existsById(id)) {
            throw new RuntimeException("Moto no encontrada con ID: " + id);
        }
        motorcycleRepository.deleteById(id);
    }
    
    // ========================================
    // MAPPERS
    // ========================================
    
    private MotorcycleDTO toDTO(Motorcycle motorcycle) {
        return MotorcycleDTO.builder()
                .id(motorcycle.getId())
                .plate(motorcycle.getPlate())
                .brand(motorcycle.getBrand())
                .model(motorcycle.getModel())
                .year(motorcycle.getYear())
                .engine(motorcycle.getEngine())
                .owner(motorcycle.getOwner())
                .phone(motorcycle.getPhone())
                .email(motorcycle.getEmail())
                .observations(motorcycle.getObservations())
                .createdAt(motorcycle.getCreatedAt())
                .updatedAt(motorcycle.getUpdatedAt())
                .build();
    }
    
    private Motorcycle toEntity(MotorcycleDTO dto) {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setPlate(dto.getPlate());
        motorcycle.setBrand(dto.getBrand());
        motorcycle.setModel(dto.getModel());
        motorcycle.setYear(dto.getYear());
        motorcycle.setEngine(dto.getEngine());
        motorcycle.setOwner(dto.getOwner());
        motorcycle.setPhone(dto.getPhone());
        motorcycle.setEmail(dto.getEmail());
        motorcycle.setObservations(dto.getObservations());
        return motorcycle;
    }
}