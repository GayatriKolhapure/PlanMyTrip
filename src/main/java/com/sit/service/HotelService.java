package com.sit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sit.model.Hotel;
import com.sit.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository repo;

    // ✅ CREATE
    public Hotel addHotel(Hotel h) {
        return repo.save(h);
    }

    // ✅ GET ALL
    public List<Hotel> getAll() {
        return repo.findAll();
    }

    // ✅ GET BY ID
    public Hotel getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    // ✅ UPDATE
    public Hotel update(Long id, Hotel newData) {

        Hotel old = getById(id);

        old.setName(newData.getName());
        old.setLocation(newData.getLocation());
        old.setDescription(newData.getDescription());
        old.setType(newData.getType());
        old.setPricePerNight(newData.getPricePerNight());
        old.setRating(newData.getRating());
        old.setHasWifi(newData.isHasWifi());
        old.setHasParking(newData.isHasParking());
        old.setHasPool(newData.isHasPool());
        old.setHasRestaurant(newData.isHasRestaurant());
        old.setPhoneNumber(newData.getPhoneNumber());
        old.setAddress(newData.getAddress());

        return repo.save(old);
    }

    // ✅ DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // 🔍 FILTER BY LOCATION
    public List<Hotel> getByLocation(String location) {
        return repo.findByLocationIgnoreCase(location);
    }
}