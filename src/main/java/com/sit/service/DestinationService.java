package com.sit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sit.model.Destination;
import com.sit.repository.DestinationRepository;
import com.sit.enums.DestinationType;

@Service
public class DestinationService {
	 @Autowired
	    private DestinationRepository repo;

	    // Create
	    public Destination addDestination(Destination d) {
	        return repo.save(d);
	    }

	    //  Get all
	    public List<Destination> getAll() {
	        return repo.findAll();
	    }

	    // Get by ID
	    public Destination getById(Long id) {
	        return repo.findById(id).orElseThrow(() -> new RuntimeException("Destination not found"));
	    }

	    //  Update
	    public Destination update(Long id, Destination newData) {
	        Destination old = getById(id);

	        old.setName(newData.getName());
	        old.setType(newData.getType());
	        old.setDescription(newData.getDescription());
	        old.setEstimatedCost(newData.getEstimatedCost());
	        old.setLocation(newData.getLocation());
	        old.setRating(newData.getRating());
	        old.setImageUrl(newData.getImageUrl());

	        return repo.save(old);
	    }

	    // ✅ Delete
	    public void delete(Long id) {
	        repo.deleteById(id);
	    }

	    // 🔍 Filters
	    public List<Destination> getByType(DestinationType type) {
	        return repo.findByType(type);
	    }

	    public List<Destination> getByBudget(double cost) {
	        return repo.findByEstimatedCostLessThanEqual(cost);
	    }

	    public List<Destination> getTopRated(double rating) {
	        return repo.findByRatingGreaterThanEqual(rating);
	    }

	    public List<Destination> getByLocation(String location) {
	        return repo.findByLocationIgnoreCase(location);
	    }

	    public List<Destination> searchByName(String name) {
	        return repo.findByNameContainingIgnoreCase(name);
	    }
}
