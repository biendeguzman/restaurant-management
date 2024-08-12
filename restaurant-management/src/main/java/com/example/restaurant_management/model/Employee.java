package com.example.restaurant_management.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;
    private String email;
    private String phoneNumber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}