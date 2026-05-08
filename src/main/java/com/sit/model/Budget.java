package com.sit.model;

import com.sit.enums.BudgetStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // 🔗 Link with Trip
	 @ManyToOne
	 @JoinColumn(name = "trip_id", nullable = false)
	 private TripPlanning trip;

	    // User planned budget
	    @NotNull(message = "Budget cannot be null")
	    @Positive(message = "Budget must be greater than 0")
	    
	    private double totalBudget;
	    
	    

	    // Calculated cost
	    private double estimatedCost;

	    // Remaining budget
	    private double remainingBudget;

	    // Budget status
	    @Enumerated(EnumType.STRING)
	    private BudgetStatus status;
	    private String suggestion;
}
