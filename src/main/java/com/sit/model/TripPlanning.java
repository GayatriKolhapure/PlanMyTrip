package com.sit.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sit.enums.DestinationType;
import com.sit.enums.TripStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripPlanning {

    // 🔥 PRIMARY KEY (VERY IMPORTANT)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    // 🔗 USER RELATION
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("trips")
    private User user;

    // BASIC INFO
    private String tripName;
    private String description;
    
    private String location;

    private int days;

    private DestinationType type;

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

    // 🍽️ RESTAURANTS
    @ManyToMany
    @JoinTable(
        name = "trip_restaurants",
        joinColumns = @JoinColumn(name = "trip_id"),
        inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> restaurants;

    // 🏨 HOTELS
    @ManyToMany
    @JoinTable(
        name = "trip_hotels",
        joinColumns = @JoinColumn(name = "trip_id"),
        inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    private List<Hotel> hotels;

    // 📅 PLAN DETAILS (JSON STRING)
    @Column(length = 2000)
    private String planDetails;

    private LocalDate createdDate;
}