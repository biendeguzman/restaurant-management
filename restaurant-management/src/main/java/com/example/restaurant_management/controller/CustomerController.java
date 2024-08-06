package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.Customer;
import com.example.restaurant_management.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping
    public Iterable<Customer> findAll() {
        return customerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerRepo.findById(id)
                .map(menu -> ResponseEntity.ok(menu))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepo.save(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        if (!customerRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        if (customerRepo.existsById(id)) {
            customer.setId(id);
            return ResponseEntity.ok(customerRepo.save(customer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

