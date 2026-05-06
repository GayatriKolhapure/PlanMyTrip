package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sit.model.Hotel;
import com.sit.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {

    @Autowired
    private HotelService service;

    // ✅ CREATE
    @PostMapping
    public Hotel add(@RequestBody Hotel h) {
        return service.addHotel(h);
    }

    // ✅ GET ALL
    @GetMapping
    public List<Hotel> getAll() {
        return service.getAll();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Hotel getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Hotel update(@PathVariable Long id, @RequestBody Hotel h) {
        return service.update(id, h);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Hotel deleted successfully";
    }

    // 🔍 FILTER BY LOCATION
    @GetMapping("/location/{location}")
    public List<Hotel> byLocation(@PathVariable String location) {
        return service.getByLocation(location);
    }
}