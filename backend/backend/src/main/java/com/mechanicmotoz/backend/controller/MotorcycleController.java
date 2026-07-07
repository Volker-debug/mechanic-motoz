package com.mechanicmotoz.backend.controller;

import com.mechanicmotoz.backend.dto.MotorcycleDTO;
import com.mechanicmotoz.backend.service.MotorcycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorcycles")
@RequiredArgsConstructor
public class MotorcycleController {
    
    private final MotorcycleService motorcycleService;
    
    @GetMapping
    public ResponseEntity<List<MotorcycleDTO>> getAll() {
        return ResponseEntity.ok(motorcycleService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(motorcycleService.findById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<MotorcycleDTO>> search(@RequestParam String query) {
        return ResponseEntity.ok(motorcycleService.search(query));
    }
    
    @PostMapping
    public ResponseEntity<MotorcycleDTO> create(@Valid @RequestBody MotorcycleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(motorcycleService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> update(@PathVariable String id, 
                                                 @Valid @RequestBody MotorcycleDTO dto) {
        return ResponseEntity.ok(motorcycleService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        motorcycleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}