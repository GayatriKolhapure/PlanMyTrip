package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.sit.enums.DestinationType;
import com.sit.model.Destination;
import com.sit.service.DestinationService;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin
public class DestinationController {

    @Autowired
    private DestinationService service;

    // ✅ CREATE (ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Destination add(@RequestBody Destination d) {
        return service.addDestination(d);
    }

    // ✅ GET ALL (USER + ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Destination> getAll() {
        return service.getAll();
    }

    // ✅ GET BY ID (USER + ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Destination getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ UPDATE (ADMIN only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Destination update(@PathVariable Long id, @RequestBody Destination d) {
        return service.update(id, d);
    }

    // ✅ DELETE (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Destination deleted successfully";
    }

    // 🔍 FILTER APIs (USER + ADMIN)

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Destination> byType(@PathVariable DestinationType type) {
        return service.getByType(type);
    }

    @GetMapping("/budget/{cost}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Destination> byBudget(@PathVariable double cost) {
        return service.getByBudget(cost);
    }

    @GetMapping("/rating/{rating}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Destination> topRated(@PathVariable double rating) {
        return service.getTopRated(rating);
    }

    @GetMapping("/location/{location}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Destination> byLocation(@PathVariable String location) {
        return service.getByLocation(location);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Destination> search(@RequestParam String name) {
        return service.searchByName(name);
    }
}