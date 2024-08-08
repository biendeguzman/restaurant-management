package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.Supplier;
import com.example.restaurant_management.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierRepo supplierRepo;

    @GetMapping
    public ResponseEntity<Iterable<Supplier>> findAll() {
        Iterable<Supplier> suppliers = supplierRepo.findAll();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        return supplierRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier savedSupplier = supplierRepo.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSupplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody Supplier supplier, @PathVariable Long id) {
        return supplierRepo.findById(id)
                .map(existingEmployee -> {
                    supplier.setId(id);
                    Supplier updatedSupplier = supplierRepo.save(supplier);
                    return ResponseEntity.ok(updatedSupplier);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        if (!supplierRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        supplierRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
