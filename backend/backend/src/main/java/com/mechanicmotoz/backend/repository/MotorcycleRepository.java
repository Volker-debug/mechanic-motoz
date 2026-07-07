package com.mechanicmotoz.backend.repository;

import com.mechanicmotoz.backend.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, String> {
    Optional<Motorcycle> findByPlate(String plate);
    boolean existsByPlate(String plate);
    
    @Query("SELECT m FROM Motorcycle m WHERE " +
           "LOWER(m.plate) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.model) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.owner) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Motorcycle> search(@Param("search") String search);
}