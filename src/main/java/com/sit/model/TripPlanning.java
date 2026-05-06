package com.sit.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sit.enums.TripStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripPlanning {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long userId;

	    private String tripName;

	    private String description;

	    private LocalDate startDate;
	    private LocalDate endDate;

	    private double totalBudget;

	    @Enumerated(EnumType.STRING)
	    private TripStatus status;

	    // 🌍 DESTINATIONS
	    @ManyToMany
	    @JoinTable(
	        name = "trip_destinations",
	        joinColumns = @JoinColumn(name = "trip_id"),
	        inverseJoinColumns = @JoinColumn(name = "destination_id")
	    )
	    private List<Destination> destinations;

	    // 🍽️ ADD HERE (RESTAURANTS)
	    @ManyToMany
	    @JoinTable(
	        name = "trip_restaurants",
	        joinColumns = @JoinColumn(name = "trip_id"),
	        inverseJoinColumns = @JoinColumn(name = "restaurant_id")
	    )
	    private List<Restaurant> restaurants;

	    // 🏨 ADD HERE (HOTELS)
	    @ManyToMany
	    @JoinTable(
	        name = "trip_hotels",
	        joinColumns = @JoinColumn(name = "trip_id"),
	        inverseJoinColumns = @JoinColumn(name = "hotel_id")
	    )
	    private List<Hotel> hotels;

	    // 📅 PLAN
	    @Column(length = 2000)
	    private String planDetails;

	    private LocalDate createdDate;
	    }