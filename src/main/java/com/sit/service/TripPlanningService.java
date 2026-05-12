package com.sit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sit.dto.TripRequestDTO;
import com.sit.model.*;
import com.sit.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TripPlanningService {

    @Autowired
    private DestinationRepository destinationRepo;

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private TripRepository tripRepo;

    @Autowired
    private UserRepository userRepository; // ✅ ADD THIS

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Object> planTrip(TripRequestDTO request) {

        // 🔥 0. GET USER
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. GET DESTINATIONS
        List<Destination> places =
                destinationRepo.findByLocationIgnoreCaseAndType(
                        request.getLocation(),
                        request.getType()
                );

        if (places.isEmpty()) {
            throw new RuntimeException("No destinations found");
        }

        // 2. GET HOTELS
        List<Hotel> hotels =
                hotelRepo.findByLocationIgnoreCase(request.getLocation());

        // 3. GET RESTAURANTS
        List<Restaurant> restaurants =
                restaurantRepo.findByLocationIgnoreCase(request.getLocation());

        // 4. BUILD PLAN
        List<Map<String, Object>> plan = new ArrayList<>();

        int placeIndex = 0;

        for (int day = 1; day <= request.getDays(); day++) {

            Map<String, Object> dayPlan = new HashMap<>();
            dayPlan.put("day", day);

            // ---- places
            List<String> dayPlaces = new ArrayList<>();

            for (int i = 0; i < 2; i++) {
                Destination d = places.get(placeIndex % places.size());
                dayPlaces.add(d.getName());
                placeIndex++;
            }

            dayPlan.put("places", dayPlaces);

            // ---- hotel
            if (!hotels.isEmpty()) {
                Map<String, Object> hotelObj = new HashMap<>();
                hotelObj.put("name", hotels.get(day % hotels.size()).getName());
                dayPlan.put("hotel", hotelObj);
            }

            // ---- restaurant
            if (!restaurants.isEmpty()) {
                Map<String, Object> restaurantObj = new HashMap<>();
                restaurantObj.put("name", restaurants.get(day % restaurants.size()).getName());
                dayPlan.put("restaurant", restaurantObj);
            }

            plan.add(dayPlan);
        }

        // 5. SAVE TRIP
        TripPlanning trip = new TripPlanning();

        trip.setUser(user);

        trip.setTripName(request.getLocation() + " Trip");

        trip.setCreatedDate(LocalDate.now());

        trip.setLocation(request.getLocation());

        trip.setDays(request.getDays());

        trip.setType(request.getType());

        trip.setStartDate(request.getStartDate());

        trip.setEndDate(request.getEndDate());

        trip.setTotalBudget(request.getTotalBudget());

        trip.setDestinations(places);

        trip.setHotels(hotels);

        trip.setRestaurants(restaurants);

        try {
            trip.setPlanDetails(objectMapper.writeValueAsString(plan));
        } catch (Exception e) {
            throw new RuntimeException("Error converting plan to JSON");
        }

        tripRepo.save(trip);

        // 6. RESPONSE
        Map<String, Object> response = new HashMap<>();
        response.put("tripId", trip.getTripId());
        response.put("location", request.getLocation());
        response.put("days", request.getDays());
        response.put("plan", plan);

        return response;
    }

    public List<TripPlanning> getAllTrips() {
        return tripRepo.findAll();
    }

    public TripPlanning getTrip(Long id) {
        return tripRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    public void deleteTrip(Long id) {
        tripRepo.deleteById(id);
    }
}