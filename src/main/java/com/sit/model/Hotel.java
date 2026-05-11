package com.sit.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="hotel_id")
    private Long id;

    // Basic Info
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(length = 1000)
    private String description;
    
 // Hotel Type
    private String type; // LUXURY, BUDGET, RESORT


    // Pricing
    private double pricePerNight;

    private double rating;
    
    

    // Contact
    private String phoneNumber;
    private String email;

    private String address;

    // Amenities
    private boolean hasWifi;
    private boolean hasParking;
    private boolean hasPool;
    private boolean hasRestaurant;

    // Online
    private String websiteUrl;
    private String imageUrl;

    // 🔁 Mapping with Trip
    @ManyToMany(mappedBy = "hotels")
    @JsonIgnore
    private List<TripPlanning> trips;
    
//    @ManyToMany
//    @JoinTable(
//        name = "trip_hotels",
//        joinColumns = @JoinColumn(name = "trip_id"),
//        inverseJoinColumns = @JoinColumn(name = "hotel_id")
//    )
//    private List<Hotel> hotels;
    
    
}