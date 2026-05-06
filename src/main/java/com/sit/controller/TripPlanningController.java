package com.sit.controller;

import com.sit.dto.TripRequestDTO;
import com.sit.model.TripPlanning;
import com.sit.service.TripPlanningService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin
public class TripPlanningController {

    @Autowired
    private TripPlanningService service;

    @PostMapping("/plan")
    public Map<String, Object> planTrip(@RequestBody TripRequestDTO request) {
        return service.planTrip(request);
    }

    @GetMapping
    public List<TripPlanning> getAll() {
        return service.getAllTrips();
    }

    @GetMapping("/{id}")
    public TripPlanning getById(@PathVariable Long id) {
        return service.getTrip(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteTrip(id);
        return "Trip deleted successfully";
    }
}