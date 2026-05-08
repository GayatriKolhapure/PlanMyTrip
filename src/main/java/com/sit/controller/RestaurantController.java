package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sit.model.Restaurant;
import com.sit.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    // ✅ CREATE
    @PostMapping
    public Restaurant add(@RequestBody Restaurant r) {
        return service.addRestaurant(r);
    }

    // ✅ GET ALL
    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ UPDATE (FIX FOR YOUR 405 ERROR 🔥)
    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant r) {
        return service.update(id, r);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Restaurant deleted successfully";
    }
}