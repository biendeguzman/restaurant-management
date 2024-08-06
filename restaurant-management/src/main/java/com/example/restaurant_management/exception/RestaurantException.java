package com.example.restaurant_management.exception;

public class RestaurantException extends RuntimeException{
public RestaurantException() {
        super("Restaurant not found");
    }
}
