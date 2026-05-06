package com.sit.model;
import com.sit.enums.DestinationType;

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
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Example: BEACH, HILL, CITY, ADVENTURE
    @Enumerated(EnumType.STRING)
    private DestinationType type;

    @Column(length = 1000)
    private String description;

    // Average cost to visit this place
    private double estimatedCost;

    private String location; // e.g., Goa, Manali

    // Rating out of 5
    private double rating;

    // Image URL for frontend display
    private String imageUrl;
}