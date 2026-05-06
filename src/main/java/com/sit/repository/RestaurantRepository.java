package com.sit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	List<Restaurant> findByLocationIgnoreCase(String location);

}
