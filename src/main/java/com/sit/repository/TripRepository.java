package com.sit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.model.TripPlanning;

public interface TripRepository extends JpaRepository<TripPlanning, Long>{
	
	List<TripPlanning> findByUserId(Long userId);

}
