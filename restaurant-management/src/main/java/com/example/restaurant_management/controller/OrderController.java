//package com.example.restaurant_management.controller;
//
//import com.example.restaurant_management.model.Order;
//import com.example.restaurant_management.repository.OrderRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/order")
//public class OrderController {
//
//    @Autowired
//    private OrderRepo orderRepo;
//
//    @GetMapping
//    public ResponseEntity<Iterable<Order>> findAll() {
//        Iterable<Order> orders = orderRepo.findAll();
//        return ResponseEntity.ok(orders);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
//        return orderRepo.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//        Order savedOrder = orderRepo.save(order);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        if (!orderRepo.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        orderRepo.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long id) {
//        return orderRepo.findById(id)
//                .map(existingOrder -> {
//                    order.setId(id);
//                    Order updatedOrder = orderRepo.save(order);
//                    return ResponseEntity.ok(updatedOrder);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//}
