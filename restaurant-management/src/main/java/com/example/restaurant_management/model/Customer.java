package com.example.restaurant_management.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

//    @OneToMany(mappedBy = "customer")
//    @JsonManagedReference
//    private List<Order> order;
//
//    public void setOrder(List<Order> savedOrder) {
//        this.order.clear();
//        this.order.addAll(savedOrder);
//    }
}