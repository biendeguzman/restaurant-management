package com.example.restaurant_management.repository;

import com.example.restaurant_management.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
