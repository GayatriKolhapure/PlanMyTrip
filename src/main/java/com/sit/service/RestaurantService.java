package com.sit.service;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sit.model.Restaurant;
import com.sit.repository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repo;

    // ✅ CREATE
    public Restaurant addRestaurant(Restaurant r) {
        return repo.save(r);
    }

    // ✅ GET ALL
    public List<Restaurant> getAll() {
        return repo.findAll();
    }

    // ✅ GET BY ID
    public Restaurant getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    // ✅ UPDATE
    public Restaurant update(Long id, Restaurant newData) {

        Restaurant old = getById(id);

        old.setName(newData.getName());
        old.setLocation(newData.getLocation());
        old.setDescription(newData.getDescription());
        old.setType(newData.getType());
        old.setCuisine(newData.getCuisine());
        old.setAverageCostPerPerson(newData.getAverageCostPerPerson());
        old.setRating(newData.getRating());
        old.setHasWifi(newData.isHasWifi());
        old.setHasParking(newData.isHasParking());
        old.setVegOnly(newData.isVegOnly());
        old.setOpeningTime(newData.getOpeningTime());
        old.setClosingTime(newData.getClosingTime());
        old.setAddress(newData.getAddress());
        old.setPhoneNumber(newData.getPhoneNumber());

        return repo.save(old);
    }

    // ✅ DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }
}