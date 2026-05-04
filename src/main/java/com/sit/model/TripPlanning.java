package com.sit.model;

import java.time.LocalDate;
import java.util.List;

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
    private Long trip_id;

    // 🔗 User who created the trip
    private Long userId; // (You can convert to @ManyToOne later)

    // Basic Info
    @Column(nullable = false)
    private String tripName;

    private String description;

    // Travel dates
    private LocalDate startDate;
    private LocalDate endDate;

    // Budget reference
    private double totalBudget;

    // Trip status (PLANNED, COMPLETED, CANCELLED)
    @Enumerated(EnumType.STRING)
    private TripStatus status;

    // 🌍 Destinations included in trip
    @ManyToMany
    @JoinTable(
        name = "trip_destinations",
        joinColumns = @JoinColumn(name = "trip_id"),
        inverseJoinColumns = @JoinColumn(name = "destination_id")
    )
    private List<Destination> destinations;

    // 🍽️ Restaurants selected
    @ManyToMany
    @JoinTable(
        name = "trip_restaurants",
        joinColumns = @JoinColumn(name = "trip_id"),
        inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> restaurants;

    // 🏨 (Optional future) Hotels
    // private List<Hotel> hotels;

    // 📅 Day-wise planning (simple version)
    @Column(length = 2000)
    private String planDetails;

    // Metadata
    private LocalDate createdDate;
}