package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.sit.model.Restaurant;
import com.sit.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    // ✅ CREATE (ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Restaurant add(@RequestBody Restaurant r) {
        return service.addRestaurant(r);
    }

    // ✅ GET ALL (USER + ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    // ✅ GET BY ID (USER + ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Restaurant getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ UPDATE (ADMIN only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant r) {
        return service.update(id, r);
    }

    // ✅ DELETE (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Restaurant deleted successfully";
    }
}