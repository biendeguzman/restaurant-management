package com.example.restaurant_management.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;
    @Lob
    private byte[] data;

}
