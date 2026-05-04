package com.sit.model;

import com.sit.enums.RestaurantType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurant_id;

    // Basic Info
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location; // City or destination name

    @Column(length = 1000)
    private String description;

    // Type of restaurant (Veg, Non-Veg, Cafe, Fine Dining)
    @Enumerated(EnumType.STRING)
    private RestaurantType type;

    // Cuisine (Indian, Chinese, Italian, etc.)
    private String cuisine;

    // Cost info
    private double averageCostPerPerson;

    // Rating (0 - 5)
    private double rating;

    // Contact Details
    private String phoneNumber;
    private String email;

    // Address details
    private String address;

    // Timing
    private String openingTime;
    private String closingTime;

    // Online presence
    private String websiteUrl;
    private String imageUrl;

    // Facilities
    private boolean hasWifi;
    private boolean hasParking;
    private boolean isVegOnly;

    // Optional: Link to Destination
    private Long destinationId;
}
