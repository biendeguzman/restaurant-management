package com.example.restaurant_management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<Menu> menu = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<Employee> employee = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private List<Customer> customer = new ArrayList<>();

    public void setEmployee(List<Employee> savedEmployees) {
        this.employee.clear();
        this.employee.addAll(savedEmployees);
    }

    public void setMenu(List<Menu> savedMenus) {
        this.menu.clear();
        this.menu.addAll(savedMenus);
    }

    public void setCustomer(List<Customer> savedCustomers) {
        this.customer.clear();
        this.customer.addAll(savedCustomers);
    }
}
