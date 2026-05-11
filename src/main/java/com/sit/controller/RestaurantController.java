package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.sit.model.Destination;
import com.sit.model.Restaurant;
import com.sit.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Restaurant add(@RequestBody Restaurant r) {
        return service.addRestaurant(r);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Restaurant> getAll() {
        return service.getAll();
    }
    
    @GetMapping("/location/{location}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Restaurant> byLocation(@PathVariable String location) {
        return service.getByLocation(location);
    }


  
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Restaurant getById(@PathVariable Long id) {
        return service.getById(id);
    }

    
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