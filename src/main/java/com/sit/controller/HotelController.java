package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import com.sit.model.Hotel;
import com.sit.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {

    @Autowired
    private HotelService service;

    // ✅ CREATE (ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Hotel add(@RequestBody Hotel h) {
        return service.addHotel(h);
    }

    // ✅ GET ALL (USER + ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Hotel> getAll() {
        return service.getAll();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Hotel getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ UPDATE (ADMIN only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Hotel update(@PathVariable Long id, @RequestBody Hotel h) {
        return service.update(id, h);
    }

    // ✅ DELETE (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Hotel deleted successfully";
    }

    // 🔍 FILTER (USER + ADMIN)
    @GetMapping("/location/{location}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Hotel> byLocation(@PathVariable String location) {
        return service.getByLocation(location);
    }
}