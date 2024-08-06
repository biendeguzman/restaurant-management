package com.example.restaurant_management.repository;

import com.example.restaurant_management.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long>{

}
