package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.*;
import com.example.restaurant_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private SupplierRepo supplierRepo;

    @GetMapping
    public List<Restaurant> findAll() {
        return restaurantRepo.findAll();
    }
    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantRepo.findById(id).orElse(null);
    }
    @PostMapping
    @Transactional
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {

        Restaurant savedRestaurant = restaurantRepo.save(restaurant);

        List<Menu> menus = restaurant.getMenu();
        List<Menu> savedMenus = new ArrayList<>();
        for (Menu menu : menus) {
            menu.setName(menu.getName());
            menu.setCategory(menu.getCategory());
            menu.setPrice(menu.getPrice());
            menu.setRestaurant(savedRestaurant);
            Menu savedMenu = menuRepo.save(menu);
            savedMenus.add(savedMenu);
        }

        List<Employee> employees = restaurant.getEmployee();
        List<Employee> savedEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            employee.setName(employee.getName());
            employee.setRole(employee.getRole());
            employee.setEmail(employee.getEmail());
            employee.setRestaurant(savedRestaurant);
            Employee savedEmployee = employeeRepo.save(employee);
            savedEmployees.add(savedEmployee);
        }

        List<Customer> customers = restaurant.getCustomer();
        List<Customer> savedCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            customer.setName(customer.getName());
            customer.setAddress(customer.getAddress());
            customer.setPhoneNumber(customer.getPhoneNumber());
            customer.setRestaurant(savedRestaurant);
            Customer savedCustomer = customerRepo.save(customer);
            savedCustomers.add(savedCustomer);
        }

        List<Supplier> suppliers = restaurant.getSuppliers();
        List<Supplier> savedSuppliers = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            Supplier savedSupplier = supplierRepo.findByName(supplier.getName())
                    .orElseGet(() -> supplierRepo.save(supplier));
            savedSuppliers.add(savedSupplier);
        }

        savedRestaurant.setSuppliers(savedSuppliers);

        restaurantRepo.save(savedRestaurant);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("id") Long id) {
        if (!restaurantRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        restaurantRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable Long id) {
        if (restaurantRepo.existsById(id)) {
            restaurant.setId(id);
            return ResponseEntity.ok(restaurantRepo.save(restaurant));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
