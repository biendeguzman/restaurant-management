package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.Customer;
import com.example.restaurant_management.model.Employee;
import com.example.restaurant_management.model.Menu;
import com.example.restaurant_management.model.Restaurant;
import com.example.restaurant_management.repository.CustomerRepo;
import com.example.restaurant_management.repository.EmployeeRepo;
import com.example.restaurant_management.repository.MenuRepo;
import com.example.restaurant_management.repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @GetMapping
    public Iterable<Restaurant> findAll() {
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
        if (menus != null) {
            for (Menu menu : menus) {
                menu.setRestaurant(savedRestaurant);
                menuRepo.save(menu);
            }
        }
        List<Employee> employees = restaurant.getEmployee();
        if (employees != null) {
            for (Employee employee : employees) {
                employee.setRestaurant(savedRestaurant);
                employeeRepo.save(employee);
            }
        }
        List<Customer> customers = restaurant.getCustomer();
        if (customers != null) {
            for (Customer customer : customers) {
                customer.setRestaurant(savedRestaurant);
                customerRepo.save(customer);
            }
        }
        return ResponseEntity.ok(savedRestaurant);
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
