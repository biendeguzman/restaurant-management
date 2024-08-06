package com.example.restaurant_management.repository;

import com.example.restaurant_management.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepo extends JpaRepository<Owner, Long> {
}
