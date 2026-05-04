package com.sit.model;

import com.sit.enums.BudgetStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private TripPlanning trip;

    // User planned budget
    @Column(nullable = false)
    private double totalBudget;

    // Calculated cost
    private double estimatedCost;

    // Remaining budget
    private double remainingBudget;

    // Budget status
    @Enumerated(EnumType.STRING)
    private BudgetStatus status;
}
