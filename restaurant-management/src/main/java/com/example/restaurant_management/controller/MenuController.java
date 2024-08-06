package com.example.restaurant_management.controller;

import com.example.restaurant_management.model.Menu;
import com.example.restaurant_management.repository.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuRepo menuRepo;

    @GetMapping
    public Iterable<Menu> findAll() {
        return menuRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        return menuRepo.findById(id)
                .map(menu -> ResponseEntity.ok(menu))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Menu createMenu(@RequestBody Menu menu) {
        return menuRepo.save(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable("id") Long id) {
        if (!menuRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        menuRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu, @PathVariable Long id) {
        if (menuRepo.existsById(id)) {
            menu.setId(id);
            return ResponseEntity.ok(menuRepo.save(menu));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
