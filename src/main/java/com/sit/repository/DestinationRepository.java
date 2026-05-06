package com.sit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sit.model.Destination;
import com.sit.enums.DestinationType;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    // Filter by type
    List<Destination> findByType(DestinationType type);

    //  Filter by budget
    List<Destination> findByEstimatedCostLessThanEqual(double cost);

    // Top rated
    List<Destination> findByRatingGreaterThanEqual(double rating);

    //  Location filter
    List<Destination> findByLocationIgnoreCase(String location);

    //  Search by name
    List<Destination> findByNameContainingIgnoreCase(String name);
    
    
    List<Destination> findByLocationIgnoreCaseAndType(String location, DestinationType type);
	
}
