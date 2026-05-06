package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sit.enums.DestinationType;
import com.sit.model.Destination;
import com.sit.service.DestinationService;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin
public class DestinationController {
	  @Autowired
	    private DestinationService service;

	    // ✅ CREATE
	    @PostMapping
	    public Destination add(@RequestBody Destination d) {
	        return service.addDestination(d);
	    }

	    // ✅ GET ALL
	    @GetMapping
	    public List<Destination> getAll() {
	        return service.getAll();
	    }

	    // ✅ GET BY ID
	    @GetMapping("/{id}")
	    public Destination getById(@PathVariable Long id) {
	        return service.getById(id);
	    }

	    // ✅ UPDATE
	    @PutMapping("/{id}")
	    public Destination update(@PathVariable Long id, @RequestBody Destination d) {
	        return service.update(id, d);
	    }

	    // ✅ DELETE
	    @DeleteMapping("/{id}")
	    public String delete(@PathVariable Long id) {
	        service.delete(id);
	        return "Destination deleted successfully";
	    }

	    // 🔍 FILTER APIs

	    @GetMapping("/type/{type}")
	    public List<Destination> byType(@PathVariable DestinationType type) {
	        return service.getByType(type);
	    }

	    @GetMapping("/budget/{cost}")
	    public List<Destination> byBudget(@PathVariable double cost) {
	        return service.getByBudget(cost);
	    }

	    @GetMapping("/rating/{rating}")
	    public List<Destination> topRated(@PathVariable double rating) {
	        return service.getTopRated(rating);
	    }

	    @GetMapping("/location/{location}")
	    public List<Destination> byLocation(@PathVariable String location) {
	        return service.getByLocation(location);
	    }

	    @GetMapping("/search")
	    public List<Destination> search(@RequestParam String name) {
	        return service.searchByName(name);
	    }

}
