package com.example.restaurant_management.repository;

import com.example.restaurant_management.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByName(String name);
}
