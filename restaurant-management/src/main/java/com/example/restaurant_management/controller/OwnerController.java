package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.Owner;
import com.example.restaurant_management.repository.OwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    @Autowired
    private OwnerRepo ownerRepo;

    @GetMapping
    public Iterable<Owner> findAll() {
        return ownerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        return ownerRepo.findById(id)
                .map(owner -> ResponseEntity.ok(owner))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Owner createOwner(@RequestBody Owner owner) {
        return ownerRepo.save(owner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") Long id) {
        if (!ownerRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ownerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner, @PathVariable Long id) {
        if (ownerRepo.existsById(id)) {
            owner.setId(id);
            return ResponseEntity.ok(ownerRepo.save(owner));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
