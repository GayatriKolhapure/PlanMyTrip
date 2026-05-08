package com.sit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sit.dto.BudgetRequestDto;
import com.sit.enums.BudgetStatus;
import com.sit.model.Budget;
import com.sit.model.Destination;
import com.sit.model.Restaurant;
import com.sit.model.TripPlanning;
import com.sit.repository.BudgetRepository;
import com.sit.repository.TripRepository;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	// MAIN LOGIC
	@Autowired
	private TripRepository tripRepository;

	public Budget calculateBudget(Long tripId, double totalBudget) {

    TripPlanning trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new RuntimeException("Trip not found"));

    Budget budget = new Budget();
    budget.setTrip(trip);
    budget.setTotalBudget(totalBudget);

    double destinationCost = 0;
    double restaurantCost = 0;

    if (trip.getDestinations() != null) {
        destinationCost = trip.getDestinations()
                .stream()
                .mapToDouble(Destination::getEstimatedCost)
                .sum();
    }

    if (trip.getRestaurants() != null) {
        restaurantCost = trip.getRestaurants()
                .stream()
                .mapToDouble(Restaurant::getAverageCostPerPerson)
                .sum();
    }

    double estimatedCost = destinationCost + restaurantCost;
    budget.setEstimatedCost(estimatedCost);

    double remaining = totalBudget - estimatedCost;
    budget.setRemainingBudget(remaining);

    if (remaining > 1000) {
        budget.setStatus(BudgetStatus.UNDER_BUDGET);
        budget.setSuggestion("Budget is sufficient for this trip");
    } else if (remaining >= 0) {
        budget.setStatus(BudgetStatus.WITHIN_BUDGET);
        budget.setSuggestion("Budget is almost fully used");
    } else {
        budget.setStatus(BudgetStatus.OVER_BUDGET);

        if (restaurantCost > destinationCost) {
            budget.setSuggestion("Choose affordable restaurants");
        } else {
            budget.setSuggestion("Choose affordable destinations");
        }
    }

    return budgetRepository.save(budget);
}
	public List<Budget> getAllBudgets() {

		return budgetRepository.findAll();
	}

	public Budget getBudgetById(Long id) {

		Optional<Budget> budget = budgetRepository.findById(id);

		return budget.orElse(null);
	}

	public String deleteBudget(Long id) {

		budgetRepository.deleteById(id);

		return "Budget Deleted Successfully";

	}

}

