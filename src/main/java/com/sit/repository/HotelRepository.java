package com.sit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	List<Hotel> findByLocationIgnoreCase(String location);

}
