package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.Customer;
//import com.example.restaurant_management.model.Order;
import com.example.restaurant_management.repository.CustomerRepo;
//import com.example.restaurant_management.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

//    @Autowired
//    private OrderRepo orderRepo;

    @GetMapping
    public ResponseEntity<Iterable<Customer>> findAll() {
        Iterable<Customer> customers = customerRepo.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        Customer savedCustomer = customerRepo.save(customer);

//        List<Order> orders = customer.getOrder();
//        List<Order> savedOrders = new ArrayList<>();
//        for (Order order : orders) {
//            order.setTotalAmount(order.getTotalAmount());
//            order.setPrice(order.getPrice());
//            order.setCustomer(savedCustomer);
//            Order savedOrder = orderRepo.save(order);
//            savedOrders.add(savedOrder);
//        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        return customerRepo.findById(id)
                .map(existingCustomer -> {
                    customer.setId(id);
                    Customer updatedCustomer = customerRepo.save(customer);
                    return ResponseEntity.ok(updatedCustomer);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
